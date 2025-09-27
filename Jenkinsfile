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
        stage('Clean Workspace') {
            steps {
                echo "🧹 Nettoyage de l'espace de travail"
                deleteDir()
            }
        }

        stage('Git Clone') {
            steps {
                echo "🔄 Clonage du dépôt Git"
                git branch: 'main', url: 'https://github.com/NdiayeGorgui/talent-plus-management.git'
            }
        }

        stage('Maven Build (Multi-Module)') {
            steps {
                echo "⚙️ Build Maven multi-module"
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
                        echo "🔧 Construction de l'image Docker pour ${module} -> ${imageName}"
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
                    echo "🔐 Connexion à DockerHub"
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
                            echo "📤 Poussée de ${imageName} sur DockerHub"
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
                echo "🚀 Déploiement avec docker-compose"
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
