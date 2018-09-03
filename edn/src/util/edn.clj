(ns util.edn
  "EDN I/O"
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :as pprint]))

(defn ->edn
  "Writes (prettyprinted) EDN to stream, no/nil writer returns string"
  ([edn]
   (->edn edn nil))
  ([edn writer]
   (pprint/write edn :stream writer)))

(defn ->file
  "Writes (prettyprinted) EDN to file"
  [edn file]
  (with-open [w (-> file io/file io/writer)]
    (->edn edn w)))
              
(defn file->
  "Reads file and returns EDN"
  [file]
  (with-open [r (-> file io/file io/reader)]
    (->> r
         slurp
         edn/read-string)))
