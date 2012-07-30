(ns util.uuid
  "Utilities for dealing with UUIDs"
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:require [clojure.string :as str])
  (:import [java.util UUID]))

;; http://johannburkard.de/software/uuid/#maven
;;
;; [com.eaio.uuid UUID]
;; (defn random-uuid-old "Creates a new (likestream) UUID.  Not sure this is really so great"
;;   []
;;   (. UUID (fromString (.toString (com.eaio.uuid.UUID.)))))

(defn random-uuid
  []
  (UUID/randomUUID))

(defn random
  []
  (UUID/randomUUID))

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

(defn uuid->string
  [uuid]
  (.toString uuid))

(defn is?
  [uuid]
  (= java.util.UUID (type uuid)))





