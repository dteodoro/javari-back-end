pipeline {
  tools {
    maven 'maven'
  }
  environment {
    sshServer = 'ec2-13-58-13-55.us-east-2.compute.amazonaws.com'
    sshUser = 'ubuntu'
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
           sh "sudo scp ./**/target/*App.jar ${sshUser}@${sshServer}/tmp/javari-build/back "
           echo "Stop current service "
           sh "sudo ssh ${sshUser}@${sshServer} systemctl stop javari-auth javari-game javari-connector javari-gateway javari-discovery "
           echo "Rename current jar to old"
           sh "sudo ssh ${sshUser}@${sshServer} mv /opt/applications/javari*.jar /app/bkp/ "
           echo "Copy new Jar to Applications folder"
           sh "sudo ssh ${sshUser}@${sshServer} cp /tmp/javari-build/back/javari*.jar /opt/applications/ "
           echo "Add permission to exec"
           sh "sudo ssh ${sshUser}@${sshServer} chmod +x /opt/applications/javari*.jar "
           sh "sudo ssh ${sshUser}@${sshServer} chown ubuntu /opt/applications/javari*.jar " 
           echo "Start services" 
           sh "sudo ssh ${sshUser}@${sshServer} systemctl start javari-discovery "
           sh "sudo ssh ${sshUser}@${sshServer} systemctl start javari-gateway "
           sh "sudo ssh ${sshUser}@${sshServer} systemctl start javari-auth "
           sh "sudo ssh ${sshUser}@${sshServer} systemctl start javari-connector "
           sh "sudo ssh ${sshUser}@${sshServer} systemctl start javari-game "
           echo "Clean build folder"
           sh "sudo ssh ${sshUser}@${sshServer} rm -rf /tmp/javari-build/back/*.jar "
         }
       }
     }
  }
}
