pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Descargando código del repositorio...'
                checkout scm
            }
        }

        stage('Compilación y Pruebas') {
            steps {
                echo 'Ejecutando pruebas unitarias de la Calculadora...'
                sh 'mvn clean test'
            }
            post {
                always {
                    // Publica los reportes de pruebas JUnit en Jenkins
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('Construcción de Paquete (JAR)') {
            steps {
                echo 'Generando archivo JAR...'
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: false
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo '¡El Pipeline se ejecutó exitosamente en Jenkins!'
            /* 
              Cuando configuremos Slack, aquí añadiremos:
              slackSend(
                  channel: '#nombre-canal',
                  color: 'good',
                  message: "SUCCESS: Build #${env.BUILD_NUMBER} de ${env.JOB_NAME} (${env.BUILD_URL})"
              )
            */
        }
        failure {
            echo '¡Hubo un error en la ejecución del Pipeline!'
            /* 
              Cuando configuremos Slack, aquí añadiremos:
              slackSend(
                  channel: '#nombre-canal',
                  color: 'danger',
                  message: "FAILED: Build #${env.BUILD_NUMBER} de ${env.JOB_NAME} (${env.BUILD_URL})"
              )
            */
        }
    }
}
