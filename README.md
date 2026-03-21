# Product API Demo

REST API đơn giản để quản lý sản phẩm, được xây dựng bằng Spring Boot với CI/CD pipeline sử dụng Jenkins.

## 📋 Mục lục

- [Tổng quan](#-tổng-quan)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Yêu cầu hệ thống](#-yêu-cầu-hệ-thống)
- [Cài đặt và chạy](#-cài-đặt-và-chạy)
- [API Endpoints](#-api-endpoints)
- [Testing](#-testing)
- [CI/CD Pipeline](#-cicd-pipeline)
- [Webhook Auto Triggers](#-webhook-auto-triggers)
- [Docker](#-docker)
- [Database](#-database)

## 🎯 Tổng quan

Project này là một ứng dụng Spring Boot demo cung cấp REST API để quản lý thông tin sản phẩm. Dự án được tích hợp với
Jenkins để tự động hóa quy trình build, test và deploy.

### Tính năng chính

- ✅ CRUD operations cho Product
- ✅ In-memory H2 database
- ✅ RESTful API design
- ✅ Unit & Integration tests
- ✅ Jenkins CI/CD pipeline
- ✅ Docker support
- ✅ Spring Boot DevTools (hot reload)

## 🛠️ Công nghệ sử dụng

- **Java**: 17
- **Spring Boot**: 4.0.4
- **Maven**: 3.x
- **Database**: H2 (in-memory)
- **Testing**: JUnit, Mockito, AssertJ
- **CI/CD**: Jenkins
- **Containerization**: Docker

### Dependencies chính

```xml
- spring-boot-starter-web          # REST API
        - spring-boot-starter-data-jpa     # JPA/Hibernate
        - h2                                # In-memory database
        - lombok                            # Boilerplate code reduction
        - spring-boot-starter-test         # Testing framework
        - jackson-databind                 # JSON processing
```

## 📦 Yêu cầu hệ thống

- **JDK**: 17 hoặc cao hơn
- **Maven**: 3.6+
- **Git**: Để clone repository
- **Jenkins**: 2.541.3+ (nếu chạy CI/CD)
- **Docker**: Nếu muốn chạy trong container

## 🚀 Cài đặt và chạy

### 1. Clone repository

```bash
git clone https://github.com/tienhuu-dev/product-api-demo.git
cd product-api-demo
```

### 2. Build project

```bash
mvn clean install
```

Hoặc build mà không chạy tests:

```bash
mvn clean install -DskipTests
```

### 3. Chạy ứng dụng

#### Cách 1: Sử dụng IntelliJ IDEA (Khuyến nghị)

1. Mở IntelliJ IDEA
2. **File** → **Open** → Chọn thư mục `product-api-demo`
3. Đợi IntelliJ import Maven dependencies
4. Tìm file `ProductApiDemoApplication.java` trong `src/main/java/com/tienhuu/productapidemo/`
5. Click chuột phải vào file → **Run 'ProductApiDemoApplication'**

   Hoặc click vào nút ▶️ màu xanh bên cạnh method `main()`

**Lợi ích:**

- Hot reload với Spring Boot DevTools
- Debug dễ dàng với breakpoints
- Tự động restart khi thay đổi code

#### Cách 2: Sử dụng Maven

```bash
mvn spring-boot:run
```

#### Cách 3: Chạy JAR file

```bash
java -jar target/product-api-demo-0.0.1-SNAPSHOT.jar
```

Ứng dụng sẽ chạy tại: `http://localhost:8386`

## 📡 API Endpoints

Base URL: `http://localhost:8386/api/products`

### Sử dụng Postman để test API

#### 1. GET - Lấy tất cả sản phẩm

**Request:**

- Method: `GET`
- URL: `http://localhost:8386/api/products`

**Response:**

```json
[
  {
    "id": 1,
    "name": "Product Name",
    "price": 99.99,
    "description": "Product Description"
  }
]
```

---

#### 2. GET - Lấy sản phẩm theo ID

**Request:**

- Method: `GET`
- URL: `http://localhost:8386/api/products/{id}`

  Ví dụ: `http://localhost:8386/api/products/1`

**Response:**

```json
{
  "id": 1,
  "name": "Product Name",
  "price": 99.99,
  "description": "Product Description"
}
```

---

#### 3. POST - Tạo sản phẩm mới

**Request:**

- Method: `POST`
- URL: `http://localhost:8386/api/products`
- Headers:
  ```
  Content-Type: application/json
  ```
- Body (raw JSON):
  ```json
  {
    "name": "New Product",
    "price": 49.99,
    "description": "Product description"
  }
  ```

**Response:**

```json
{
  "id": 2,
  "name": "New Product",
  "price": 49.99,
  "description": "Product description"
}
```

---

#### 4. PUT - Cập nhật sản phẩm

**Request:**

- Method: `PUT`
- URL: `http://localhost:8386/api/products/{id}`

  Ví dụ: `http://localhost:8386/api/products/1`
- Headers:
  ```
  Content-Type: application/json
  ```
- Body (raw JSON):
  ```json
  {
    "name": "Updated Product",
    "price": 59.99,
    "description": "Updated description"
  }
  ```

**Response:**

```json
{
  "id": 1,
  "name": "Updated Product",
  "price": 59.99,
  "description": "Updated description"
}
```

---

#### 5. DELETE - Xóa sản phẩm

**Request:**

- Method: `DELETE`
- URL: `http://localhost:8386/api/products/{id}`

  Ví dụ: `http://localhost:8386/api/products/1`

**Response:**

```
Status: 204 No Content
```

### 💡 Tips sử dụng Postman

1. **Tạo Collection**: Tạo một collection mới tên "Product API" để quản lý tất cả requests
2. **Sử dụng Environment Variables**:
    - Tạo variable `baseUrl` = `http://localhost:8386`
    - Sử dụng: `{{baseUrl}}/api/products`
3. **Save Responses**: Lưu response examples để tham khảo
4. **Test Scripts**: Thêm tests để tự động validate responses

## 🧪 Testing

### Chạy tests với IntelliJ IDEA (Khuyến nghị)

#### Chạy tất cả tests

1. Mở panel **Project** (Alt + 1)
2. Click chuột phải vào thư mục `src/test/java`
3. Chọn **Run 'All Tests'** hoặc nhấn **Ctrl + Shift + F10**

#### Chạy một test class cụ thể

1. Mở test class cần chạy
2. Click chuột phải vào tên class → **Run 'ClassName'**
3. Hoặc click vào biểu tượng ▶️ màu xanh bên cạnh tên class

#### Chạy một test method cụ thể

1. Click vào biểu tượng ▶️ bên cạnh method test
2. Hoặc đặt con trỏ trong method → **Ctrl + Shift + F10**

#### Debug tests

1. Click chuột phải vào test → **Debug 'TestName'**
2. Đặt breakpoints bằng cách click vào lề trái
3. Sử dụng Debug panel để step through code

#### Xem kết quả

- Kết quả tests hiển thị trong panel **Run** (Alt + 4)
- ✅ Màu xanh: Test passed
- ❌ Màu đỏ: Test failed
- Click vào test để xem chi tiết error

---

### Chạy tests với Maven

#### Chạy tất cả tests

```bash
mvn test
```

#### Chạy tests với báo cáo

```bash
mvn clean test
```

Test reports sẽ được tạo tại: `target/surefire-reports/`

#### Chạy một test class cụ thể

```bash
mvn test -Dtest=ProductControllerTest
```

#### Chạy một test method cụ thể

```bash
mvn test -Dtest=ProductControllerTest#testGetAllProducts
```

---

### Test Coverage

Project bao gồm:

- **Unit tests** cho Controllers và Services
- **Integration tests** cho Repository layer
- **MockMvc tests** cho REST endpoints

#### Xem Code Coverage trong IntelliJ

1. Click chuột phải vào test/package → **Run with Coverage**
2. Xem báo cáo coverage trong panel **Coverage**
3. Code được highlight:
    - 🟢 Màu xanh: Đã được cover
    - 🔴 Màu đỏ: Chưa được cover
    - 🟡 Màu vàng: Một phần được cover

## 🔄 CI/CD Pipeline

Project được cấu hình với Jenkins pipeline gồm các stage sau:

### Pipeline Stages

1. **Checkout** 📥
    - Clone code từ GitHub repository

2. **Build** 🔨
    - Build project với Maven
    - Skip tests trong giai đoạn này

3. **Unit & Integration Tests** ✅
    - Chạy tất cả tests
    - Tạo JUnit test reports

4. **Package JAR** 📦
    - Archive JAR file artifacts
    - Fingerprint cho version tracking

5. **Deploy** 🚀
    - Deploy ứng dụng local (demo)
    - Khởi động API server
    - Health check

### Cấu hình Jenkins

#### Prerequisites - Global Tool Configuration

Trước khi chạy pipeline, cần cấu hình JDK và Maven trong Jenkins:

##### 1. Cấu hình JDK

1. Vào **Manage Jenkins** → **Global Tool Configuration**
2. Tìm section **JDK**
3. Click **Add JDK**
4. Cấu hình như sau:
   - **Name**: `JDK17` (phải trùng với tên trong Jenkinsfile)
   - **JAVA_HOME**: Đường dẫn đến JDK 17 trên máy
     - Windows: `C:\Program Files\Java\jdk-17`
     - Linux/Mac: `/usr/lib/jvm/java-17-openjdk` hoặc `/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home`
   - Hoặc tích **Install automatically** và chọn version JDK 17

##### 2. Cấu hình Maven

1. Trong cùng trang **Global Tool Configuration**
2. Tìm section **Maven**
3. Click **Add Maven**
4. Cấu hình như sau:
   - **Name**: `Maven3` (phải trùng với tên trong Jenkinsfile)
   - **MAVEN_HOME**: Đường dẫn đến Maven trên máy
     - Windows: `C:\Program Files\Apache\maven`
     - Linux/Mac: `/usr/share/maven` hoặc `/opt/maven`
   - Hoặc tích **Install automatically** và chọn version Maven 3.9.x

##### 3. Lưu cấu hình

Click **Save** ở cuối trang

##### 4. Verify trong Jenkinsfile

```groovy
tools {
    jdk 'JDK17'      // Tên phải khớp với Global Tool Configuration
    maven 'Maven3'   // Tên phải khớp với Global Tool Configuration
}
```

**⚠️ Lưu ý quan trọng:**
- Tên trong `tools` block phải **khớp chính xác** với tên đã đặt trong Global Tool Configuration
- Nếu tên không khớp, pipeline sẽ báo lỗi: `Tool type "jdk" does not have an install of "JDK17" configured`

#### Kết quả Pipeline

- ✅ **Success**: API & Tests đều PASS
- ❌ **Failure**: Kiểm tra logs để debug

## 🔗 Webhook Auto Triggers

Cấu hình tự động trigger Jenkins pipeline khi có push/commit lên GitHub sử dụng Webhook và Cloudflare Tunnel.

### Tổng quan

- **Branch**: `feature/webhook-auto-triggers`
- **Pipeline Type**: **Multibranch Pipeline**
- **Mục đích**: Tự động chạy pipeline khi có thay đổi trên bất kỳ branch nào
- **Công nghệ**: GitHub Webhook + Cloudflare Tunnel (expose local Jenkins ra internet)

### Cloudflare Tunnel Setup

Cloudflare Tunnel giúp expose Jenkins server local (localhost:8080) ra internet mà không cần public IP hay mở port firewall.

#### 1. Chạy Cloudflare Tunnel

```bash
docker run --rm --network jenkins cloudflare/cloudflared:latest tunnel --url http://jenkins-blueocean:8080
```

**Giải thích:**
- `--rm`: Tự động xóa container khi dừng
- `--network jenkins`: Kết nối vào cùng Docker network với Jenkins
- `tunnel --url http://jenkins-blueocean:8080`: Tạo tunnel đến Jenkins container

**Output:**
```
Your quick Tunnel has been created! Visit it at (it may take some time to be reachable):
https://random-name-1234.trycloudflare.com
```

**⚠️ Lưu ý:**
- URL này chỉ tồn tại khi container đang chạy
- Mỗi lần chạy sẽ tạo URL mới (random)
- Để sử dụng lâu dài, giữ terminal/container chạy hoặc dùng Cloudflare Tunnel với domain riêng

#### 2. Copy URL Public

Copy URL `https://random-name-1234.trycloudflare.com` để cấu hình GitHub Webhook

### Cấu hình Jenkins Credentials

Trước khi setup webhook, cần tạo credentials để GitHub có thể authenticate với Jenkins.

#### 1. Tạo GitHub Personal Access Token (PAT)

1. Vào GitHub → **Settings** → **Developer settings** → **Personal access tokens** → **Tokens (classic)**
2. Click **Generate new token** → **Generate new token (classic)**
3. Cấu hình:
   - **Note**: `Jenkins Webhook`
   - **Expiration**: Chọn thời gian phù hợp (ví dụ: 90 days)
   - **Select scopes**: Chọn các quyền sau
     - ✅ `repo` (Full control of private repositories)
     - ✅ `admin:repo_hook` (Full control of repository hooks)
4. Click **Generate token**
5. **⚠️ Copy token ngay** (chỉ hiển thị 1 lần): `ghp_xxxxxxxxxxxxxxxxxxxx`

#### 2. Thêm Credentials vào Jenkins

1. Vào Jenkins → **Manage Jenkins** → **Credentials**
2. Click vào **(global)** domain
3. Click **Add Credentials**
4. Cấu hình như sau:

   **Loại**: `Username with password`
   
   - **Username**: GitHub username của bạn (ví dụ: `tienhuu-dev`)
   - **Password**: GitHub Personal Access Token vừa tạo (ví dụ: `ghp_xxxxxxxxxxxxxxxxxxxx`)
   - **ID**: `github-credentials` (hoặc tên bạn muốn)
   - **Description**: `GitHub PAT for Webhook`

5. Click **Create**

### Cấu hình Webhook trên GitHub

#### 1. Vào Repository Settings

1. Truy cập repository: `https://github.com/tienhuu-dev/product-api-demo`
2. Click tab **Settings**
3. Sidebar bên trái → Click **Webhooks**
4. Click **Add webhook**

#### 2. Cấu hình Webhook

**Payload URL:**
```
https://random-name-1234.trycloudflare.com/github-webhook/
```

**⚠️ Lưu ý:** 
- Thay `random-name-1234.trycloudflare.com` bằng URL từ Cloudflare Tunnel
- Phải có `/github-webhook/` ở cuối

**Content type:**
```
application/json
```

**Secret:** (Tùy chọn, để trống nếu không dùng)

**Which events would you like to trigger this webhook?**
- Chọn: **Just the push event** (hoặc custom nếu cần)

**Active:**
- ✅ Tích chọn

Click **Add webhook**

#### 3. Verify Webhook

Sau khi tạo, GitHub sẽ gửi một ping request. Kiểm tra:
- Trong danh sách Webhooks, click vào webhook vừa tạo
- Tab **Recent Deliveries** sẽ hiển thị ping request
- Status code `200` = Thành công ✅

### Tạo Multibranch Pipeline trong Jenkins

#### 1. Tạo Multibranch Pipeline mới

1. Vào Jenkins Dashboard
2. Click **New Item**
3. Nhập tên: `product-api-demo` (hoặc tên bạn muốn)
4. Chọn **Multibranch Pipeline**
5. Click **OK**

#### 2. Cấu hình Branch Sources

Trong màn hình cấu hình:

**Section: Branch Sources**

1. Click **Add source** → Chọn **GitHub**

2. Cấu hình GitHub source:
   - **Credentials**: Chọn credentials đã tạo (`github-credentials`)
   - **Repository HTTPS URL**: `https://github.com/tienhuu-dev/product-api-demo`
   - Hoặc **Owner**: `tienhuu-dev` và **Repository**: `product-api-demo`

3. **Behaviours** (mở rộng để cấu hình):
   - ✅ **Discover branches**: All branches
   - ✅ **Discover pull requests from origin**: 
     - Strategy: Merging the pull request with the current target branch revision
   - Có thể bỏ chọn các behaviours không cần thiết

#### 3. Cấu hình Build Configuration

**Section: Build Configuration**

- **Mode**: `by Jenkinsfile`
- **Script Path**: `Jenkinsfile` (mặc định, nếu file ở root)

#### 4. Cấu hình Scan Multibranch Pipeline Triggers

**Section: Scan Multibranch Pipeline Triggers**

- ✅ Tích chọn **Scan by webhook**
  - **Trigger token**: Tạo một token bất kỳ (ví dụ: `product-api-token`)
  - Hoặc để trống nếu dùng GitHub hook mặc định

- Hoặc ✅ Tích chọn **Periodically if not otherwise run**
  - **Interval**: 1 minute (hoặc tùy chọn)

**⚠️ Quan trọng:**
- **KHÔNG cần** tích "Build whenever a SNAPSHOT dependency is built"
- **KHÔNG cần** tích "Poll SCM" (webhook sẽ tự động trigger)

#### 5. Lưu cấu hình

Click **Save**

Jenkins sẽ tự động:
- Scan repository lần đầu
- Phát hiện tất cả branches có Jenkinsfile
- Tạo pipeline cho mỗi branch

### Kiểm tra Multibranch Pipeline đã được tạo

Sau khi Save, Jenkins sẽ:
1. Hiển thị danh sách các branch được phát hiện
2. Mỗi branch là một sub-pipeline
3. Click vào branch để xem build history

Ví dụ:
```
product-api-demo
├── main
├── feature/webhook-auto-triggers
└── develop (nếu có)
```

### Test Webhook với Multibranch Pipeline

#### 1. Test trên branch hiện tại

```bash
git checkout feature/webhook-auto-triggers
echo "Test webhook multibranch" >> test.txt
git add .
git commit -m "Test webhook trigger for multibranch"
git push origin feature/webhook-auto-triggers
```

#### 2. Kiểm tra Jenkins

- Jenkins sẽ tự động:
  1. Nhận webhook từ GitHub
  2. Scan repository
  3. Phát hiện branch `feature/webhook-auto-triggers` có thay đổi
  4. Trigger build cho branch đó
  
- Vào Dashboard → `product-api-demo` → Branch `feature/webhook-auto-triggers`
- Build mới sẽ xuất hiện trong Build History

#### 3. Test tạo branch mới

```bash
git checkout -b feature/test-new-branch
echo "New branch test" > newfile.txt
git add .
git commit -m "Test new branch detection"
git push origin feature/test-new-branch
```

Jenkins sẽ:
- Tự động phát hiện branch mới
- Tạo pipeline cho branch mới
- Chạy build lần đầu

#### 4. Kiểm tra GitHub Webhook

- Vào Settings → Webhooks → Click vào webhook
- Tab **Recent Deliveries** sẽ hiển thị:
  - Push event cho mỗi commit
  - Status `200` = Webhook thành công
  - Response từ Jenkins

### Troubleshooting Multibranch Pipeline

#### Webhook không trigger scan

**1. Kiểm tra webhook delivery trên GitHub:**
```
Settings → Webhooks → Recent Deliveries
```
- Nếu Status 2xx → Webhook đã gửi thành công
- Nếu Status 4xx/5xx → Có lỗi (xem Response)

**2. Kiểm tra Jenkins Multibranch scan log:**
- Vào Multibranch Pipeline → Click **Scan Multibranch Pipeline Log**
- Xem log để biết Jenkins có nhận được webhook không

**3. Trigger scan thủ công:**
- Vào Multibranch Pipeline
- Click **Scan Multibranch Pipeline Now**
- Xem có phát hiện branches không

#### Branch không được phát hiện

**1. Kiểm tra Jenkinsfile:**
- Branch cần có file `Jenkinsfile` ở root
- Nội dung Jenkinsfile phải hợp lệ

**2. Kiểm tra Branch Sources filter:**
- Vào Configure → Branch Sources
- Xem filter có exclude branch không
- Include: `*` (tất cả branches)

#### Credentials không hoạt động

**1. Test credentials:**
```bash
curl -u tienhuu-dev:ghp_xxxx https://api.github.com/user
```
- Nếu trả về thông tin user → Credentials OK
- Nếu 401 → Credentials sai

**2. Kiểm tra scope của PAT:**
- PAT cần có scope: `repo`, `admin:repo_hook`

### Chạy Cloudflare Tunnel lâu dài

#### Option 1: Chạy trong background

```bash
docker run -d --name cloudflare-tunnel --network jenkins cloudflare/cloudflared:latest tunnel --url http://jenkins-blueocean:8080
```

Xem logs:
```bash
docker logs -f cloudflare-tunnel
```

Xem URL:
```bash
docker logs cloudflare-tunnel 2>&1 | grep -o 'https://.*\.trycloudflare\.com'
```

#### Option 2: Sử dụng Cloudflare Named Tunnel (Khuyến nghị cho production)

1. Tạo Cloudflare account
2. Setup Cloudflare Tunnel với domain riêng
3. URL cố định: `https://jenkins.yourdomain.com`

### Lợi ích của Multibranch Pipeline + Webhook

✅ **Tự động phát hiện branches:** Không cần tạo pipeline cho mỗi branch thủ công
✅ **Isolation:** Mỗi branch có pipeline riêng, không ảnh hưởng lẫn nhau
✅ **PR Testing:** Có thể test Pull Requests trước khi merge
✅ **Tự động cleanup:** Xóa pipeline khi branch bị xóa
✅ **Fast feedback:** Mỗi commit trên mọi branch đều được test tự động
✅ **Branch strategy support:** Phù hợp với Git Flow, GitHub Flow

## 🐳 Docker

### Dockerfile

Project bao gồm Dockerfile để setup Jenkins với các tools cần thiết:

- Jenkins 2.541.3 với JDK 21
- Git
- Maven
- Docker CLI
- Blue Ocean và Docker Workflow plugins

### Setup Jenkins với Docker

#### 1. Tạo Docker network

```bash
docker network create jenkins
```

#### 2. Build Docker image

```bash
docker build -t myjenkins-blueocean:2.541.3-1 .
```

#### 3. Run Jenkins container

**Trên Windows (Command Prompt):**

```cmd
docker run --name jenkins-blueocean --restart=on-failure --detach ^
  --network jenkins --env DOCKER_HOST=tcp://docker:2376 ^
  --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 ^
  --volume jenkins-data:/var/jenkins_home ^
  --volume jenkins-docker-certs:/certs/client:ro ^
  --publish 8080:8080 --publish 8386:8386 myjenkins-blueocean:2.541.3-1
```

**Trên Linux/MacOS:**

```bash
docker run --name jenkins-blueocean --restart=on-failure --detach \
  --network jenkins --env DOCKER_HOST=tcp://docker:2376 \
  --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client:ro \
  --publish 8080:8080 --publish 8386:8386 myjenkins-blueocean:2.541.3-1
```

#### 4. Lấy initial admin password

```bash
docker exec jenkins-blueocean cat /var/jenkins_home/secrets/initialAdminPassword
```

#### 5. Truy cập Jenkins

Mở trình duyệt và truy cập: `http://localhost:8080`

### Docker Commands hữu ích

```bash
# Xem logs
docker logs jenkins-blueocean

# Xem logs realtime
docker logs -f jenkins-blueocean

# Stop container
docker stop jenkins-blueocean

# Start container
docker start jenkins-blueocean

# Restart container
docker restart jenkins-blueocean

# Xóa container
docker rm jenkins-blueocean

# Xóa volumes (nếu cần reset hoàn toàn)
docker volume rm jenkins-data jenkins-docker-certs
```

## 💾 Database

### H2 Console

Truy cập H2 Console tại: `http://localhost:8386/h2-console`

**Connection settings:**

```
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (để trống)
```

### Schema

```sql
CREATE TABLE product
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    price       DECIMAL(10, 2),
    description TEXT
);
```

### Sample Data

Dữ liệu mẫu có thể được load từ `data.sql` (nếu có) hoặc qua API.

## 📁 Cấu trúc project

```
product-api-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/tienhuu/productapidemo/
│   │   │       ├── controller/      # REST Controllers
│   │   │       ├── model/           # Entity classes
│   │   │       ├── repository/      # JPA Repositories
│   │   │       ├── service/         # Business logic
│   │   │       └── ProductApiDemoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql (optional)
│   └── test/
│       └── java/                    # Test classes
├── Dockerfile                       # Jenkins Docker config
├── Jenkinsfile                      # CI/CD pipeline definition
├── pom.xml                          # Maven configuration
└── README.md
```

## 🔧 Configuration

### application.properties

Các cấu hình chính có thể tuỳ chỉnh:

```properties
# Server
server.port=8386
# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
# JPA
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

## 🤝 Contributing

1. Fork repository
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📝 License

Project này được tạo cho mục đích học tập và demo.

## 👤 Author

**tienhuu-dev**

- GitHub: [@tienhuu-dev](https://github.com/tienhuu-dev)

## 🙏 Acknowledgments

- Spring Boot Documentation
- Jenkins Documentation
- Maven Central Repository

---

**Note**: Đây là project demo, không nên sử dụng trực tiếp trong production mà không có các cải tiến về security, error
handling, và configuration management.
