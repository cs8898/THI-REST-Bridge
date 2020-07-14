pipeline{
agent any
    environment {
        DOCKER_CREDENTIALS = credentials('jenkins-docker-repo')
        DOCKER_IMAGE_NAME='thi-rest-bridge'
        DOCKER_USERNAME = "${env.DOCKER_CREDENTIALS_USR}"
        DOCKER_PASSWORD = "${env.DOCKER_CREDENTIALS_PSW}"
        DOCKER_REPOSITORY = "${env.DOCKER_DOCKER_CREDENTIALS_USR}/${env.DOCKER_IMAGE_NAME}"
    }

    stages {
        stage ('Build') {
            steps {
                sh './mvnw package -Pnative -Dquarkus.native.container-build=true'
            }
        }
        stage ('Packege') {
            steps {
                  sh 'docker build -f src/main/docker/Dockerfile.native -t $DOCKER_IMAGE_NAME .'
            }
        }
        stage ('Deploy') {
            steps {
                  sh 'echo "$DOCKER_PASSWORD" | docker login --username $DOCKER_USERNAME --password-stdin'
                  sh 'docker tag $DOCKER_IMAGE $DOCKER_REPOSITORY:latest && docker push $DOCKER_REPOSITORY:latest'
            }
        }
    }
}