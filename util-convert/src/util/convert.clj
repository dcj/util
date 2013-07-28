(ns util.convert
  "Type conversion utilities"
  (:use [slingshot.slingshot :only [throw+ try+]])
  (:require [clojure.string :as str]
            [util.uuid :as uuid])
  (:import [java.util UUID]))

(defn string->uuid
  "Convert a string to a UUID"
  [stringified-uuid]
  {:pre  [(string? stringified-uuid)]
   :post [(= (type %) java.util.UUID)]}
  (UUID/fromString stringified-uuid))

(defn string->float
  "Convert a string to a Float"
  [stringified-float]
  {:pre [(string? stringified-float)]}
  (Float/parseFloat stringified-float))

(defn string->bool
  "Convert a string of yes to boolean true, and a string of no to boolean false"
  [yes-or-no-string]
  (cond
   (= yes-or-no-string "yes") true
   (= yes-or-no-string "no")  false))

(defn uuid->string
  [uuid]
  (.toString uuid))

(defn long->string
  [long]
  (.toString long))

;; From relevence.utils - still necessary?
(defn #^String safe-str [x]
  (when x
    (if (string? x)
      x
      (if (instance? clojure.lang.Named x)
        (name x)
        (str x)))))

(defn nilify-blanks [input]
  (when (seq input) input))

;; TODO anyplace the below us used, needs to be fixed
(defn ensure-string-id [id]
  (let [id-type (type id)]
    (if (or (= java.lang.Long id-type)
            (= java.lang.Integer id-type))
      (.toString id)
      id)))

;; TODO anyplace the below us used, needs to be fixed
(defn ensure-string-uuid [uuid]
  (if (= java.util.UUID (type uuid))
    (.toString uuid)
    uuid))

(defn- all-digit-string?
  [istring]
  (empty? (drop-while #(Character/isDigit %)
                      (seq istring))))  ;; all characters are digits, but must follow blank?

(defmulti carefully-to
  (fn [target-key &rest]
    target-key))

(defmethod carefully-to :Integer
  [_ thing-to-convert]
  (cond
   (integer? thing-to-convert) (int thing-to-convert)
   (string? thing-to-convert) (when (and (not (str/blank? thing-to-convert))
                                         (all-digit-string? thing-to-convert))
                                (try+
                                 (Integer/parseInt thing-to-convert)
                                 (catch java.lang.NumberFormatException _ nil)))))

(defmethod carefully-to :Long
  [_ thing-to-convert]
  (cond
   (integer? thing-to-convert) (long thing-to-convert)
   (string? thing-to-convert) (when (and (not (str/blank? thing-to-convert))
                                         (all-digit-string? thing-to-convert))
                                (try+
                                 (Long/parseLong thing-to-convert)
                                 (catch java.lang.NumberFormatException _ nil)))))

(defmethod carefully-to :Float
  [_ thing-to-convert]
  (cond
   (float? thing-to-convert) (float thing-to-convert)
   (string? thing-to-convert) (when (and (not (str/blank? thing-to-convert))
                                         (all-digit-string? thing-to-convert))
                                (try+
                                 (Float/parseFloat thing-to-convert)
                                 (catch java.lang.NumberFormatException _ nil)))))

(defmethod carefully-to :Int-Float
  [_ thing-to-convert]
  (let [my-float (cond
                  (float? thing-to-convert) (float thing-to-convert)
                  (string? thing-to-convert) (when (and (not (str/blank? thing-to-convert))
                                                        (all-digit-string? thing-to-convert))
                                               (try+
                                                (Float/parseFloat thing-to-convert)
                                                (catch java.lang.NumberFormatException _ nil))))]
    (when my-float
     (int my-float))))
    

(defmethod carefully-to :UUID
  [_ thing-to-convert]
  (cond
   (uuid/is? thing-to-convert) thing-to-convert
   (string? thing-to-convert) (uuid/<-string :validate thing-to-convert)))




