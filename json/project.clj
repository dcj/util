(defproject

  com.dcj.util/json

  "1.0.7.1-SNAPSHOT"

  :description "JSON conversion and I/O utilities"

  :clean-targets ["pom.xml"]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [tick "0.4.23-alpha"]
                 [cheshire "5.10.0"]
                 [com.dcj/inet.data "0.5.9-SNAPSHOT"]
                 ;; [inet.data "0.5.7"] Not compatible with JDK 11 for now
                 ]

  :repositories {"snapshots"
               {:url "https://repo.deps.co/aircraft-noise/snapshots"
                :username :env/deps_key
                :password :env/deps_secret}}

  :codox {:output-path "resources/doc/api"}

  )
