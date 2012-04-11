(ns util.convert
  "Type conversion utilities"
  (:import [java.util UUID]))

(defn string->uuid
  "Convert a string to a UUID"
  [stringified-uuid]
  {:pre  [(string? stringified-uuid)]
   :post [(= (type %) java.util.UUID)]}
  (. UUID (fromString stringified-uuid)))

(defn string->float
  "Convert a string to a Float"
  [stringified-float]
  {:pre [(string? stringified-float)]}
  (Float/parseFloat stringified-float))

(defn string->bool
  "Convert a string of yes to boolean true, and a string of no to boolean false"
  [yes-or-no-string]
  (cond
   (= yes-or-no-string "yes") true
   (= yes-or-no-string "no")  false))

(defn uuid->string
  [uuid]
  (.toString uuid))

(defn long->string
  [long]
  (.toString long))

;; From relevence.utils - still necessary?
(defn #^String safe-str [x]
  (when x
    (if (string? x)
      x
      (if (instance? clojure.lang.Named x)
        (name x)
        (str x)))))

;; TODO anyplace the below us used, needs to be fixed
(defn ensure-string-id [id]
  (let [id-type (type id)]
    (if (or (= java.lang.Long id-type)
            (= java.lang.Integer id-type))
      (.toString id)
      id)))

;; TODO anyplace the below us used, needs to be fixed
(defn ensure-string-uuid [uuid]
  (if (= java.util.UUID (type uuid))
    (.toString uuid)
    uuid))




