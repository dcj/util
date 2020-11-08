(defproject

  com.dcj.util/yaml

  "1.0.6.2-SNAPSHOT"

  :description "YAML conversion and I/O utilities"

  :clean-targets ["pom.xml"]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;; [circleci/clj-yaml "0.5.6"]
                 ;; [circleci/clj-yaml "0.6.0"]
                 [clj-commons/clj-yaml "0.7.2"]
                 ]

  :repositories {"snapshots"
               {:url "https://repo.deps.co/aircraft-noise/snapshots"
                :username :env/deps_key
                :password :env/deps_secret}}

  :codox {:output-path "resources/doc/api"}

  )
