pipeline {
	agent any
	environment {
	    CONTAINER_NAME = "profile-api"
	    IMAGE_NAME = "venus/profile"
	    IMAGE_TAG = "latest"
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
				sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
			}
		}
		stage('Clean up') {
		    steps {
		        echo 'Clean up old containers...'
                sh 'docker ps -f name=${CONTAINER_NAME} -q | xargs --no-run-if-empty docker container stop'
                sh 'docker container ls -a -f name=${CONTAINER_NAME} -q | xargs -r docker container rm'
            }
		}
		stage('Deploy') {
            steps {
                echo 'Deploy'
                sh 'docker run --name ${CONTAINER_NAME} -d -p 8081:8081  ${IMAGE_NAME}:${IMAGE_TAG} '
            }
        }
	}
}