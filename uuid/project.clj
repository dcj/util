(defproject com.dcj.util/uuid "1.0.4"

  :description "UUID utilities"

  :aot :all

;;  :clean-targets ["pom.xml"]

  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [slingshot "0.10.3"]
                 ;; [com.datomic/datomic-free "0.9.4609" :exclusions [org.slf4j/log4j-over-slf4j
                 ;;                                                   org.slf4j/slf4j-nop
                 ;;                                                   org.slf4j/jcl-over-slf4j
                 ;;                                                   org.slf4j/jul-to-slf4j]]
                 ]
  )

;;		 [com.eaio.uuid/uuid "3.2"]




