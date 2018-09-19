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

#_
(defmacro time
  "Evaluates expr and prints the time it took.  Returns the value of
 expr."
  {:added "1.0"}
  [expr]
  `(let [start# (. System (nanoTime))
         ret# ~expr]
     (prn (str "Elapsed time: " (/ (double (- (. System (nanoTime)) start#)) 1000000.0) " msecs"))
     ret#))
