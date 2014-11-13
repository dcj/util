(ns util.logging
  "Logging utilities"
  (:require [clojure.tools.logging :as log]))

(defn #^String msecs
  ([duration]
     (str (/ (double duration)
          1000000.0)
          "msec."))
  ([start end]
     (msecs (- end start))))

(defmacro with-duration [[d results form] & body]
  `(let [start# (. java.lang.System (clojure.core/nanoTime))
         ~results ~form
         end# (. java.lang.System (clojure.core/nanoTime))
         ~d (- end# start#)]
     ~@body))

(defmacro time-call [s call]
  `(with-duration [d# r# ~call]
     (log/log :info (str ~s (msecs d#)))
     r#))

