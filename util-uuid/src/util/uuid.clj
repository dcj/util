(ns util.uuid
  "Utilities for dealing with UUIDs"
  (:import [java.util UUID]
                                        ;           [com.eaio.uuid UUID]
           ))

;; http://johannburkard.de/software/uuid/#maven

;; (defn random-uuid-old "Creates a new (likestream) UUID.  Not sure this is really so great"
;;   []
;;   (. UUID (fromString (.toString (com.eaio.uuid.UUID.)))))

(defn random-uuid
  []
  (. UUID (randomUUID)))

(defn string->uuid
  [stringified-uuid]
  (. UUID (fromString stringified-uuid)))

