(ns util.counter
  "Persistent counter")

(def ^{:private true} *persistent-counters* (ref {}))

;; TODO:  Is commute the correct thing for counters below, or should I be using alter?
;; TODO: Do we need delete-counter ?

;; (defn- new-counter []
;;   (let [count (ref 0)
;; 	zero  (fn [x] 0)]
;;     {:increment #(dosync (alter count inc))
;;      :decrement #(dosync (alter count dec))
;;      :reset     #(dosync (alter count zero))
;;      :get-value #(deref count)}))

(defn new-counter
  "Creates a new counter in *persistent-counters*"
  [name]
  {:pre [(keyword? name)]}
  (when *persistent-counters*
    (dosync (commute *persistent-counters* assoc name 0))))

(defn new-counters
  "Creates new counters in *persistent-counters*"
  [names]
  {:pre [(vector? names)]}
  (doseq [counter names]
    (new-counter counter)))

(defn inc-counter
  "Increments the named counter in *persistent-counters*, a ref to a map."
  [name]
  {:pre [(keyword? name)]}
  (when  *persistent-counters*
    (dosync (commute *persistent-counters* assoc name
                     (inc (or (*persistent-counters* name) 0))))))

(defn get-counter
  "Returns value of the named counter in *persistent-counters*, a ref to a map."
  [name]
  {:pre [(keyword? name)]}
  (when  *persistent-counters*
    (get (deref *persistent-counters*) name)))

(defn get-counters-map
  "Returns the value of the *persistent-counters*, a ref, a map"
  []
  (when  *persistent-counters*
    (deref *persistent-counters*)))

;;  TODO: AFAIK reset-counter is the same as new-counter, so just calling that for now....
(defn reset-counter
  "Sets to zero the value of named counter in *persistent-counters*"
  [name]
  (new-counter name))

