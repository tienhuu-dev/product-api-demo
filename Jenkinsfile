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
                    # BƯỚC 1: Tắt App cũ đang chạy để giải phóng cổng 8386
                    # pkill -f sẽ tìm tiến trình dựa trên tên file jar
                    pkill -f "java -jar target/.*.jar" || true
                    sleep 2

                    # BƯỚC 2: "Bùa hộ mệnh" để Jenkins không kill tiến trình sau khi kết thúc Stage
                    export JENKINS_NODE_COOKIE=dontKillMe

                    # BƯỚC 3: Chạy App ngầm với nohup
                    # Cổng 8386 được cấu hình trực tiếp để dễ quản lý
                    nohup java -jar target/*.jar --server.port=8386 > app.log 2>&1 &
                    
                    echo "🚀 Đang chờ App khởi động..."
                    sleep 20 

                    # BƯỚC 4: Kiểm tra nội bộ xem App đã lên chưa
                    curl -s http://localhost:8386/api/products || echo "⚠️ App chưa phản hồi ngay, hãy kiểm tra lại sau!"
                '''
                echo "✅ Demo API chạy tại: http://localhost:8386/api/products"
            }
        }
    }

    post {
        success { echo "🎉 Pipeline PASS - API & Tests OK!" }
        failure { echo "❌ Pipeline FAIL - kiểm tra test hoặc build" }
    }
}
