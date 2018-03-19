(defproject com.dcj.util/uuid "1.0.5"

  :description "UUID utilities"

  :aot :all

;;  :clean-targets ["pom.xml"]

  :dependencies [
                 [org.clojure/clojure "1.9.0"]
                 [slingshot "0.12.2"]
                 ;; [com.datomic/datomic-free "0.9.4609" :exclusions [org.slf4j/log4j-over-slf4j
                 ;;                                                   org.slf4j/slf4j-nop
                 ;;                                                   org.slf4j/jcl-over-slf4j
                 ;;                                                   org.slf4j/jul-to-slf4j]]
                 ]

  :codox {
          :output-path "resources/doc/api"
          }
  
  )

;;		 [com.eaio.uuid/uuid "3.2"]




