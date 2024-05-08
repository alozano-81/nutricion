pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'javac target/nutricion-0.0.1-SNAPSHOT.java'
                sh 'cd src ; jar cd nutricion-0.0.1-SNAPSHOT.jar target/nutricion-0.0.1-SNAPSHOT.class'
            }
        }
        stages('Archive'){
            steps {
                archiveArtifacts artifacts: 'target/nutricion-0.0.1-SNAPSHOT.jar', finger
            }
        }
    }
}