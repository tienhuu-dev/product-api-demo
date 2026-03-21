pipeline {
    agent any

    // Kích hoạt lắng nghe tín hiệu từ GitHub Webhook
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
                // Tự động lấy đúng code của nhánh được push lên (linh hoạt hơn gán cứng nhánh 'main')
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
                    // Thu thập kết quả test để hiển thị biểu đồ trên Jenkins
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package JAR') {
            steps {
                // Lưu trữ file JAR để có thể tải về từ giao diện Jenkins
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy (giả lập local)') {
            steps {
                sh '''
                    # 1. Tìm và tắt các tiến trình Java cũ dựa trên tên file JAR (tránh xung đột cổng)
                    pkill -f "java -jar target/.*.jar" || true
                    sleep 2

                    # 2. Đặt biến môi trường để Jenkins không tự động tắt App sau khi Pipeline kết thúc
                    export JENKINS_NODE_COOKIE=dontKillMe

                    # 3. Khởi động ứng dụng Spring Boot ở chế độ chạy ngầm (background)
                    # Kết quả được ghi vào file app.log để tiện kiểm tra lỗi
                    nohup java -jar target/*.jar --server.port=8386 > app.log 2>&1 &
                    
                    echo "🚀 Đang chờ Spring Boot khởi động trên cổng 8386..."
                    sleep 20 

                    # 4. Kiểm tra nội bộ container xem API đã phản hồi chưa
                    curl -s http://localhost:8386/api/products || echo "⚠️ App chưa phản hồi, hãy kiểm tra app.log bên trong container!"
                '''
                
                echo "✅ Demo API chạy tại: http://localhost:8386/api/products"
            }
        }
    }

    post {
        success {
            echo "🎉 Pipeline THÀNH CÔNG - Webhook hoạt động tốt!"
        }
        failure {
            echo "❌ Pipeline THẤT BẠI - Vui lòng kiểm tra lại log build hoặc lỗi test."
        }
    }
}
