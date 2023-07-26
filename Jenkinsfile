pipeline {
  agent any
  stages {
    stage('Checkout') {
      steps {
        git branch: 'main',
            credentialsId: 'dteodoro-github',
            url: 'https://github.com/dteodoro/javari-back-end.git'
      }
    }
    stage('Build Javari Commons') {
      steps {
        echo 'Build Javari Commons 2...'
        dir('javari-commons'){
          sh "mvn clean package -DskipTests"
        }
      }
    }
    stage('Build Javari Core') {
      steps {
        echo 'Build Javari Core...'
        dir('javari-core'){
          sh "mvn clean package -DskipTests"
        }
      }
    }
    stage('Build Auth') {
      steps {
        echo 'Building Auth Module..'
        dir('javari-auth'){
          sh "java -version"
          sh "mvn clean package -DskipTests"
        }
      }
    }
    stage('Build Connector') {
      steps {
        echo 'Building Connector Module..'
        dir('javari-connector'){
          sh "java -version"
          sh "mvn clean package -DskipTests"
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
