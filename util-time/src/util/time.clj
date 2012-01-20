(ns util.time
  "Utilities for dealing with time"
;;  (:use 'clj-time.core)
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




