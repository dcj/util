(require 'cemerick.pomegranate.aether)
(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))

(defproject

  com.dcj.util/http

  "1.0.6-SNAPSHOT"

  :description "HTTP utilities"

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
                 [slingshot "0.12.2"]]
  
  :codox {:output-path "resources/doc/api"}

  )







