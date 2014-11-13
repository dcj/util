(ns util.counter
 "Persistent counters"
  (:refer-clojure :exclude [inc get])
  )

(def ^{:private true
       :dynamic true} *persistent-counters* (ref {}))

(defmulti create
  (fn 
    create-dispatch-fn
    [kw-or-vec]
    (cond 
     (keyword? kw-or-vec) :keyword
     (vector? kw-or-vec) :vector)))

(defmethod create :keyword
  create-keyword-fn
  [name]
  (when *persistent-counters*
    (dosync (commute *persistent-counters* assoc name 0))))

(defmethod create :vector
  create-vector-fn
  [names]
  (doseq [name names]
    (create name)))

(defn inc
  "Increments the named counter"
  [name]
  {:pre [(keyword? name)]}
  (when  *persistent-counters*
    (dosync (commute *persistent-counters* assoc name
                     (clojure.core/inc (or (*persistent-counters* name) 0))))))

(defn get
  "Returns value of the named counter, with no argument, returns entire counter map"
  ([]
     (when  *persistent-counters*
       (deref *persistent-counters*)))
  ([name]
     {:pre [(keyword? name)]}
     (when  *persistent-counters*
       (clojure.core/get (deref *persistent-counters*) name))))

(defn reset
  "Zeros all specified counters, if no seq of counters provided, resets ALL previously defined counters"
  ([]
     (reset (keys (get))))
  ([name-or-names]
     (cond 
      (keyword? name-or-names) (create name-or-names)
      (vector? name-or-names) (doseq [counter name-or-names]
                                (reset counter)))))

(defn nuke-all!
  "Destroys all counters, resets the ref to the empty map"
  []
  (dosync (ref-set *persistent-counters* (ref {}))))

;; TODO:  Is commute the correct thing for counters below, or should I be using alter?
;; TODO: Do we need delete-counter ?

;; (defn- new-counter []
;;   (let [count (ref 0)
;; 	zero  (fn [x] 0)]
;;     {:increment #(dosync (alter count inc))
;;      :decrement #(dosync (alter count dec))
;;      :reset     #(dosync (alter count zero))
;;      :get-value #(deref count)}))

;; (defn counter-dispatch
;;   "Returns dispatch keyword for new counter multimethods"
;;   [arg]
;;   (if (vector? arg)
;;     ::vector
;;     ::keyword))

;; (defmulti new counter-dispatch)

;; (defmethod new ::keyword [name]
;;   {:pre [(keyword? name)]}
;;   (when *persistent-counters*
;;     (dosync (commute *persistent-counters* assoc name 0))))

;; (defmethod new ::vector [names]
;;   (doseq [counter names]
;;     (new ::keyword counter)))

