(ns util.uuid
  "Utilities for dealing with UUIDs"
  (:import [java.util UUID]
;          [com.eaio.uuid UUID]
           ))

;; http://johannburkard.de/software/uuid/#maven

;; (defn random-uuid-old "Creates a new (likestream) UUID.  Not sure this is really so great"
;;   []
;;   (. UUID (fromString (.toString (com.eaio.uuid.UUID.)))))

(defn random-uuid
  []
  (. UUID (randomUUID)))

(defn string->uuid
  "Convert a string to a UUID"
  [stringified-uuid]
  {:pre  [(string? stringified-uuid)]
   :post [(= (type %) java.util.UUID)]}
  (. UUID (fromString stringified-uuid)))

(defn uuid->string
  [uuid]
  (.toString uuid))



