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
        success {
            echo '¡El Pipeline se ejecutó exitosamente en Jenkins!'
            sh '''
                URL=$(echo 'aHR0cHM6Ly9ob29rcy5zbGFjay5jb20vc2VydmljZXMvVDBDNThMN041UlIvQjBDNTk2UUU2R0svd0NuQ2oxQzg3RW00QXFGZDVGSWlJRGF1' | base64 -d)
                curl -s -X POST -H 'Content-type: application/json' \
                --data '{"attachments":[{"color":"#36a64f","title":"✅ Build Exitoso en Jenkins","title_link":"'"${BUILD_URL}"'","text":"*Proyecto:* '"${JOB_NAME}"' | *Build #:* '"${BUILD_NUMBER}"'\\n*Rama:* main | *Calculadora y Pruebas Unitarias:* OK\\n<'"${BUILD_URL}"'|Ver ejecución en Jenkins>"}]}' \
                "$URL"
            '''
        }
        failure {
            echo '¡Hubo un error en la ejecución del Pipeline!'
            sh '''
                URL=$(echo 'aHR0cHM6Ly9ob29rcy5zbGFjay5jb20vc2VydmljZXMvVDBDNThMN041UlIvQjBDNTk2UUU2R0svd0NuQ2oxQzg3RW00QXFGZDVGSWlJRGF1' | base64 -d)
                curl -s -X POST -H 'Content-type: application/json' \
                --data '{"attachments":[{"color":"#ff0000","title":"❌ Build Fallido en Jenkins","title_link":"'"${BUILD_URL}"'","text":"*Proyecto:* '"${JOB_NAME}"' | *Build #:* '"${BUILD_NUMBER}"'\\n*Rama:* main\\n<'"${BUILD_URL}"'|Revisar logs en Jenkins>"}]}' \
                "$URL"
            '''
        }
        cleanup {
            cleanWs()
        }
    }
}
