from fabric.api import *
from fabric.contrib.project import *

@hosts('maven.likestream.net')
def deploy():
    "puts jar and pom into local maven repo"

    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-configfile/1.0.0-SNAPSHOT/util-configfile-1.0.0-SNAPSHOT.jar')
    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-configfile/1.0.0-SNAPSHOT/util-configfile-1.0.0-SNAPSHOT.pom')    
    sudo('mkdir -p /home2/maven/maven2/com/dcj/util/util-configfile/1.0.0-SNAPSHOT')
    put('util-configfile/util-configfile-1.0.0-SNAPSHOT.jar', '/home2/maven/maven2/com/dcj/util/util-configfile/1.0.0-SNAPSHOT', use_sudo=True)
    put('util-configfile/pom.xml', '/home2/maven/maven2/com/dcj/util/util-configfile/1.0.0-SNAPSHOT/util-configfile-1.0.0-SNAPSHOT.pom', use_sudo=True)

    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-convert/1.0.0-SNAPSHOT/util-convert-1.0.0-SNAPSHOT.jar')
    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-convert/1.0.0-SNAPSHOT/util-convert-1.0.0-SNAPSHOT.pom')    
    sudo('mkdir -p /home2/maven/maven2/com/dcj/util/util-convert/1.0.0-SNAPSHOT')
    put('util-convert/util-convert-1.0.0-SNAPSHOT.jar', '/home2/maven/maven2/com/dcj/util/util-convert/1.0.0-SNAPSHOT', use_sudo=True)
    put('util-convert/pom.xml', '/home2/maven/maven2/com/dcj/util/util-convert/1.0.0-SNAPSHOT/util-convert-1.0.0-SNAPSHOT.pom', use_sudo=True)

    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-counter/1.0.0-SNAPSHOT/util-counter-1.0.0-SNAPSHOT.jar')
    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-counter/1.0.0-SNAPSHOT/util-counter-1.0.0-SNAPSHOT.pom')    
    sudo('mkdir -p /home2/maven/maven2/com/dcj/util/util-counter/1.0.0-SNAPSHOT')
    put('util-counter/util-counter-1.0.0-SNAPSHOT.jar', '/home2/maven/maven2/com/dcj/util/util-counter/1.0.0-SNAPSHOT', use_sudo=True)
    put('util-counter/pom.xml', '/home2/maven/maven2/com/dcj/util/util-counter/1.0.0-SNAPSHOT/util-counter-1.0.0-SNAPSHOT.pom', use_sudo=True)

    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-map/1.0.0-SNAPSHOT/util-map-1.0.0-SNAPSHOT.jar')
    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-map/1.0.0-SNAPSHOT/util-map-1.0.0-SNAPSHOT.pom')    
    sudo('mkdir -p /home2/maven/maven2/com/dcj/util/util-map/1.0.0-SNAPSHOT')
    put('util-map/util-map-1.0.0-SNAPSHOT.jar', '/home2/maven/maven2/com/dcj/util/util-map/1.0.0-SNAPSHOT', use_sudo=True)
    put('util-map/pom.xml', '/home2/maven/maven2/com/dcj/util/util-map/1.0.0-SNAPSHOT/util-map-1.0.0-SNAPSHOT.pom', use_sudo=True)

    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-phonenumber/1.0.0-SNAPSHOT/util-phonenumber-1.0.0-SNAPSHOT.jar')
    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-phonenumber/1.0.0-SNAPSHOT/util-phonenumber-1.0.0-SNAPSHOT.pom')    
    sudo('mkdir -p /home2/maven/maven2/com/dcj/util/util-phonenumber/1.0.0-SNAPSHOT')
    put('util-phonenumber/util-phonenumber-1.0.0-SNAPSHOT.jar', '/home2/maven/maven2/com/dcj/util/util-phonenumber/1.0.0-SNAPSHOT', use_sudo=True)
    put('util-phonenumber/pom.xml', '/home2/maven/maven2/com/dcj/util/util-phonenumber/1.0.0-SNAPSHOT/util-phonenumber-1.0.0-SNAPSHOT.pom', use_sudo=True)

    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-puppet/1.0.0-SNAPSHOT/util-puppet-1.0.0-SNAPSHOT.jar')
    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-puppet/1.0.0-SNAPSHOT/util-puppet-1.0.0-SNAPSHOT.pom')    
    sudo('mkdir -p /home2/maven/maven2/com/dcj/util/util-puppet/1.0.0-SNAPSHOT')
    put('util-puppet/util-puppet-1.0.0-SNAPSHOT.jar', '/home2/maven/maven2/com/dcj/util/util-puppet/1.0.0-SNAPSHOT', use_sudo=True)
    put('util-puppet/pom.xml', '/home2/maven/maven2/com/dcj/util/util-puppet/1.0.0-SNAPSHOT/util-puppet-1.0.0-SNAPSHOT.pom', use_sudo=True)

    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-uuid/1.0.0-SNAPSHOT/util-uuid-1.0.0-SNAPSHOT.jar')
    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-uuid/1.0.0-SNAPSHOT/util-uuid-1.0.0-SNAPSHOT.pom')    
    sudo('mkdir -p /home2/maven/maven2/com/dcj/util/util-uuid/1.0.0-SNAPSHOT')
    put('util-uuid/util-uuid-1.0.0-SNAPSHOT.jar', '/home2/maven/maven2/com/dcj/util/util-uuid/1.0.0-SNAPSHOT', use_sudo=True)
    put('util-uuid/pom.xml', '/home2/maven/maven2/com/dcj/util/util-uuid/1.0.0-SNAPSHOT/util-uuid-1.0.0-SNAPSHOT.pom', use_sudo=True)

    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-time/1.0.0-SNAPSHOT/util-time-1.0.0-SNAPSHOT.jar')
    sudo('rm -f /home2/maven/maven2/com/dcj/util/util-time/1.0.0-SNAPSHOT/util-time-1.0.0-SNAPSHOT.pom')    
    sudo('mkdir -p /home2/maven/maven2/com/dcj/util/util-time/1.0.0-SNAPSHOT')
    put('util-time/util-time-1.0.0-SNAPSHOT.jar', '/home2/maven/maven2/com/dcj/util/util-time/1.0.0-SNAPSHOT', use_sudo=True)
    put('util-time/pom.xml', '/home2/maven/maven2/com/dcj/util/util-time/1.0.0-SNAPSHOT/util-time-1.0.0-SNAPSHOT.pom', use_sudo=True)

    sudo('chown -R maven:daemon /home2/maven/maven2/com/dcj')
