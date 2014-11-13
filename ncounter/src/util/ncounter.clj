(ns util.ncounter
 "Persistent counters - no dynamic vars"
  (:refer-clojure :exclude [inc get])
  )

(defn create-container
  []
  (ref {}))

(defmulti create
  (fn 
    create-dispatch-fn
    [container kw-or-vec]
    (cond 
     (keyword? kw-or-vec) :keyword
     (vector? kw-or-vec) :vector)))

(defmethod create :keyword
  create-keyword-fn
  [container name]
  (when container
    (dosync (commute container assoc name 0))))

(defmethod create :vector
  create-vector-fn
  [container names]
  (doseq [name names]
    (create container name)))

(defn inc
  "Increments the named counter"
  [container name]
  {:pre [(keyword? name)]}
  (when container
    (dosync (commute container assoc name
                     (clojure.core/inc (or (container name) 0))))))

(defn get
  "Returns value of the named counter, with no argument, returns entire counter map"
  ([container]
     (when container
       (deref container)))
  ([container name]
     {:pre [(keyword? name)]}
     (when container
       (clojure.core/get (deref container) name))))

(defn reset
  "Zeros all specified counters, if no seq of counters provided, resets ALL previously defined counters"
  ([container]
     (reset container (keys (get container))))
  ([container name-or-names]
     (cond 
      (keyword? name-or-names) (create container name-or-names)
      (vector? name-or-names) (doseq [counter name-or-names]
                                (reset container counter)))))

(defn nuke-all!
  "Destroys all counters, resets the ref to the empty map"
  [container]
  (dosync (ref-set container (ref {}))))

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
;;   (when container
;;     (dosync (commute container assoc name 0))))

;; (defmethod new ::vector [names]
;;   (doseq [counter names]
;;     (new ::keyword counter)))

