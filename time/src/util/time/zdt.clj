(ns util.time.zdt
  "Utilities for dealing with ZonedDateTime"
  (:require [tick.alpha.api :as t]
            [tick.timezone]))


(defn now
  ([]
   (now "UTC"))
  ([tz]
   (t/in (t/now) tz)))


(defn ->ems
  [x]
  (->> x
       t/instant
       (t/new-interval (t/epoch))
       t/duration
       t/millis))


(defn ->es
  [x]
  (->> x
       t/instant
       (t/new-interval (t/epoch))
       t/duration
       t/seconds))


(defn ems->
  ([x]
   (ems-> "UTC" x))
  ([tz x]
   (-> x
       t/instant
       (t/in tz))))


(defn es->
  ([x]
   (es-> "UTC" x))
  ([tz x]
   (ems-> tz (* x 1000))))


(defn create-date-time
  [tz year month day hour minutes & seconds-plus]
  (-> (t/new-date year month day)
      (t/at (apply t/new-time (concat (list hour minutes) seconds-plus)))
      (t/in tz)))


(defn start-of-day
  ([year month day]
   (start-of-day "UTC" year month day))
  ([tz year month day]
   (create-date-time tz year month day 0 0)))


(defn end-of-day
  ([year month day]
   (end-of-day "UTC" year month day))
  ([tz year month day]
   (create-date-time tz year month day 23 59 59)))
