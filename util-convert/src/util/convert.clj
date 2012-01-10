(ns util.convert
  "Type conversion utilities"
  (:import [java.util UUID]))

(defn string->uuid
  [stringified-uuid]
  {:pre [(string? stringified-uuid)]}
  (. UUID (fromString stringified-uuid)))

(defn string->float
  [stringified-float]
  {:pre [(string? stringified-float)]}
  (Float/parseFloat stringified-float))


