(ns util.uuid
  "Utilities for dealing with UUIDs"
  (:require [clojure.string :as str]
            [slingshot.slingshot :refer [throw+ try+]]
            [datomic.api :as datomic]
            )
  (:import [java.util UUID])
  )
            
;; http://johannburkard.de/software/uuid/#maven
;;
;; [com.eaio.uuid UUID]
;; (defn random-uuid-old "Creates a new (likestream) UUID.  Not sure this is really so great"
;;   []
;;   (. UUID (fromString (.toString (com.eaio.uuid.UUID.)))))

(defn random
  "Returns a random UUID.  
   If :squuid is specified, returns a Datomic squuid, that is:
   Constructs a semi-sequential UUID. Useful for creating UUIDs
   that don't fragment indexes. Returns a UUID whose most significant
   32 bits are currentTimeMillis rounded to seconds."
  ([]
     (random :random))
  ([uuid-type]
     (case uuid-type
       :random (UUID/randomUUID)
       :squuid (datomic/squuid)
       (UUID/randomUUID))))

(defn squuid
  "Datomic's squuid,
   Constructs a semi-sequential UUID. Useful for creating UUIDs
   that don't fragment indexes. Returns a UUID whose most significant
   32 bits are currentTimeMillis rounded to seconds."
  []
  (datomic/squuid))

(defn squuid-time-millis
  "get the time part of a squuid (a UUID created by squuid), in
   the format of System.currentTimeMillis"
  [squuid]
  (datomic/squuid-time-millis squuid))

(defn string->uuid
  "Convert a string to a UUID"
  [stringified-uuid]
  {:pre  [(string? stringified-uuid)]
   :post [(= (type %) java.util.UUID)]}
  (. UUID (fromString stringified-uuid)))

(defn <-string
  ([uuid-string]
     (UUID/fromString uuid-string))
  ([key uuid-string]
     (if (and (= key :validate)
              uuid-string
              (string? uuid-string)
              (not (str/blank? uuid-string))
              (not (= "null" uuid-string)))
       (try+
           (<-string uuid-string)
           (catch java.lang.IllegalArgumentException _
             nil)))))

(defn ->string
  [uuid]
  (.toString uuid))

(defn is?
  [uuid]
  (= java.util.UUID (type uuid)))





