(defproject

  com.dcj.util/edn

  "1.0.6-SNAPSHOT"

  :description "EDN I/O Utilities"

  :clean-targets ["pom.xml"]

  :dependencies [[org.clojure/clojure "1.9.0"]]

  :repositories {"snapshots"
               {:url "https://repo.deps.co/aircraft-noise/snapshots"
                :username :env/deps_key
                :password :env/deps_secret}}

  :codox {:output-path "resources/doc/api"}

  )
