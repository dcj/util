(defproject com.dcj.util/util-convert "1.0.2-SNAPSHOT"
  :description "Utilities converting between data types"

  :repositories {"likestream" 
                 {:url "http://maven.likestream.net/maven2"
                  :update :always
                  :checksum :ignore }
                 }
  
  :dependencies [
                 [org.clojure/clojure "1.4.0"]
                 [slingshot "0.10.3"]
                 [com.dcj.util/util-uuid "1.0.2-SNAPSHOT"]
                 ]
  )


