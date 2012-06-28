(defproject com.dcj/util "1.0.2-SNAPSHOT"
  
  :description "Libraries of functions I like to use"

  :min-lein-version "2.0.0"

  :repositories {"mvnrepository" "http://mvnrepository.com/"
                 "likestream" 
                 {:url "http://maven.likestream.net/maven2"
                  :update :always
                  :checksum :ignore }
                 }
                 
  :dependencies [
                 [com.dcj.util/util-map "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-convert "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-logging "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-phonenumber "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-puppet "1.0.2-SNAPSHOT"]                 
                 [com.dcj.util/util-counter "1.0.2-SNAPSHOT"]
                 [com.dcj.util/util-configfile "1.0.2-SNAPSHOT"]                 
                 [com.dcj.util/util-time "1.0.2-SNAPSHOT"]
                 ]

  :sub ["util-map"
        "util-convert"
        "util-logging"
        "util-puppet"
        "util-phonenumber"
        "util-counter"
        "util-configfile"
        "util-time"
        "util-uuid"        
        ]

  :codox {:sources ["util-map/src"
                    "util-convert/src"
                    "util-logging/src"
                    "util-phonenumber/src"
                    "util-puppet/src"                    
                    "util-counter/src"
                    "util-configfile/src"
                    "util-time/src"
                    "util-uuid/src"                    
                    ]
           :output-dir "docs/codox"
          }

  )
