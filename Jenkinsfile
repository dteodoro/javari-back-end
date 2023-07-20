pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'dteodoro-github', url: 'https://github.com/dteodoro/javari-back-end.git']])
            }
        }
        
        stage('Build - Javari Auth') {
            steps {
                sh './javari-auth/.mvnw clean package'
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'echo deploying'
            }
        }
    }
}
