(ns util.configfile
  "Read, maintain, store, and access application configuration"
  (:require [clojure.tools.logging :as log]
            [clojure.pprint :as pp]
            [slingshot.slingshot :refer [throw+ try+]]))

(def ^{:private true
       :dynamic true} *config* (ref nil))

(def ^{:private true
       :dynamic true} *env-variable* (ref nil))

(def ^{:private true
       :dynamic true} *file-name* (ref nil))

(defn read-config-file
  "Read the configuration from the given file"
  [filename]
  (log/log :info (str "Loading configuration from file: " filename))
  (if-not filename (throw+ {:type ::invalid-file} (str "Attempted to load from an invalid file: " filename)))
  (binding [*read-eval* false]
    ;; Will throw an exception if the file is not found, or is otherwise
    ;; unreadable.
    (read-string
     (slurp filename))))

(defn read-and-store-config-file!
  "Read the configuration from the given file, and store it"
  ([]
     (read-and-store-config-file! nil))
  ([filename]
     (if-let [read-config (read-config-file filename)]
       (do
         (dosync (ref-set *config* read-config))
         (log/log :info (str "Configuation read from: " filename ", contents as follows:\n" (pp/write read-config :stream nil)))
;;         (log/log :info (pr-str read-config))
         read-config))))

(defn get-config 
  "Returns the configuration, or throws an exception"
  []
  (or @*config*
      (throw+ {:type ::no-active-config} "No configuration loaded.")))

(defn configured?
  "Predicate to test if there is a configuration"
  ([]
   (configured? @*config*))
  ([c]
   (and c (not (empty? c)))))

(defn get-value
  "Returns value of given configuration key"
  ([v]
   (get-value (get-config) v nil))
  ([c v]
   (get-value c v nil))
  ([c v default]
   (get c v default)))

(defn get-values
  "Returns map of given keys with their values"
  ([ks]
   (get-values (get-config) ks))
  ([c ks]
     (select-keys c ks)))

(defn initialize
  "Attempts to read configuration from locations specified in map argument.  Keys include :file-name :env-variable and :on-failure.  If value of :on-failure is nil or :exit, exit is called"
  ([]
     (initialize {}))
  ([{:keys [env-variable file-name on-failure]}]
     {:pre [(or (not (nil? env-variable))
                (not (nil? file-name)))]}
     (log/log :info (str "Reading config file, first trying: " env-variable ", if not found, then: " file-name ", if both fail, then: " (if (or (= :exit on-failure)
                                                                                                                                                (nil? on-failure))
                                                                                                                                          "exit"
                                                                                                                                          (name on-failure))))
     (when-not (configured?)
       (letfn [(load-from-hardwired-file []
                 (dosync (ref-set  *file-name* file-name))
                 (try+
                  (read-and-store-config-file! file-name)
                  (catch [:type ::invalid-file] _
                    (log/log :error (str "Couldn't load config from environment variable: " env-variable "=" (System/getenv env-variable) ", nor hardwired config file: " file-name ". Specified on-failure action: " (name on-failure)))
                    (if (or (= :exit on-failure)
                            (nil? on-failure))
                      (System/exit 1)))
                  (catch Object _
                    (log/error (:throwable &throw-context) "unexpected error")
                    (throw+))))]
         ;; If not set, will fall back to the default file location.
         (if env-variable
           (try+
            (dosync (ref-set *env-variable* env-variable))
            (read-and-store-config-file! (System/getenv env-variable))
            (catch [:type ::invalid-file] _ (load-from-hardwired-file))
            (catch Object _
              (log/error (:throwable &throw-context) "unexpected error")
              (throw+)))
           (load-from-hardwired-file))))))

(defn reload
  "Re-reads configuration from locations specified by args previously supplied on call to initialize"
  []
  (if (configured?)
    (letfn [(reload-from-hardwired-file []
              (try+
               (read-and-store-config-file! @*file-name*)
               (catch [:type ::invalid-file] _
                 (log/log :error (str "Couldn't reload config from environment variable: " @*env-variable* "=" (System/getenv @*env-variable*) " nor hardwired config file: " @*file-name*)))
               (catch Object _
                 (log/error (:throwable &throw-context) "unexpected error")
                 (throw+))))]
      (if @*env-variable*
        (try+
         (read-and-store-config-file! (System/getenv @*env-variable*))
         (catch [:type ::invalid-file] _ (reload-from-hardwired-file))
         (catch Object _
                 (log/error (:throwable &throw-context) "unexpected error")
                 (throw+)))
        (reload-from-hardwired-file)))
    (do
      (prn-str "reload requested, but configuration not initialized!")
      (log/log :error "reload requested, but configuration not initialized"))))




    








