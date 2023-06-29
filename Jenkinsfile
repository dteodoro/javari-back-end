pipeline {
    agent any
    stages {
        stage('Build Module Auth') {
            steps {
                echo 'Building javari-auth..'
                sh './javari-auth/.mvnw .'
            }
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
