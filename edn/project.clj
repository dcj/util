(require 'cemerick.pomegranate.aether)
(cemerick.pomegranate.aether/register-wagon-factory!
 "http" #(org.apache.maven.wagon.providers.http.HttpWagon.))

(defproject

  com.dcj.util/edn

  "1.0.6-SNAPSHOT"

  :repositories [["snapshots"
                  {:url "http://artifactory.dc.drivescale.com:8081/artifactory/libs-snapshot-local"
                   :username "jenkins"
                   :password "HodorHodor"}]
                 ["releases"
                  {:url "http://artifactory.dc.drivescale.com:8081/artifactory/libs-release-local"
                   :username "jenkins"
                   :password "HodorHodor"}]]

  :description "EDN I/O Utilities"
  
  :clean-targets ["pom.xml"] 

  :dependencies [[org.clojure/clojure "1.9.0"]]

  :codox {:output-path "resources/doc/api"}

  )







