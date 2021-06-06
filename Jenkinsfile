pipeline {
	agent any
	environment {
	    CONTAINER_NAME = "profile-api"
	    IMAGE_NAME = "venus/profile"
	    IMAGE_TAG = "latest"
	    NETWORK_NAME = "venusnet"
	    PORT = "8081:8081"
	}
	stages {
		stage('Checkout') {
			steps {
				echo 'Checkout..,'
				checkout scm
			}
		}
		stage('Build') {
			steps {
				echo 'Build...'
				sh 'mvn clean package -DskipTests'
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
                sh 'docker run --name ${CONTAINER_NAME} -d -p ${PORT} --network=${NETWORK_NAME} ${IMAGE_NAME}:${IMAGE_TAG} '
            }
        }
	}
}