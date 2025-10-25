pipeline {
    agent any

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
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    echo "🔐 Connexion à DockerHub"
                    bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'

                    script {
                        def modules = [
                            "api-gateway-service"     : "gateway-service",
                            "candidat-service"        : "candidat-service",
                            "config-service"          : "config-server",
                            "employeur-service"       : "employeur-service",
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
                            bat "docker build -t %DOCKERHUB_USER%/${imageName}:latest .\\${module}"
                            bat "docker tag %DOCKERHUB_USER%/${imageName}:latest %DOCKERHUB_USER%/${imageName}:%VERSION%"
                            echo "📤 Poussée de ${imageName} sur DockerHub"
                            bat "docker push %DOCKERHUB_USER%/${imageName}:latest"
                            bat "docker push %DOCKERHUB_USER%/${imageName}:%VERSION%"
                        }
                    }
                }
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
