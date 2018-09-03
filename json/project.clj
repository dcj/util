(require 'cemerick.pomegranate.aether)
(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))

(defproject

  com.dcj.util/json

  "1.0.6-SNAPSHOT"

  :description "JSON conversion and I/O utilities"

  :repositories [["snapshots"
                  {:url "http://artifactory.dc.drivescale.com:8081/artifactory/libs-snapshot-local"
                   :username "jenkins"
                   :password "HodorHodor"}]
                 ["releases"
                  {:url "http://artifactory.dc.drivescale.com:8081/artifactory/libs-release-local"
                   :username "jenkins"
                   :password "HodorHodor"}]]

  :clean-targets ["pom.xml"]

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [cheshire "5.8.0"]
                 [inet.data "0.5.7"]]

  :codox {:output-path "resources/doc/api"}

  )
