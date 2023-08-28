pipeline {
  tools {
    maven 'maven'
  }
  agent any
  stages {
    stage('Checkout') {
      steps {
        git branch: 'main',
            credentialsId: 'dteodoro-github',
            url: 'https://github.com/dteodoro/javari-back-end.git'
      }
    }
    stage('Build Javari') {
      steps {
        sh "mvn clean package -DskipTests"
      }
    }
     stage('Deploy on EC2') {
       steps {
         sshagent(credentials : ['javari-aws-prd']){
           echo "Sand files to Server" 
           sh "scp ./**/target/*App.jar ${sshUser}@${sshServer}/tmp/javari-build/back "
           echo "Stop current service "
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} systemctl stop javari-auth javari-game javari-connector javari-gateway javari-discovery "
           echo "Rename current jar to old"
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} mv /opt/applications/javari*.jar /app/bkp/ "
           echo "Copy new Jar to Applications folder"
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} cp /tmp/javari-build/back/javari*.jar /opt/applications/ "
           echo "Add permission to exec"
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} chmod +x /opt/applications/javari*.jar "
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} chown ubuntu /opt/applications/javari*.jar " 
           echo "Start services" 
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} systemctl start javari-discovery "
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} systemctl start javari-gateway "
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} systemctl start javari-auth "
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} systemctl start javari-connector "
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} systemctl start javari-game "
           echo "Clean build folder"
           sh "sshpass -p ${sshPassword} ssh ${sshUser}@${sshServer} rm -rf /tmp/javari-build/back/*.jar "
         }
       }
     }
  }
}
