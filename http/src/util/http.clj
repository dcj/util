(ns util.http
  "HTTP Utilities"
  (:require [slingshot.slingshot :refer [throw+ try+]])
  (:import [org.apache.http.impl.cookie DateUtils DateParseException]))

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

