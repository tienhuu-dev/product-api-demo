pipeline {
    agent any

    parameters {
        string(name: 'APP_PORT', defaultValue: '8081', description: 'Nhập cổng muốn chạy ứng dụng')
        string(name: 'BRANCH', defaultValue: 'main', description: 'Nhánh muốn build')
    }

    environment {
        // Gom các cấu hình vào đây để dễ sửa đổi một chỗ
        APP_NAME = "product-api-demo"
        JAR_PATH = "target/*.jar"
    }

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    stages {
        stage('Initialize') {
            steps {
                echo "Đang chuẩn bị build dự án: ${APP_NAME} trên nhánh ${params.BRANCH}"
            }
        }

        stage('Checkout') {
            steps {
                // Sử dụng biến params để linh hoạt chọn nhánh khi nhấn "Build with Parameters"
                git branch: "${params.BRANCH}", url: 'https://github.com/tienhuu-dev/product-api-demo.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo "Building application..."
                sh 'mvn clean package'
            }
            post {
                always {
                    // Luôn thu thập báo cáo test dù build thành công hay thất bại
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive') {
            steps {
                // Lưu lại file JAR để có thể tải về từ giao diện Jenkins
                archiveArtifacts artifacts: "${env.JAR_PATH}", fingerprint: true
            }
        }

        stage('Deploy (Local Container)') {
            steps {
                script {
                    // Tắt app cũ đang chạy trên port này trước khi chạy app mới (tránh lỗi Port in use)
                    sh "fuser -k ${params.PORT}/tcp || true"

                    echo "Đang khởi chạy ứng dụng tại port: ${params.PORT}..."
                    sh "nohup java -jar ${env.JAR_PATH} --server.port=${params.PORT} > app.log 2>&1 &"

                    // Đợi app khởi động
                    sleep 5
                    echo "✅ App đã sẵn sàng tại: http://localhost:${params.PORT}/api/products"
                }
            }
        }
    }

    post {
        success {
            echo "Chúc mừng! Pipeline hoàn thành xuất sắc."
        }
        failure {
            echo "Có lỗi xảy ra. Hãy kiểm tra Console Output hoặc file app.log."
        }
        always {
            // Dọn dẹp các tiến trình thừa hoặc file tạm nếu cần
            cleanWs()
            echo "Kết thúc phiên làm việc."
        }
    }
}
