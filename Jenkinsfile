pipeline {
    agent any

    environment {
        REGISTRY = "docker.io"
        DOCKERHUB_USER = "gorgui"
        APP_NAME = "talent-plus"
        VERSION = "1.0-SNAPSHOT"
    }

    stages {
        stage('Git clone') {
            steps {
                git 'https://github.com/NdiayeGorgui/talent-plus-management.git'
            }
        }

        stage('Maven Build (Multi-Module)') {
            steps {
                // Nettoyage + Build complet avec tests
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Docker Build & Tag') {
            steps {
                script {
                    // Map des modules -> noms d'images Docker
                    def modules = [
                        "api-gateway-service" : "gateway-service",
                        "candidat-service"    : "candidat-service",
                        "config-service"      : "config-server",
                        "eureka-service"      : "eureka-service",
                        "notification-service": "notification-talent-service",
                        "offre-emploi-service": "offre-service",
                        "recrutement-service" : "recrutement-service",
                        "recruteur-service"   : "recruteur-service",
                        "statistic-service"   : "statistic-service",
                        "utilisateur-service" : "utilisateur-service"
                    ]

                    modules.each { module, imageName ->
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
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'

                    script {
                        def modules = [
                            "api-gateway-service" : "gateway-service",
                            "candidat-service"    : "candidat-service",
                            "config-service"      : "config-server",
                            "eureka-service"      : "eureka-service",
                            "notification-service": "notification-talent-service",
                            "offre-emploi-service": "offre-service",
                            "recrutement-service" : "recrutement-service",
                            "recruteur-service"   : "recruteur-service",
                            "statistic-service"   : "statistic-service",
                            "utilisateur-service" : "utilisateur-service"
                        ]

                        modules.each { module, imageName ->
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
                sh 'docker compose down || true'
                sh 'docker compose up -d'
            }
        }
    }

    post {
        always {
            echo 'Pipeline terminé.'
        }
        failure {
            echo 'Le pipeline a échoué ❌'
        }
        success {
            echo 'Déploiement terminé ✅'
        }
    }
}
