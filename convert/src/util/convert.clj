(ns util.convert
  "Type conversion utilities"
  (:require [clojure.string :as str]
            [util.uuid :as uuid]
            [cheshire.core :as json]
            [slingshot.slingshot :refer [throw+ try+]])
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
   (string? thing-to-convert) (when (not (str/blank? thing-to-convert))
                                (try+
                                 (Float/parseFloat thing-to-convert)
                                 (catch java.lang.NumberFormatException _ nil)))))

(defmethod carefully-to :Int-Float
  [_ thing-to-convert]
  (let [my-float (cond
                  (float? thing-to-convert) (float thing-to-convert)
                  (string? thing-to-convert) (when (not (str/blank? thing-to-convert))
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

(defn split-by-whitespace 
  "Returns list of input string split by whitespace"
  [s]
  (clojure.string/split s #"\s+"))

(defn split-by-tab
  "Returns input string split by tab, useful for reading TSV files"
  [s]
  (clojure.string/split s #"\t"))

(defn vectorize-string
  "Returns vector of input string substrings split by comma"
  [string]
  (if string
    (into [] (clojure.string/split string #"\,"))
    []))

(defn vectorize
  "Returns (vec args), unless nil, then []"
  [& args]
  (if-not (or (empty? args)
              (= '(nil) args))
    (vec args)
    []))

(defn parse-json-string-into-vector
  "Returns parsed JSON input string into a Clojure vector"
  [json-string]
  (if json-string
    (when-let [parsed (json/parse-string json-string true)]
      (cond 
       (map? parsed) parsed
       (seq? parsed) (into [] parsed)
       :else (vector parsed)))
    []))

(defn keywordize-string
  "Returns keyword of given string, any spaces in string replaced with hyphens"
  [string]
  (-> string
      (clojure.string/replace #"\s" "-")
      keyword))



