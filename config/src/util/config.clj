(ns util.config
  "Read configuration via environ"
  (:require [util.edn]
            [environ.core :refer [env]]))

(defn env-var-defined?
  "Is the given enviornment variable defined? Lookup by enviorn/env"
  [env-var]
  (-> env-var
      env
      nil?
      not))

(defn ^:private str->boolean
  "Converts strings \"true\" and \"false\" to boolean, else throws exception"
  [s]
  (case s
    "true" true
    "false" false))

(defn get-env-var
  "environ/env with not-found if env-var not defined"
  ([env-var]
   (get-env-var env-var nil))
  ([env-var not-found]
   (if (env-var-defined? env-var)
     (-> env-var env str->boolean)
     not-found)))

(defn get-edn-file-from-env-var
  "Contents of EDN file resolved by environ/env"
  [env-var]
  (-> env-var
      env
      util.edn/file->))

(defn resolve-env-var-values
  "Replaces values in m that are keywords with enviorn/env lookup of such values"
  [m]
  (reduce-kv (fn [acc k v]
               (if (keyword? v)
                 (assoc acc k (env v))
                 (assoc acc k v)))
             (hash-map)
             m))
