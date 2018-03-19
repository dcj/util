(defproject com.dcj.util/facter "1.0.5"
  :description "Access Puppet/Facter info from Clojure"
;;  :clean-targets ["pom.xml"] 
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-yaml "0.4.0"]
                 [cheshire "5.8.0"]
		 [clj-ssh "0.5.14"]]

  :codox {
          :output-path "resources/doc/api"
          }

  )



