(use '[control.commands])

(defcluster :lsmaven
  :user ~(System/getenv "USER")
  :addresses ["maven.likestream.net"])

(deftask :deploy-to-maven "Deploy jar and pom to maven repo" [name version]
  (ssh (str "rm -f /home2/maven/maven2/com/dcj/util/" name "/" version "/" name "-" version ".jar") :sudo true)
  (ssh (str "rm -f /home2/maven/maven2/com/dcj/util/" name "/" version "/" name "-" version ".jar") :sudo true)  
  (ssh (str "rm -f /home2/maven/maven2/com/dcj/util/" name "/" version "/" name "-" version ".pom") :sudo true)
  (ssh (str "mkdir -p /home2/maven/maven2/com/dcj/util/" name "/" version) :sudo true)
  (scp (str name "/target/" name "-" version ".jar") (str "/home2/maven/maven2/com/dcj/util/" name "/" version) :sudo true)
  (scp (str name "/pom.xml") (str "/home2/maven/maven2/com/dcj/util/" name "/" version "/" name "-" version ".pom") :sudo true)
  (ssh (str "chown -R maven:daemon /home2/maven/maven2/com/dcj/util/" name) :sudo true))

(deftask :deploy "Deploy all libs to maven repo" []
  (call :deploy-to-maven "util-map"         "1.0.1-SNAPSHOT")
  (call :deploy-to-maven "util-convert"     "1.0.1-SNAPSHOT")
  (call :deploy-to-maven "util-logging"     "1.0.1-SNAPSHOT")
  (call :deploy-to-maven "util-puppet"      "1.0.1-SNAPSHOT")
  (call :deploy-to-maven "util-phonenumber" "1.0.1-SNAPSHOT")
  (call :deploy-to-maven "util-counter"     "1.0.1-SNAPSHOT")
  (call :deploy-to-maven "util-configfile"  "1.0.1-SNAPSHOT")
  (call :deploy-to-maven "util-time"        "1.0.1-SNAPSHOT")
  (call :deploy-to-maven "util-uuid"        "1.0.1-SNAPSHOT"))


        
        
             
        
       
       
       


