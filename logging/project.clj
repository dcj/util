(defproject

  com.dcj.util/logging

  "1.0.6-SNAPSHOT"

  :description "Logging utilities"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.logging "0.4.1"]]

  :clean-targets ["pom.xml"]

  :codox {
          :output-path "resources/doc/api/util/logging"
          }

  )
