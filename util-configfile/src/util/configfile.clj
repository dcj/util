(ns util.configfile
  "Maintain and read application configuration information"
  (:require [clojure.tools.logging :as log]))

(def ^{:private true
       :dynamic true} *config* (ref nil))

(defn load-config [p]
  (binding [*read-eval* false]
    ;; Will throw an exception if the file is not found, or is otherwise
    ;; unreadable.
    (read-string
     (slurp p ))))

(defn load-config!
  ([]
     (load-config! nil))
  ([p]
     (let [read-config (load-config p)]
       (dosync
        (ref-set *config* read-config))
       (log/log :info (str "Config is " (prn-str read-config))))))

(defn config []
  (or @*config*
      (throw (new Exception "No config loaded."))))

(defn configured?
  ([]
   (configured? @*config*))
  ([c]
   (and c (not (empty? c)))))

(defn configured-value
  ([v]
   (configured-value (config) v nil))
  ([c v]
   (configured-value c v nil))
  ([c v default]
   (get c v default)))

(defn configured-values
  ([ks]
   (configured-values (config) ks))
  ([c ks]
     (select-keys c ks)))

(defn read-config-file
  [{:keys [env-variable file-name]}]
  {:pre [(or (not (nil? env-variable))
             (not (nil? file-name)))]}
  (log/log :info "Running initial setup.")
  (when-not (configured?)
    ;; If not set, will fall back to the default location.
    (if-let [env-var-val (System/getenv env-variable)]
      (do
        (log/log :info (str "Loading config from " env-var-val))
        (load-config! env-var-val))
      (try
        (log/log :info (str "Loading config from " file-name))
        (load-config! file-name)
        (catch Throwable e
          (log/log :fatal "Couldn't load config from environment variable nor hardwired config file. Dying.")
          (System/exit 1))))))








