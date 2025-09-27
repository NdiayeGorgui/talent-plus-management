pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17' // Maven + JDK 17
            args '-v /var/run/docker.sock:/var/run/docker.sock' // Permet d'utiliser Docker dans le conteneur
        }
    }

    environment {
        REGISTRY = "docker.io"
        DOCKERHUB_USER = "gorgui"
        APP_NAME = "talent-plus"
        VERSION = "1.0-SNAPSHOT"
    }

    stages {
        stage('Git Clone') {
            steps {
                git branch: 'main', url: 'https://github.com/NdiayeGorgui/talent-plus-management.git'
            }
        }

        stage('Maven Build (Multi-Module)') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Docker Build & Tag') {
            steps {
                script {
                    def modules = [
                        "api-gateway-service"     : "gateway-service",
                        "candidat-service"        : "candidat-service",
                        "config-service"          : "config-server",
                        "eureka-service"          : "eureka-service",
                        "notification-service"    : "notification-talent-service",
                        "offre-emploi-service"    : "offre-service",
                        "recrutement-service"     : "recrutement-service",
                        "recruteur-service"       : "recruteur-service",
                        "statistic-service"       : "statistic-service",
                        "utilisateur-service"     : "utilisateur-service"
                    ]

                    modules.each { module, imageName ->
                        echo "🔧 Building Docker image for ${module} -> ${imageName}"
                        sh """
                            docker build -t ${DOCKERHUB_USER}/${imageName}:latest ./${module}
                            docker tag ${DOCKERHUB_USER}/${imageName}:latest ${DOCKERHUB_USER}/${imageName}:${VERSION}
                        """
                    }
                }
            }
        }

        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'

                    script {
                        def modules = [
                            "api-gateway-service"     : "gateway-service",
                            "candidat-service"        : "candidat-service",
                            "config-service"          : "config-server",
                            "eureka-service"          : "eureka-service",
                            "notification-service"    : "notification-talent-service",
                            "offre-emploi-service"    : "offre-service",
                            "recrutement-service"     : "recrutement-service",
                            "recruteur-service"       : "recruteur-service",
                            "statistic-service"       : "statistic-service",
                            "utilisateur-service"     : "utilisateur-service"
                        ]

                        modules.each { module, imageName ->
                            echo "📤 Pushing ${imageName} to DockerHub"
                            sh """
                                docker push ${DOCKERHUB_USER}/${imageName}:latest
                                docker push ${DOCKERHUB_USER}/${imageName}:${VERSION}
                            """
                        }
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo "🚀 Deploying with docker-compose"
                sh '''
                    docker compose down || true
                    docker compose up -d
                '''
            }
        }
    }

    post {
        always {
            echo '🧹 Pipeline terminé (post actions).'
        }
        failure {
            echo '❌ Le pipeline a échoué.'
        }
        success {
            echo '✅ Déploiement terminé avec succès.'
        }
    }
}
