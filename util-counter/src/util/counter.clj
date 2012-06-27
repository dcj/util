(ns util.counter
  "Persistent counter")

(def ^{:private true
       :dynamic true} *persistent-counters* (ref {}))

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
  
(defn new-counter
  "Creates a new counter"
  [name]
  {:pre [(keyword? name)]}
  (when *persistent-counters*
    (dosync (commute *persistent-counters* assoc name 0))))

(defn new-counters
  "Creates new counters"
  [names]
  {:pre [(vector? names)]}
  (doseq [counter names]
    (new-counter counter)))

(defn inc-counter
  "Increments the named counter"
  [name]
  {:pre [(keyword? name)]}
  (when  *persistent-counters*
    (dosync (commute *persistent-counters* assoc name
                     (inc (or (*persistent-counters* name) 0))))))

(defn get-counter
  "Returns value of the named counter."
  [name]
  {:pre [(keyword? name)]}
  (when  *persistent-counters*
    (get (deref *persistent-counters*) name)))

(defn get-counters-map
  "Returns a map containing all counters and their values"
  []
  (when  *persistent-counters*
    (deref *persistent-counters*)))

;;  TODO: AFAIK reset-counter is the same as new-counter, so just calling that for now....
(defn reset-counter
  "Zeros the value of named counter"
  [name]
  (new-counter name))

(defn reset-counters
  "Zeros all specified counters, if no seq of counters provided, resets ALL previously defined counters"
  ([]
     (reset-counters (keys (get-counters-map))))
  ([names]
     {:pre [(seq? names)]}
     (doseq [counter names]
       (reset-counter counter))))

(defn nuke-all!
  "Destroys all counters, resets the ref to the empty map"
  []
  (dosync (ref-set *persistent-counters* (ref {}))))



