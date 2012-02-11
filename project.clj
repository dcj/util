(defproject com.dcj/util "1.0.0-SNAPSHOT"
  
  :description "Libraries of functions I like to use"

  :repositories {"likestream" "http://maven.likestream.net/maven2/"}  

  :dependencies [
                 [com.dcj.util/util-map "1.0.0-SNAPSHOT"]
                 [com.dcj.util/util-convert "1.0.0-SNAPSHOT"]
                 [com.dcj.util/util-phonenumber "1.0.0-SNAPSHOT"]
                 [com.dcj.util/util-puppet "1.0.0-SNAPSHOT"]                 
                 [com.dcj.util/util-counter "1.0.0-SNAPSHOT"]
                 [com.dcj.util/util-configfile "1.0.0-SNAPSHOT"]                 
                 [com.dcj.util/util-time "1.0.0-SNAPSHOT"]
                 ]

  :dev-dependencies [
                     [lein-sub "0.1.2"]
                     [codox "0.4.0"]
                     [lein-marginalia "0.6.1"]
                     ]

  :sub ["util-map"
        "util-convert"
        "util-phonenumber"
        "util-puppet"        
        "util-counter"
        "util-configfile"
        "util-time"
        "util-uuid"        
        ]

  :codox {:sources ["util-map/src"
                    "util-convert/src"
                    "util-phonenumber/src"
                    "util-puppet/src"                    
                    "util-counter/src"
                    "util-configfile/src"
                    "util-time/src"
                    "util-uuid/src"                    
                    ]})
