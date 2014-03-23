(defproject com.dcj/util "1.0.4"
  
  :description "Libraries of functions I like to use"

  :min-lein-version "2.0.0"

  :repositories {"mvnrepository" "http://mvnrepository.com/"
                 "likestream" 
                 {:url "http://maven.likestream.net/maven2"
                  :update :always
                  :checksum :ignore }
                 }
  
  :extra-files-to-clean ["pom.xml"]                 
  
  :dependencies [
                 [com.dcj.util/map "1.0.4"]
                 [com.dcj.util/convert "1.0.4"]
                 [com.dcj.util/uuid "1.0.4"]
                 [com.dcj.util/ddl "1.0.4"]
                 [com.dcj.util/logging "1.0.4"]
                 [com.dcj.util/phonenumber "1.0.4"]
                 [com.dcj.util/puppet "1.0.4"]                 
                 [com.dcj.util/counter "1.0.4"]
                 [com.dcj.util/ncounter "1.0.4"]
                 [com.dcj.util/configfile "1.0.4"]                 
                 [com.dcj.util/time "1.0.4"]
                 [com.dcj.util/http "1.0.4"]                 
                 [com.dcj.util/debug "1.0.4"]                 
                 [com.dcj.util/introspect "1.0.4"]                 
                 [com.dcj.util/clojure "1.0.4"]                 
                 ]

  :sub [
        "clojure"
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
        ]

  :codox {:sources [
                    "clojure/src"
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
                    ]

          :output-dir "docs/codox"
          }
  )

