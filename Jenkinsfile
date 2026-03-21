pipeline {
    agent any

    // THAY ĐỔI 1: Thêm triggers để Jenkins biết lắng nghe GitHub Webhook
    triggers {
        githubPush()
    }

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                // THAY ĐỔI 2: Xóa dòng 'git branch: main...' cũ
                // Thay bằng 'checkout scm' để Jenkins tự động lấy đúng code của nhánh vừa push lên
                checkout scm
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
    # 1. Giết app cũ
    pkill -f "java -jar target/.*.jar" || true
    sleep 2

    # 2. Khởi động app mới (Quan trọng: Phải có JENKINS_NODE_COOKIE)
    export JENKINS_NODE_COOKIE=dontKillMe
    nohup java -jar target/*.jar --server.port=8386 > app.log 2>&1 &
    
    # 3. Đợi app "thức dậy" hoàn toàn
    echo "🚀 Đang chờ Spring Boot khởi động..."
    sleep 20 

    # 4. Kiểm tra nội bộ Jenkins xem app sống chưa
    curl -s http://localhost:8386/api/products || echo "App chưa phản hồi, kiểm tra app.log"
'''
        }
    }

    post {
        success { echo "🎉 Pipeline PASS - API & Tests OK!" }
        failure { echo "❌ Pipeline FAIL - kiểm tra test hoặc build" }
    }
}
