pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git branch: 'main',
            credentialId: 'dteodoro-github',
            url: 'https://github.com/dteodoro/javari-back-end.git'
      }
    }
    stage('Build') {
      steps {
        echo 'Building Auth Module..'
        dir('javari-auth'){
          sh "java -version"
          sh ".mvnw clean package -DskipTests"
        }
      }
      steps {
        echo 'Building Connector Module..'
        dir('javari-connector'){
          sh "java -version"
          sh ".mvnw clean package -DskipTests"
        }
      }
     }
     stage('Deploy') {
       steps {
         echo 'Deploying....'
       }
     }
  }
}
