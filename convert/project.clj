(defproject com.dcj.util/convert "1.0.4"
  :description "Utilities converting between data types"

  :repositories {"likestream" 
                 {:url "http://maven.likestream.net/maven2"
                  :update :always
                  :checksum :ignore }
                 }

  :aot :all

  :clean-targets ["pom.xml"] 
  
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [slingshot "0.10.3"]
                 [com.dcj.util/uuid "1.0.4"]
                 [cheshire "5.3.1"]
                 ]
  )


