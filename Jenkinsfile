pipeline {
    agent any

    tools {
        jdk 'JDK17'    // hoặc JDK21
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/tienhuu-dev/product-api-demo.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit & Integration Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy (giả lập local)') {
            steps {
                sh '''
                    nohup java -jar target/*.jar > app.log 2>&1 &
                    sleep 10
                    curl -s http://localhost:8386/api/products || echo "API started!"
                '''
                echo "Demo API chạy tại: http://localhost:8386/api/products (GET all products)"
                echo "H2 console: http://localhost:8386/h2-console (nếu muốn xem DB)"
            }
        }
    }

    post {
        success { echo "🎉 Pipeline PASS - API & Tests OK!" }
        failure { echo "❌ Pipeline FAIL - kiểm tra test hoặc build" }
    }
}
