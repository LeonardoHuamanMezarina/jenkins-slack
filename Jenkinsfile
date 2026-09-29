pipeline {
    agent any

    /* 
      Si configuraste Maven o JDK en 'Manage Jenkins' -> 'Tools', 
      descomenta este bloque:
    */
    /*
    tools {
        maven 'Maven 3'
        jdk 'JDK 17'
    }
    */

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
                // Si tu Jenkins corre en Linux/Docker usa 'sh', si fuera Windows 'bat'
                sh 'mvn clean test'
            }
            post {
                always {
                    // Publica los reportes de pruebas JUnit en Jenkins
                    junit 'target/surefire-reports/*.xml'
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
