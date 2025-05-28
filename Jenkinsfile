Jenkinsfilepipeline {
    agent any
    tools {
        maven 'Maven3'
        jdk 'JDK11'
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/username/student-score-analyzer.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Deploy to Staging') {
            steps {
                sh 'scp target/student-score-analyzer.jar user@staging-server:/app'
                sh 'ssh user@staging-server "java -jar /app/student-score-analyzer.jar"'
            }
        }
    }
}