(ns util.map
  "Functions for manipulating Clojure maps")

;; TODO: figure out how to eliminate simple recursion from string-keys->keyword-keys

;; (defn string-keys->keyword-keys
;;   "Convert a map with string keys to have keyword keys"
;;   [map2convert]
;;   {:pre [(map? map2convert)]}
;;   (letfn [(process-map [map2process]
;;             "Process a map"
;;             #(apply hash-map
;;                      (mapcat process-kv
;;                              (seq map2process))))
;;           (process-vector [vec2process]
;;             "Process a vector"
;;             #(into '[] (map (fn [m]
;;                                (if (map? m)
;;                                  (process-map m)
;;                                  m))
;;                              vec2process)))
;;           (process-kv [[k v]]
;;             "Process a vector representing a key value pair"
;;             #(list (keyword k)
;;                     (cond (map? v) (process-map v)
;;                           (vector? v) (process-vector v)
;;                           :else v)))]
;;     (trampoline process-map map2convert)))

(declare string-key->keyword-keys-process-kv)

(defn string-keys->keyword-keys
   "Convert a map with string keys to have keywords keys"
   [map2convert]
   {:pre [(map? map2convert)]}
   (apply hash-map
          (mapcat string-key->keyword-keys-process-kv (seq map2convert))))

(defn- string-keys->keyword-keys-process-vector
  [vector2convert]
  (into '[] (map #(if (map? %)
                    (string-keys->keyword-keys %)
                    %)
                 vector2convert)))

(defn- string-key->keyword-keys-process-kv
   [[k v]]
   (list (keyword k)
         (cond (map? v)    (string-keys->keyword-keys v)
               (vector? v) (string-keys->keyword-keys-process-vector v)
               :else       v)))

(defn get-keys-with-value-of
  "Return list of keys from input-map whose value is = to test-value"
  [test-value input-map]
  {:pre [(map? input-map)]}
  (remove nil?
          (for [[k v] input-map]
            (if (= v test-value) k))))

(defn remove-keys-with-empty-string-values
  "You can't figure out what this function does by the name? Really???"
  [input-map]
  {:pre [(map? input-map)]}
  (apply (partial dissoc input-map)
         (get-keys-with-value-of "" input-map)))

(defn remove-keys-with-nil-values
  "You can't figure out what this function does by the name? Really???"
  [input-map]
  {:pre [(map? input-map)]}
  (apply (partial dissoc input-map)
         (get-keys-with-value-of nil input-map)))

(defn translate-map
  "Returns updated input-map, with new values obtained by applying the function which is the value of the (same) key in conversion-map.  In other words, conversion-map provides a map of keys to conversion functions, each conversion function takes the current value of the associated key"
  [input-map conversion-map]
  {:pre [(map? input-map)
         (map? conversion-map)]}
  (loop [keys-to-convert (keys conversion-map)
         keys-and-value-funcs conversion-map
         acc input-map]
    (let [current-key (first keys-to-convert)]
      (if (and (keyword? current-key)
               (contains? acc current-key))
        (recur (rest keys-to-convert)
               conversion-map
               (update-in acc [current-key] (get keys-and-value-funcs current-key)))
        acc))))


;; Taken from:  http://groups.google.com/group/clojure/msg/3d2df2067080a52a?hl=en

(defmacro def-fields [struct-name & fields]
  (let [field-symbol-vector (->> fields (map name) (map symbol) vec)
        arg (gensym)
        body (gensym)
        macro-name (symbol (str "let-" struct-name))]
    `(defmacro ~macro-name [~arg & ~body]
      `(let [{:keys ~'~field-symbol-vector} ~~arg] ~@~body))))
    
;; ; How to use it.

;; (def-fields person :first-name :last-name :city)

;; (defn print-person [p]
;;   (let-person p
;;     (println "First name:" first-name)
;;     (println "Last name:" last-name)
;;     (println "City:" city)))

;; (def person1 {:first-name "John" :last-name "Smith" :city "San Francisco"})
;; (print-person person1)

