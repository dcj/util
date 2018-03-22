(ns util.yaml
  "YAML conversion and I/O"
  (:require [clj-yaml.core :as yaml]
            [clojure.java.io :as io]))

(defn ^:private vectorize-list-values
  "Recursively transforms all map values from lists to vectors."
  [m]
  (let [f (fn [[k v]] (if (seq? v) [k (clojure.core/vec v)] [k v]))]
    ;; only apply to maps
    (clojure.walk/postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn ->edn
  "Returns parsed YAML as EDN"
  [yaml]
  (->> yaml
       yaml/parse-string
       (mapv vectorize-list-values)))

(defn file->edn
  "Reads file, returns parsed YAML as EDN"
  [file]
  (with-open [r (-> file io/file io/reader)]
    (-> r
        slurp
        ->edn)))

(defn list-file->edn
  "Reads file, returns vector of keywordized items of the YAML list therein"
  [file]
  (->> file
       file->edn
       (mapv keyword)))

(defn ->yaml
  "Returns YAML string"
  [edn]
  (yaml/generate-string edn :dumper-options {:flow-style :block}))

(defn ->file
  "Writes generated YAML to file"
  [edn file]
  (->> edn
       ->yaml
       (spit file)))


