(ns util.time
  "Utilities for dealing with time"
  (:import [java.util Date]
           [java.sql Timestamp]))

(defn right-now
  "Returns the time right now"
  []
  (Date.))

(defn right-now-sql-timestamp
  "Returns the time right now as a java.sql.Timestamp, useful for Postgres"
  []
  (java.sql.Timestamp. (.getTime (java.util.Date.))))

(defn date->string
  "Converts java.util.Date to a string"
  [date]
  (.toString date))

(defn string->date
  "Converts a string to java.util.Date"
  [string]
  (Date. string))



