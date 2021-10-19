(defproject

  com.dcj.util/ddl

  "1.0.6-SNAPSHOT"

  :description "Utilities for creating SQL DDLs"

  :clean-targets ["pom.xml"]

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [slingshot "0.12.2"]]

  :repositories {"snapshots"
               {:url "https://repo.deps.co/aircraft-noise/snapshots"
                :username :env/deps_key
                :password :env/deps_secret}}

  :codox {:output-path "resources/doc/api"}

  )
