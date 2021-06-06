pipeline {
	agent any
	environment {
	    CONTAINER_NAME = "profile-api-app"
	    IMAGE_NAME = "venus/profile"
	}
	stages {
		stage('Checkout') {
			steps {
				echo 'Checkout..,'
				checkout scm
				stash 'sources'
			}
		}
		stage('Build') {
			steps {
				echo 'Build...'
				unstash 'sources'
				sh 'mvn clean package -DskipTests'
				stash 'sources'
				sh 'docker build -t venus/profile:latest .'
			}
		}
		stage('Clean up') {
		    steps {
		        echo 'Clean up old containers...'
                sh 'docker ps -f name=profile-api-app -q | xargs --no-run-if-empty docker container stop'
                sh 'docker container ls -a -fname=profile-api-app -q | xargs -r docker container rm'
            }
		}
		stage('Deploy') {
            steps {
                echo 'Deploy'
                sh 'docker run --name profile-api-app -d -p 8081:8081  venus/profile:latest'
            }
        }
	}
}