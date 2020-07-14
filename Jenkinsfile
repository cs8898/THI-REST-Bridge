pipeline{
agent any
    environment {
        DOCKER_CREDENTIALS = credentials('jenkins-docker-repo'),
        DOCKER_USERNAME="${env.DOCKER_DOCKER_CREDENTIALS_USR}"
        DOCKER_PASSWORD = "${env.DOCKER_CREDENTIALS_PSW}"
        DOCKER_IMAGE_NAME='thi-rest-bridge'
        DOCKER_REPOSITORY='${env.DOCKER_USERNAME}/${env.DOCKER_IMAGE_NAME}'
    }

    stages {
        stage {'Build'} {
            steps {
                sh './mvnw package -Pnative -Dquarkus.native.container-build=true'
            }
        }
        stage {'Deploy'} {
            steps {
                  sh 'echo "$DOCKER_PASSWORD" | docker login --username $DOCKER_USERNAME --password-stdin'
                  sh 'docker build -f src/main/docker/Dockerfile.native -t $DOCKER_IMAGE_NAME .'
            }
        }
    }
}