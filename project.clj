(defproject

  com.dcj/util

  "1.0.6-SNAPSHOT"
  
  :description "Libraries of functions I like to use"

  :min-lein-version "2.0.0"

  :repositories {"mvnrepository" "https://mvnrepository.com/"
                 ;; "likestream" 
                 ;; {:url "http://maven.likestream.net/maven2"
                 ;;  :update :always
                 ;;  :checksum :ignore }
                 }
  
;;  :extra-files-to-clean ["pom.xml"]                 
  
  :dependencies [[com.dcj.util/map "1.0.6-SNAPSHOT"]
                 [com.dcj.util/convert "1.0.6-SNAPSHOT"]
                 [com.dcj.util/uuid "1.0.6-SNAPSHOT"]
                 [com.dcj.util/ddl "1.0.6-SNAPSHOT"]
                 [com.dcj.util/logging "1.0.6-SNAPSHOT"]
                 [com.dcj.util/phonenumber "1.0.6-SNAPSHOT"]
                 [com.dcj.util/puppet "1.0.6-SNAPSHOT"]                 
                 [com.dcj.util/counter "1.0.6-SNAPSHOT"]
                 [com.dcj.util/ncounter "1.0.6-SNAPSHOT"]
                 [com.dcj.util/configfile "1.0.6-SNAPSHOT"]                 
                 [com.dcj.util/time "1.0.6-SNAPSHOT"]
                 [com.dcj.util/http "1.0.6-SNAPSHOT"]                 
                 [com.dcj.util/debug "1.0.6-SNAPSHOT"]                 
                 [com.dcj.util/introspect "1.0.6-SNAPSHOT"]                 
                 [com.dcj.util/clojure "1.0.6-SNAPSHOT"]
                 [com.dcj.util/facter "1.0.6-SNAPSHOT"]
                 [com.dcj.util/edn "1.0.6-SNAPSHOT"]
                 [com.dcj.util/json "1.0.6-SNAPSHOT"]
                 [com.dcj.util/yaml "1.0.6-SNAPSHOT"]
                 [com.dcj.util/config "1.0.6-SNAPSHOT"]]

  :sub ["clojure"
        "configfile"
        "convert"
        "ddl"
        "counter"
        "ncounter"
        "debug"
        "http"
        "introspect"
        "logging"
        "map"
        "phonenumber"
        "puppet"
        "time"
        "uuid"
        "facter"
        "edn"
        "json"
        "yaml"
        "config"]

  :codox {:sources ["clojure/src"
                    "configfile/src"
                    "convert/src"
                    "ddl/src"
                    "counter/src"
                    "ncounter/src"
                    "debug/src"
                    "http/src"
                    "introspect/src"
                    "logging/src"
                    "map/src"
                    "phonenumber/src"
                    "puppet/src"
                    "time/src"
                    "uuid/src"
                    "facter/src"
                    "edn/src"
                    "json/src"
                    "yaml/src"
                    "config/src"]

          :output-dir "resources/doc/api"}
  )

