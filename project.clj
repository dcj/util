(defproject com.dcj/util "1.0.2-SNAPSHOT"
  
  :description "Libraries of functions I like to use"

  :min-lein-version "2.0.0"

  :repositories {"mvnrepository" "http://mvnrepository.com/"
                 "likestream" 
                 {:url "http://maven.likestream.net/maven2"
                  :update :always
                  :checksum :ignore }
                 }
  
  :extra-files-to-clean ["pom.xml" "lib"]                 
  
  :dependencies [
                 [com.dcj.util/util-map "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-convert "1.0.3-SNAPSHOT"]
                 [com.dcj.util/util-ddl "1.0.3-SNAPSHOT"]
                 [com.dcj.util/util-logging "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-phonenumber "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-puppet "1.0.2-SNAPSHOT"]                 
                 [com.dcj.util/util-counter "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-configfile "1.0.2-SNAPSHOT"]                 
                 [com.dcj.util/util-time "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-http "1.0.2-SNAPSHOT"]                 
                 [com.dcj.util/util-debug "1.0.2-SNAPSHOT"]                 
                 [com.dcj.util/util-introspect "1.0.2-SNAPSHOT"]                 
                 [com.dcj.util/util-clojure "1.0.2-SNAPSHOT"]                 
                 ]

  :sub [
        "util-clojure"
        "util-configfile"
        "util-convert"
        "util-ddl"
        "util-counter"
        "util-debug"
        "util-http"
        "util-introspect"
        "util-logging"
        "util-map"
        "util-phonenumber"
        "util-puppet"
        "util-time"
        "util-uuid"
        ]

  :codox {:sources [
                    "util-clojure/src"
                    "util-configfile/src"
                    "util-convert/src"
                    "util-ddl/src"
                    "util-counter/src"
                    "util-debug/src"
                    "util-http/src"
                    "util-introspect/src"
                    "util-logging/src"
                    "util-map/src"
                    "util-phonenumber/src"
                    "util-puppet/src"
                    "util-time/src"
                    "util-uuid/src"
                    ]

          :output-dir "docs/codox"
          }
  )

