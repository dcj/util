(defproject

  com.dcj.util/http

  "1.0.7-SNAPSHOT"

  :description "HTTP utilities"

  :clean-targets ["pom.xml"]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-http "3.10.0"]
                 [slingshot "0.12.2"]]

  :codox {:output-path "resources/doc/api"}

  )
