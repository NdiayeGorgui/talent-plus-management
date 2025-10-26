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

        stage('Detect Changed Modules') {
            steps {
                script {
                    echo "🔍 Détection des microservices modifiés..."

                    // Récupération des fichiers modifiés (HEAD vs commit précédent)
                    def changedFiles = bat(
                        script: 'git diff --name-only HEAD~1 HEAD',
                        returnStdout: true
                    ).trim().split('\n')

                    // Liste des modules correspondants
                    def allModules = [
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

                    // Trouver les modules à builder
                    CHANGED_MODULES = []
                    allModules.each { module, image ->
                        if (changedFiles.any { it.startsWith("${module}/") }) {
                            CHANGED_MODULES << [module: module, image: image]
                        }
                    }

                    if (CHANGED_MODULES.isEmpty()) {
                        echo "✅ Aucun microservice modifié ✅"
                    } else {
                        echo "✅ Services concernés : ${CHANGED_MODULES*.module}"
                    }
                }
            }
        }

        stage('Docker Build & Push Only Changed') {
            when {
                expression { return CHANGED_MODULES && CHANGED_MODULES.size() > 0 }
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    echo "🔐 Connexion à DockerHub"
                    bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'

                    script {
                        CHANGED_MODULES.each { item ->
                            def module = item.module
                            def imageName = item.image

                            echo "🚀 Build & Push pour ${module} -> ${imageName}"
                            bat "docker build -t %DOCKERHUB_USER%/${imageName}:latest .\\${module}"
                            bat "docker tag %DOCKERHUB_USER%/${imageName}:latest %DOCKERHUB_USER%/${imageName}:%VERSION%"
                            bat "docker push %DOCKERHUB_USER%/${imageName}:latest"
                            bat "docker push %DOCKERHUB_USER%/${imageName}:%VERSION%"
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline terminé avec succès.'
        }
        failure {
            echo '❌ Échec du pipeline.'
        }
    }
}
