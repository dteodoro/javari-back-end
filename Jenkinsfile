pipeline {
  tools {
    maven 'maven'
  }
  environment {
    sshServer = "${sshServer}"
    sshUser = "${sshUser}"
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
         sshagent(credentials : ['ubuntu']){
           echo "Send files to Server" 
           sh "scp ./**/target/*App.jar ${sshUser}@${sshServer}:build/back/ "
           echo "Stop current service "
           sh "ssh ${sshUser}@${sshServer} systemctl stop javari-auth javari-game javari-connector javari-gateway javari-discovery "
           echo "Rename current jar to old"
           sh "ssh ${sshUser}@${sshServer} mv /opt/applications/javari*.jar /opt/applications/bkp/ "
           echo "Copy new Jar to Applications folder"
           sh "ssh ${sshUser}@${sshServer} cp ~/build/back/javari*.jar /opt/applications/ "
           echo "Add permission to exec"
           sh "ssh ${sshUser}@${sshServer} chmod +x /opt/applications/javari*.jar "
           sh "ssh ${sshUser}@${sshServer} chown ubuntu /opt/applications/javari*.jar " 
           echo "Start services" 
           sh "ssh ${sshUser}@${sshServer} systemctl start javari-discovery "
           sh "ssh ${sshUser}@${sshServer} systemctl start javari-gateway "
           sh "ssh ${sshUser}@${sshServer} systemctl start javari-auth "
           sh "ssh ${sshUser}@${sshServer} systemctl start javari-connector "
           sh "ssh ${sshUser}@${sshServer} systemctl start javari-game "
           echo "Clean build folder"
           sh "ssh ${sshUser}@${sshServer} rm -rf /home/ubuntu/build/back/*.jar "
         }
       }
     }
  }
}
