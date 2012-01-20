(ns util.puppet
  "Access Puppet from Clojure"
  (:use [clojure.contrib.shell-out]
        clj-ssh.ssh)
  (:require [clj-yaml.core :as yaml]))

(defn get-local-machine-facts
  "Shells out to Facter, returns a map of all machine info"
  []
  (if-let [facter-result (sh "facter" "-y"
			     :out "UTF-8"
			     :return-map true)]
    (if (= 0 (:exit facter-result))
      (yaml/parse-string (:out facter-result)))))

(defn get-remote-machine-facts
  "Shells out to Facter, returns a map of all machine info"
  [servername]
  (with-ssh-agent []
    (add-identity (str (System/getenv "HOME") "/.ssh/id_rsa"))  
    (let [session (session servername :strict-host-key-checking :no)]
      (with-connection session
	(if-let [facter-result (ssh session
				    "facter" "-y"
				    :out "UTF-8"
				    :return-map true)]
	  (if (= 0 (:exit facter-result))
	    (yaml/parse-string (:out facter-result))))))))


