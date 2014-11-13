(ns util.http
  "Utilities for dealing with HTTP"
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:import  [org.apache.http.impl.cookie DateUtils DateParseException]))

(defn status-range
  "Returns most significant digit of http-response-code, assumes 3 digit response codes"
  [http-response-code]
  (quot http-response-code 100))
  
(defn status-in-range?
  "Returns true if the given HTTP-response-code begins with digit, an Integer."
  [digit http-response-code]
  (= digit (status-range http-response-code)))

(defn response-map?
  "Predicate that attempts to verify that the given response map came from HTTP.  This is a total hack!"
  [hrm]
  (get hrm :status false))

(defn parse-header-date
  [date-string]
  (. DateUtils (parseDate date-string)))

(defn filter-header-times
  [header-map]
  (select-keys header-map [:date :expires :last-modified]))

(defn parse-header-times
  "The idea here is to catch the DateParseException, return nil, and move on....."
  [header-map]
  (apply merge
	 (for [[k v] (filter-header-times header-map)]
	   (try+
	    {k (parse-header-date v)}
	    (catch DateParseException _
	      nil)))))



