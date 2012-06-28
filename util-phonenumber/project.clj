(defproject com.dcj.util/util-phonenumber "1.0.2-SNAPSHOT"
  
  :description "Utilities for manipulating telephone numbers"

  :repositories {"likestream" 
                 {:url "http://maven.likestream.net/maven2"
                  :update :always
                  :checksum :ignore }
                 }
  
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.dcj.util/util-map "1.0.2-SNAPSHOT"]])

