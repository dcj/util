(ns util.facter
  "Access Facter from Clojure"
  (:require [clj-yaml.core :as yaml]
            [clj-ssh.ssh :as ssh]
            [cheshire.core :as json]
            [clojure.java.shell :as shell]))

(defn get-localhost-facts
  "Shells out to Facter, returns a map of all machine info"
  []
  (if-let [facter-result-sudo (shell/sh "sudo" "-A" "/usr/local/var/rbenv/shims/facter" "-j"
                                        :env {"SUDO_ASKPASS" "/bin/echo"}
                                        :out-enc "UTF-8")]
    (if (= 0 (:exit facter-result-sudo))
      (json/parse-string (:out facter-result-sudo) true)
      (if-let [facter-result (shell/sh "/usr/local/var/rbenv/shims/facter" "-j"
                                       :out-enc "UTF-8")]
        (if (= 0 (:exit facter-result))
          (json/parse-string (:out facter-result) true))))))

(defmulti get-server-facts
  "Runs Facter on given servername(s), if target is a string (representing a hostname) returns map of facts, if a collection, returns map keyed by each server with values of the server facts"
  (fn [target-or-targets]
    (if (sequential? target-or-targets) :sequential :singleton)))

(defmethod get-server-facts :singleton
  [servername]
  (if (or (= servername "localhost")
          (= servername :localhost)
          (= servername
             (.getHostName (java.net.InetAddress/getLocalHost))))
    (get-localhost-facts)
    (let [ssh-agent   (ssh/ssh-agent {:use-system-ssh-agent true})
          ssh-session (ssh/session ssh-agent servername {:strict-host-key-checking :no})]
      (ssh/with-connection ssh-session
        (if-let [facter-result-sudo (ssh/ssh ssh-session {:cmd "export SUDO_ASKPASS=/bin/echo; sudo -A facter -j"})]
          (if (= 0 (:exit facter-result-sudo))
            (json/parse-string (:out facter-result-sudo) true)
            (if-let [facter-result (ssh/ssh ssh-session {:cmd "facter -j"})]
              (json/parse-string (:out facter-result) true))))))))

(defmethod get-server-facts :sequential
  [servernames]
  (apply merge
         (for [server servernames
               :let [server-facts (get-server-facts server)]]
           {server server-facts})))


