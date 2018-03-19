(defproject com.dcj.util/ddl "1.0.5"
  :description "Utilities for creating SDL DDLs"

  ;; :repositories {"likestream" 
  ;;                {:url "http://maven.likestream.net/maven2"
  ;;                 :update :always
  ;;                 :checksum :ignore }
  ;;                }

  ;;  :clean-targets ["pom.xml"] 
  
  :dependencies [
                 [org.clojure/clojure "1.9.0"]
                 [slingshot "0.12.2"]
                 ]

  :codox {
          :output-path "resources/doc/api"
          }
  
  )


