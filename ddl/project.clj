(defproject com.dcj.util/ddl "1.0.4"
  :description "Utilities for creating SDL DDLs"

  :repositories {"likestream" 
                 {:url "http://maven.likestream.net/maven2"
                  :update :always
                  :checksum :ignore }
                 }

  :clean-targets ["pom.xml"] 
  
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [slingshot "0.10.3"]
                 ]
  )


