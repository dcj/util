(ns util.convert
  "Type conversion utilities"
  (:import [java.util UUID]))

(defn string->uuid
  "Convert a string to a UUID"
  [stringified-uuid]
  {:pre [(string? stringified-uuid)]}
  (. UUID (fromString stringified-uuid)))

(defn string->float
  "Convert a string to a Float"
  [stringified-float]
  {:pre [(string? stringified-float)]}
  (Float/parseFloat stringified-float))


