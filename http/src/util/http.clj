(ns util.http
  "HTTP Utilities"
  (:require [slingshot.slingshot :refer [throw+ try+]]
            [clj-http.client :as http.client]
            [clojure.java.io :as io])
  (:import [org.apache.http.impl.cookie DateUtils DateParseException]
           [java.io File]))

(defn url-string
  "Returns a string representing the url path of the input"
  [url-vec]
  (if (or (vector? url-vec)
          (list? url-vec))
    (apply str (interpose "/" (map name url-vec)))
    (name url-vec)))

(defn response-map?
  "Predicate that attempts to verify that the given response map came from HTTP.  This is a total hack!"
  [hrm]
  (get hrm :status false))

(defn ^:private parse-header-date
  [date-string]
  (->> date-string
       parseDate
       (. DateUtils)))
;; (. DateUtils (parseDate date-string)))

;; Former name: filter-header-times
(defn get-header-times
  "Returns HTTP response headers that contain date-times, [:date :expires :last-modified]"
  [header-map]
  (select-keys header-map [:date :expires :last-modified]))

;; Former name: parse-header-times
(defn add-parsed-header-times
  "Replaces value of [:date :expires :last-modified] keys with parsed value, and nil on DateParseException"
  [header-map]
  (apply merge
	 (for [[k v] (get-header-times header-map)]
	   (try+
	    {k (parse-header-date v)}
	    (catch DateParseException _
	      nil)))))


;; Moved to util.http.status
;; (defn status-range
;;   "Returns most significant digit of http-response-code, assumes 3 digit response codes"
;;   [http-response-code]
;;   (quot http-response-code 100))

;; Moved to util.http.status
;; (defn status-in-range?
;;   "Returns true if the given HTTP-response-code begins with digit, an Integer."
;;   [digit http-response-code]
;;   (= digit (status-range http-response-code)))


(defn copy-uri-to-file
  [uri file]
  (with-open [out (io/output-stream file)]
    (io/copy (:body (http.client/get uri {:as :stream}))
             out)))

(defn download-unzip
  [url dir]
  (let [saveDir (File. dir)]
    (with-open [stream (-> (http.client/get url {:as :stream})
                           (:body)
                           (java.util.zip.ZipInputStream.))]
      (loop [entry (.getNextEntry stream)]
        (if entry
          (let [savePath (str dir File/separatorChar (.getName entry))
                saveFile (File. savePath)]
            (if (.isDirectory entry)
              (if-not (.exists saveFile)
                (.mkdirs saveFile))
              (let [parentDir (File. (.substring savePath 0 (.lastIndexOf savePath (int File/separatorChar))))]
                (if-not (.exists parentDir) (.mkdirs parentDir))
                (clojure.java.io/copy stream saveFile)))
            (recur (.getNextEntry stream))))))))

(defn head-success?
  [url]
  (-> url
      (http.client/head {:throw-exceptions false})
      http.client/success?))

;; https://stackoverflow.com/questions/11321264/saving-an-image-form-clj-http-request-to-file
;; https://stackoverflow.com/questions/32742744/how-to-download-a-file-and-unzip-it-from-memory-in-clojure
