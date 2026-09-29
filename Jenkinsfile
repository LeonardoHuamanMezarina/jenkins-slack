pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    triggers {
        // Revisa GitHub automáticamente cada 2 minutos; si hay nuevo push, se ejecuta solo
        pollSCM('H/2 * * * *')
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
            slackSend(
                channel: 'noti-jenkins',
                color: '#36a64f',
                botUser: true,
                tokenCredentialId: 'slack-token',
                message: "✅ *Build Exitoso* en Jenkins!\n*Proyecto:* ${env.JOB_NAME} | *Build #:* ${env.BUILD_NUMBER}\n*Rama:* main | *Calculadora y Pruebas Unitarias:* OK\n*Detalles:* <${env.BUILD_URL}|Ver ejecución en Jenkins>"
            )
        }
        failure {
            echo '¡Hubo un error en la ejecución del Pipeline!'
            slackSend(
                channel: 'noti-jenkins',
                color: '#ff0000',
                botUser: true,
                tokenCredentialId: 'slack-token',
                message: "❌ *Build Fallido* en Jenkins!\n*Proyecto:* ${env.JOB_NAME} | *Build #:* ${env.BUILD_NUMBER}\n*Rama:* main\n*Detalles:* <${env.BUILD_URL}|Revisar logs en Jenkins>"
            )
        }
    }
}
