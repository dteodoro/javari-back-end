pipeline {
    agent any
    stages {
        stage('Checkout') {
            git 'https://github.com/dteodoro/javari-back-end.git'
        }
        stage('Build - Java Auth') {
            sh "java -version"
            sh "./javari-auth/.mvnw clean package -DskipTests"
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
