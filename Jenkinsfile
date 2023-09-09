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
     stage('Deploy on Server') {
       steps {
         echo "Send files to Server" 
         sh "sshpass -p ${sshPass} scp ./**/target/*App.jar ${sshUser}@${sshServer}:~/build/back/ "
         echo "Stop current service "
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} systemctl stop javari-auth javari-game javari-connector javari-gateway javari-discovery "
         echo "Rename current jar to old"
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} mv /opt/applications/javari*.jar /opt/applications/bkp/ "
         echo "Copy new Jar to Applications folder"
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} cp ~/build/back/javari*.jar /opt/applications/ "
         echo "Add permission to exec"
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} chmod +x /opt/applications/javari*.jar "
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} chown ubuntu /opt/applications/javari*.jar " 
         echo "Start services" 
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} systemctl start javari-discovery "
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} systemctl start javari-gateway "
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} systemctl start javari-auth "
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} systemctl start javari-connector "
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} systemctl start javari-game "
         echo "Clean build folder"
         sh "sshpass -p ${sshPass} ssh ${sshUser}@${sshServer} rm -rf /home/ubuntu/build/back/*.jar "
       }
     }
  }
}
