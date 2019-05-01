(ns util.json
  "JSON conversion and I/O"
  (:require ;; [inet.data.ip :as ip]
            ;; [inet.data.dns :as dns]
            [cheshire.core :as cheshire]
            [cheshire.generate]
            [clojure.java.io :as io]))

(defn ->edn
  "Returns parsed JSON as EDN, keywordized by default, set optional 2nd arg to false for string keys"
  ([json]
   (->edn json true))
  ([json keywordize?]
   (cheshire/parse-string json keywordize?)))

(defn file->edn
  "Reads file, returns parsed JSON as EDN, keywordized by default, set optional 2nd arg to false for string keys"
  ([file]
   (file->edn file true))
  ([file keywordize?]
   (with-open [r (-> file io/file io/reader)]
     (-> r
         slurp
         (->edn keywordize?)))))

;; (cheshire.generate/add-encoder
;;  inet.data.dns.DNSDomain
;;  (fn [c jsonGenerator]
;;    (.writeString jsonGenerator (str c))))

;; (cheshire.generate/add-encoder
;;  inet.data.ip.IPAddress
;;  (fn [c jsonGenerator]
;;    (.writeString jsonGenerator (str c))))

;; (cheshire.generate/add-encoder
;;  inet.data.ip.IPNetwork
;;  (fn [c jsonGenerator]
;;    (.writeString jsonGenerator (str c))))

(defn ->json
  "Returns JSON string, prettyprinted by default, set optional 2nd arg to false to forego prettyprinting"
  ([edn]
   (->json true edn))
  ([pretty? edn]
   (cheshire/generate-string edn {:pretty pretty?})))

(defn ->file
  "Writes JSON to file, prettyprinted by default, set optional 3rd arg to false to forego prettyprinting"
  ([edn file]
   (->file edn file true))
  ([edn file pretty?]
   (->> edn
        (->json pretty?)
        (spit file))))
