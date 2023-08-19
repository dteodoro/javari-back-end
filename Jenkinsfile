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
        sh "mvn clean install -DskipTests"
      }
    }
     stage('Deploy') {
       steps {
         sh "ls -la"
         sh "cd javari-auth ls -la"
         echo 'Deploying....ES2'
       }
     }
  }
}
