pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                sh 'echo hello pipeline'
            }
        }
        
        stage('Build - Javari Auth') {
            steps {
                sh 'sleep 60'
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'echo deploying'
            }
        }
    }
}
