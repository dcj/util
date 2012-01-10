(ns util.phonenumber
  "Utility functions for manipulating telephone numbers, from Richard Newman"
  (:use util.phonenumber.e164))

;; TODO: provide a higher-level interface that accepts countries or
;; other 'soft' input, looking up the country code on your behalf.

;;;
;;; Utilities.
;;;

(defn- assoc!-when
  "Like assoc!, but skips keys with null values."
  ([m k v]
    (if (nil? v)
      m
      (assoc! m k v)))
  ([m k v & kvs]
     (if k
       (apply assoc!-when
              (assoc!-when m k v)
              kvs)
       m)))

(defn- assoc-when [m & args]
  (persistent! (apply assoc!-when (transient m) args)))

(defn- #^String skip-re
  "Skip the first part of `str` if it matches `re`."
  [#^java.util.regex.Pattern re #^String str]
  (if re
    (let [#^String match? (re-find re str)]
      (if match?
        (.substring str (.length match?))
        str))
    str))

;;;
;;; Data mappings to allow recognition of countries and prefixes.
;;;

(def ^{:private true} cc-prefix-lengths
     ;; CC -> [skip, prefix].
     ;; If prefix is nil, don't take any.
     ;; If skip is a regex, skip it.
     {971 [nil 1]       ; Dubai.
      44  [#"^0" nil]   ; Variable length prefixes.
      1   [#"^1" 3]     ; No NANP code starts with 1, so it must be an
                        ; erroneous long-distance prefix.
      })


;;;
;;; Phone number munging.
;;;

(defn- skip-and-prefix
  "Skip the specified optional bits of the string, pull out the right prefix
   length, and return [prefix num]."
  [cc str]
  (let [found
        (get cc-prefix-lengths cc :not-found)]
    (if (= found :not-found)
      [nil str]
      (let [[skip pref-count] found]
        (let [candidate
              (skip-re skip str)]
          ;; Take the prefix.
          (if (and pref-count
                   (> (.length candidate) pref-count))
            [(.substring candidate 0 pref-count)
             (.substring candidate pref-count)]
            [nil candidate]))))))

(defn- extract-cc
  "Return the CC (integer) and remaining number string."
  [#^String cc-str #^String num-str]
  ;;(println "Extract CC: " cc-str "," num-str)
  (let [[cc offset] (str->cc cc-str)]
    (let [out
          (when cc
            [cc (if (>= offset (.length cc-str))
                  num-str
                  (if num-str
                    (.concat (.substring cc-str offset) num-str)
                    (.substring cc-str offset)))])]
      out)))

(defn- clean-number-str [#^String s]
  (when s
    (let [#^java.util.regex.Matcher m (.matcher #"[- \.\(\)]" s)]
      (.replaceAll m ""))))

;;;
;;; Main functions.
;;;

;; It would be nice to remove some of the redundancy from this. Oh
;; well.
(defn- normalize-phone-from-parts
  [default-cc cc-str prefix-str num-str ext-str]
             
  ;; There are several possibilities here.
  ;; Firstly, we could have a full "spread out" number, where
  ;; we have a CC, prefix, and number. (E.g., +1 (408) 111
  ;; 2222.)
  ;; Secondly, we could have a partial number, where some of
  ;; the number and/or prefix is pulled into earlier
  ;; segments.
  
  (if (and prefix-str num-str)
    (let [[cc remaining-number]
          (if cc-str
            (extract-cc cc-str prefix-str)
            [default-cc prefix-str])]
      (when cc
        (assoc-when {:cc cc}
                    :x ext-str
                    :number (clean-number-str num-str)
                    :prefix (or remaining-number
                                prefix-str))))
               
    (let [left-over (or prefix-str num-str)]
      (let [[cc remaining-number]
            (if cc-str
              (extract-cc cc-str left-over)
              [default-cc left-over])]
        (when cc
          (let [[prefix remainder]
                (skip-and-prefix cc (clean-number-str
                                     remaining-number))]
            (assoc-when {:cc cc}
                        :x ext-str
                        :number remainder
                        :prefix prefix)))))))


(defn normalize-phone
  "Accept country codes and various groupings of digits.
   Returns a map, {:cc _ :prefix _ :number _}.
   Any part can be nil."
  ([#^String p default-cc]

     ;; Rules:
     ;; * If there's a "tel:" at the front, ignore it. This function
     ;;   still works for valid tel URIs.
     ;; * Exclude leading and trailing whitespace.
     ;; * Dots and dashes are noise, but could inform grouping of
     ;;   prefix.
     ;; * Parens also inform grouping.
     ;; * A plus is an indicator of a country code, which we extract.
     ;; * An 'x' or ';ext=' indicates a numeric extension.

     ;; I know! Let's use regular expressions.
     ;;
     ;; Accept:
     ;; * Whitespace, then
     ;; * An optional country code, then whitespace, then
     ;; * An optional parenthesized prefix, then WS, then
     ;; * Any number of dots, dashes, and numbers, then
     ;; * An optional extension, then more whitespace.

     ;; CCs can be at most three digits in length. This needs
     ;; additional parsing, because input can be "+1234567890".
     
     (let [basic-number-regex
           #"\s*(?:tel:)?(?:(?:00|\+)\s*([0-9]{0,3}))?\s*(?:\(\s*([0-9]+)\s*\)\s*)?\s*([- .0-9]+)\s*(?:(?:x|;ext=)([0-9]+))?\s*"]
       (let [[matched? cc-str prefix-str num-str ext-str]
             (re-matches
              basic-number-regex p)]

         (when (or num-str prefix-str cc-str)
           (normalize-phone-from-parts
            default-cc
            cc-str prefix-str num-str ext-str)))))
  
  
  ([#^String p]
     (normalize-phone p 1)))

(defn phone->uri
  ([m]
     (phone->uri m 1))
  ([m default-cc]
     (if (string? m)
       (phone->uri (normalize-phone m default-cc))
       (when-let [number (:number m)]
         (str "tel:+"
              (:cc m) (:prefix m)
              number
              (when (:x m)
                ";ext=")
              (:x m))))))

(defn phone->e164
  ([m]
     (phone->e164 m false 1))
  ([m allow-extension?]
     (phone->e164 m allow-extension? 1))
  ([m allow-extension? default-cc]
     (if (string? m)
       (phone->e164 (normalize-phone m default-cc) allow-extension? default-cc)
       (when-let [number (:number m)]
         (str "+"
              (:cc m) (:prefix m)
              number
              (when (and allow-extension? (:x m))
                (str ";ext=" (:x m))))))))

(defn number-partition [#^String n cc]
  (when n
    (if (and (= cc 1)
             (= (.length n) 7))
      (str (.substring n 0 3)
           "-"
           (.substring n 3))
      n)))

(defn phone->us-format
  ([m]
     (phone->us-format m 1 1))
  ([m default-cc]
     (phone->us-format m default-cc 1))
  ([m default-cc native-cc]
     (if (string? m)
       (phone->us-format (normalize-phone m default-cc))
       (when-let [number (:number m)]
         (str (when-not (= (:cc m)
                           native-cc)
                (str "+" (:cc m) " "))
              (when (:prefix m)
                (str "(" (:prefix m) ") "))
              (number-partition number (:cc m))
              (when (:x m)
                (str " x" (:x m))))))))

(defn phone->controlling-country
  [m default-cc]
  (if (string? m)
    (phone->controlling-country (normalize-phone m default-cc) nil)
    (let [cc (:cc m)]
      ;; TODO: could use prefix for additional determination for
      ;; small territories.
      (get itu-code->primary-country-name
           cc))))

;; Tests.
#_
(let [phone-expected
      [[["3309207530"] {:cc 1 :prefix "330" :number "9207530"}]
       [["(330)644-2239"] {:cc 1 :prefix "330" :number "6442239"}]
       [["(330) 644-2239"] {:cc 1 :prefix "330" :number "6442239"}]
       [["330-262-2489"] {:cc 1 :prefix "330" :number "2622489"}]
      
       ;; Extensions.
       [["4043650410x22"] {:cc 1 :prefix "404" :number "3650410" :x "22"}]
      
       ;; Dubai.
       [["+97143172222"] {:cc 971 :prefix "4" :number "3172222"}]
       [["4 3172222" 971] {:cc 971 :prefix "4" :number "3172222"}]
       [["009714501777"] {:cc 971 :prefix "4" :number "501777"}]

       ;; UK. Variable-length prefixes = no chance without a lookup table.
       [[" 0207.240.8085" 44] {:cc 44 :number "2072408085"}]]]

  (doseq [[args expected] phone-expected]
    (let [out (apply normalize-phone args)]
      (when-not (= out expected)
        (println out expected)))))
