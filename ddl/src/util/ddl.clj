(ns util.ddl
  "SQL DDL utils"
  (:require [clojure.string :as string]
            [slingshot.slingshot :refer [throw+ try+]]))


(defn text-columns
  "Returns vector of subvectors, each subvector containing the column and the :text data type specifier"
  [column-vec]
  (->> column-vec
       (mapv #(vector % :text))))

(defn tbd-columns
  "Returns vector of subvectors, each subvector contains the column, intended for subsequently manually adding/editing the data type specifier"
  [column-vec]
  (->> column-vec
       (mapv vector)))

(defn create-table-preface
  "Returns string of DDL specifying a SQL CREATE TABLE statement"
  [schema-name table-name]
  (println (str "CREATE TABLE "
                    schema-name
                    "."
                    table-name
                    " (")))

  ;; (println (str "CREATE TABLE \""
  ;;                   schema-name
  ;;                   "\".\""
  ;;                   table-name
  ;;                   "\" (")))

(defn create-table-epilogue
  "Returns string of DDL terminating a SQL CREATE TABLE statement"
  ([schema-name table-name]
     (create-table-epilogue schema-name table-name "postgres"))
  ([schema-name table-name owner]
   (println (if owner
              (str ")\nWITH (OIDS=false);\nALTER TABLE \""
                   schema-name
                   "\".\""
                   table-name
                   "\" OWNER TO \""
                   owner
                   "\";")
              (str ");")))))

(defn default-column-datatypes
  "Returns SQL/Postgres data type for the given data-type specifier keyword"
  [key]
  (case key
    :bool "bool"
    :text "text"
    :smallint "smallint"
    :integer "integer"
    :integer-array "integer[]"
    :bigint "bigint"
    :bigint-array "bigint[]"
    :decimal "decimal"
    :numeric "numeric"
    :rating-numeric "numeric(2,1)"
    :real "real"
    :double-precision "double precision"
    :serial "serial"
    :serial-pk "serial primary key"
    :bigserial "bigserial"
    :bigserial-pk "bigserial primary key"
    :timestamp-now "timestamp(6) NOT NULL DEFAULT now()"
    :timestamp "timestamp"
    :varchar "varchar"
    :pk "primary key"
    :primary-key "primary key"
    :!null "NOT NULL"
    :not-null "NOT NULL"
    :date "date"
    :float4 "float4"
    :float8 "float8"
    :uuid "uuid"
    :uuid-array "uuid[]"
    :json "json"
    :hstore "\"public\".\"hstore\""
    :lat-long-numeric "numeric(9,6) DEFAULT NULL::numeric"
    :timezone-numeric "numeric(4,2)"))


(defn create-table-column
  "Returns SQL/Postgres table column DDL from the given column and data-type specifier keywords"
  ([format-map]
   (create-table-column format-map nil #'default-column-datatypes))
  ([format-map last-char]
   (create-table-column format-map last-char #'default-column-datatypes))
  ([{:keys [column column-data-type] :as format-map} last-char column-datatypes-fn]
   (println (str "\t" 
                 (clojure.core/name column) 
                 " "
                 (column-datatypes-fn column-data-type)
                 last-char))))


(defn create-table
  [{:keys [schema table owner columns] :as table-spec}]
  (create-table-preface schema table)
  (doseq [column columns]
    (create-table-column column ","))
  (create-table-epilogue schema table owner))

  
  
  



