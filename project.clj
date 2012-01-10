(defproject com.dcj/util "1.0.0-SNAPSHOT"
  :description "Libraries of functions I like to use"
  :dependencies [[com.dcj/util/util-map "1.0.0-SNAPSHOT"]
                 [com.dcj/util/util-convert "1.0.0-SNAPSHOT"]
                 [com.dcj/util/util-phonenumber "1.0.0-SNAPSHOT"]
                 [com.dcj/util/util-counter "1.0.0-SNAPSHOT"]
;;               [util/util-time "1.0.0-SNAPSHOT"]
                 ]
  :dev-dependencies [[lein-sub "0.1.2"]
                     [codox "0.3.3"]]
  :sub ["util-map"
        "util-convert"
        "util-phonenumber"
        "util-counter"
        ;;      "util-time"
        ]
  :codox {:sources ["util-map/src"
                    "util-convert/src"
                    "util-phonenumber/src"
                    "util-counter/src"
;;                  "util-time/src"
                    ]})
