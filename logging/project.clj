(defproject

  com.dcj.util/logging

  "1.0.6-SNAPSHOT"

  :description "Logging utilities"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.logging "0.4.1"]]

  :clean-targets ["pom.xml"]

  :repositories {"snapshots"
                 {:url "https://repo.deps.co/aircraft-noise/snapshots"
                  :username :env/deps_key
                  :password :env/deps_secret}}

  :codox {
          :output-path "resources/doc/api/util/logging"
          }

  )
