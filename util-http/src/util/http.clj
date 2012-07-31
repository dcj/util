(ns util.http
  "Utilities for dealing with HTTP"
  (:use [slingshot.slingshot :only [throw+ try+]]))

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



