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
            # Tìm và tắt các tiến trình Java đang chạy ứng dụng của mình
            # -f: tìm kiếm toàn bộ dòng lệnh (full command line)
            # || true: để tránh lỗi Pipeline dừng lại nếu không tìm thấy tiến trình nào để tắt
            pkill -f "java -jar target/.*.jar" || true
            
            # Đợi 2 giây để tiến trình cũ giải phóng tài nguyên hoàn toàn
            sleep 2
            
            # Khởi động App mới
            nohup java -jar target/*.jar --server.port=8386 > app.log 2>&1 &
            
            echo "🚀 App đang khởi động trên cổng 8386..."
            sleep 10
            curl -s http://localhost:8386/api/products || echo "Check log nếu không thấy API phản hồi"
            '''
            }
        }
    }

    post {
        success { echo "🎉 Pipeline PASS - API & Tests OK!" }
        failure { echo "❌ Pipeline FAIL - kiểm tra test hoặc build" }
    }
}
