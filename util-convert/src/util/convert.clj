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



