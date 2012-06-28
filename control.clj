(use '[control.commands]
     '[clojure.string :as str :only (blank?)])

(defcluster :lsmaven
  :user ~(System/getenv "USER")
  :addresses ["maven.likestream.net"])

(deftask :deploy-to-maven "Deploy jar and pom to maven repo" [org base name version]
  (let [org-slash-base (str org (if-not (blank? base) (str "/" base)))
        target-pom-prefix (if (blank? base) "." (str name))]
    (ssh (str "rm -f /home2/maven/maven2/" org-slash-base "/" name "/" version "/" name "-" version ".jar") :sudo true)
    (ssh (str "rm -f /home2/maven/maven2/" org-slash-base "/" name "/" version "/" name "-" version ".pom") :sudo true)
    (ssh (str "mkdir -p /home2/maven/maven2/" org-slash-base "/" name "/" version) :sudo true)
    (scp (str target-pom-prefix "/target/" name "-" version ".jar") (str "/home2/maven/maven2/" org-slash-base "/" name "/" version) :sudo true)
    (scp (str target-pom-prefix "/pom.xml") (str "/home2/maven/maven2/" org-slash-base "/" name "/" version "/" name "-" version ".pom") :sudo true)
    (ssh (str "chown -R maven:daemon /home2/maven/maven2/" org-slash-base "/" name) :sudo true)))

(deftask :deploy "Deploy all libs to maven repo" [version]
  (call :deploy-to-maven "com/dcj" "util" "util-map"         version)
  (call :deploy-to-maven "com/dcj" "util" "util-convert"     version)
  (call :deploy-to-maven "com/dcj" "util" "util-logging"     version)
  (call :deploy-to-maven "com/dcj" "util" "util-puppet"      version)
  (call :deploy-to-maven "com/dcj" "util" "util-phonenumber" version)
  (call :deploy-to-maven "com/dcj" "util" "util-counter"     version)
  (call :deploy-to-maven "com/dcj" "util" "util-configfile"  version)
  (call :deploy-to-maven "com/dcj" "util" "util-time"        version)
  (call :deploy-to-maven "com/dcj" "util" "util-uuid"        version)
  (call :deploy-to-maven "com/dcj" ""     "util"             version))



        
        
             
        
       
       
       


