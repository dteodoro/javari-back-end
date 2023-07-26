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
        echo 'Build Javari'
        sh "pwd"
        sh "ls -la"
        sh "./mvn clean package -DskipTests"
      }
    }
     stage('Deploy') {
       steps {
         echo 'Deploying....'
       }
     }
  }
}
