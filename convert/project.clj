(defproject com.dcj.util/convert "1.0.5"
  :description "Utilities converting between data types"

  ;; :repositories {"likestream" 
  ;;                {:url "http://maven.likestream.net/maven2"
  ;;                 :update :always
  ;;                 :checksum :ignore }
  ;;                }

  ;; :aot :all

  ;;  :clean-targets ["pom.xml"] 
  
  :dependencies [
                 [org.clojure/clojure "1.9.0"]
                 [slingshot "0.12.2"]
                 [com.dcj.util/uuid "1.0.5"]
                 [cheshire "5.8.0"]
                 ]

  :codox {
          :output-path "resources/doc/api"
          }

  )


