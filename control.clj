(use '[control.commands])

(defcluster :lsmaven
  :user ~(System/getenv "USER")
  :addresses ["maven.likestream.net"])

(deftask :deploy-to-maven "Deploy jar and pom to maven repo" [name version]
  (ssh (str "rm -f /home2/maven/maven2/com/dcj/util/" name "/" version "/" name "-" version ".jar") :sudo true)
  (ssh (str "rm -f /home2/maven/maven2/com/dcj/util/" name "/" version "/" name "-" version ".pom") :sudo true)
  (ssh (str "mkdir -p /home2/maven/maven2/com/dcj/util/" name "/" version) :sudo true)
  (scp (str name "/target/" name "-" version ".jar") (str "/home2/maven/maven2/com/dcj/util/" name "/" version) :sudo true)
  (scp (str name "/pom.xml") (str "/home2/maven/maven2/com/dcj/util/" name "/" version "/" name "-" version ".pom") :sudo true)
  (ssh (str "chown -R maven:daemon /home2/maven/maven2/com/dcj/util/" name) :sudo true))

(deftask :deploy "Deploy all libs to maven repo" [version]
  (call :deploy-to-maven "util-map"         version)
  (call :deploy-to-maven "util-convert"     version)
  (call :deploy-to-maven "util-logging"     version)
  (call :deploy-to-maven "util-puppet"      version)
  (call :deploy-to-maven "util-phonenumber" version)
  (call :deploy-to-maven "util-counter"     version)
  (call :deploy-to-maven "util-configfile"  version)
  (call :deploy-to-maven "util-time"        version)
  (call :deploy-to-maven "util-uuid"        version))


        
        
             
        
       
       
       


