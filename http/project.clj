(defproject

  com.dcj.util/http

  "1.0.7-SNAPSHOT"

  :description "HTTP utilities"

  :clean-targets ["pom.xml"]

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [clj-http "3.12.1"]
                 [slingshot "0.12.2"]]

  :codox {:output-path "resources/doc/com.dcj/util/http/"}

  :repositories {"snapshots"
               {:url "https://repo.deps.co/aircraft-noise/snapshots"
                :username :env/deps_key
                :password :env/deps_secret}}

  )
