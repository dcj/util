(defproject

  com.dcj.util/config

  "1.0.6-SNAPSHOT"

  :description "Read configuration via environ"
  
  :clean-targets ["pom.xml"] 

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [com.dcj.util/edn "1.0.6-SNAPSHOT"]
                 [environ "1.1.0"]]
  
  :codox {:output-path "resources/doc/api"}

  )







