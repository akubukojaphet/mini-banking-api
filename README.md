# 🏦 Mini Banking Backend API

> A production-style banking backend API built with **Java Spring Boot**, PostgreSQL, JWT Authentication, Docker, and deployed on **AWS EC2**.
> This project simulates core banking operations such as user registration, authentication, balance checks, deposits, transfers, and transaction history management using secure REST APIs.

---

## 🚀 Live Demo

```
Base URL: http://3.81.116.79:9090/api
```

---

## 📌 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Database Schema](#database-schema)
- [Docker](#docker)
- [Docker Compose](#docker-compose)
- [Deployment](#deployment)
- [Security](#security)
- [Future Improvements](#future-improvements)
- [Screenshots](#screenshots)
- [Author](#author)

---

## ✅ Features

- 🔐 **User Registration** — Secure account creation with BCrypt password hashing
- 🪙 **User Login with JWT Authentication** — Stateless token-based auth on all protected routes
- 🔒 **Secure Password Encryption** — BCrypt hashing on all passwords
- 📊 **Check Account Balance** — View real-time balance
- 💰 **Deposit Funds** — Add money to your account
- 💸 **Transfer Money Between Users** — Send funds instantly
- 📜 **Transaction History Tracking** — Full audit log of all activity
- 🐘 **PostgreSQL Database Integration** — Relational data persistence
- 🐳 **Dockerized Application** — Runs in isolated containers
- 🔧 **Docker Compose Multi-Container Setup** — App + DB together
- ☁️ **AWS EC2 Cloud Deployment** — Live on the internet
- 🌐 **RESTful API Architecture** — Clean, standard API design

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Backend Programming |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Security |
| JWT | Token-Based Authentication |
| PostgreSQL | Relational Database |
| Docker | Containerization |
| Docker Compose | Multi-container Orchestration |
| AWS EC2 | Cloud Deployment |
| Maven | Dependency Management |
| Git & GitHub | Version Control |

---

## 📂 Project Structure

```
mini-banking-api/
│
├── src/main/java/com/japhtech/mini_banking_api/
│   ├── controller/          # REST endpoints (HTTP layer)
│   │   ├── AuthController.java
│   │   └── AccountController.java
│   ├── service/             # Business logic
│   │   ├── AuthService.java
│   │   └── AccountService.java
│   ├── repository/          # Database access (Spring Data JPA)
│   │   ├── UserRepository.java
│   │   └── TransactionRepository.java
│   ├── model/               # JPA Entities (DB tables)
│   │   ├── User.java
│   │   └── Transaction.java
│   ├── dto/                 # Request/Response objects
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── DepositRequest.java
│   │   └── TransferRequest.java
│   ├── security/            # JWT + Spring Security config
│   │   ├── JwtUtil.java
│   │   ├── JwtFilter.java
│   │   └── SecurityConfig.java
│   ├── config/              # App configuration
│   └── exception/           # Global error handling
│       └── GlobalExceptionHandler.java
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## 🏁 Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 17+
- Maven 3.x
- PostgreSQL
- Docker & Docker Compose
- Postman (for testing)

### 1. Clone the repository

```bash
git clone https://github.com/akubukojaphet/mini-banking-api.git
cd mini-banking-api
```

### 2. Set up PostgreSQL

```bash
sudo -u postgres psql
```

```sql
CREATE DATABASE mini_bank;
CREATE USER bank_user WITH PASSWORD 'BankPass123';
GRANT ALL PRIVILEGES ON DATABASE mini_bank TO bank_user;
\q
```

### 3. Configure environment

Open `src/main/resources/application.properties` and update:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mini_bank
spring.datasource.username=bank_user
spring.datasource.password=BankPass123
jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
server.port=8080
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The API will be live at `http://localhost:8080`

---

## 📡 API Endpoints

### Authentication (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/auth/register` | Register new user |
| `POST` | `/api/auth/login` | Login and receive JWT |

### Account Operations (🔐 Protected — requires Bearer Token)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/account/balance` | Check balance |
| `POST` | `/api/account/deposit` | Deposit money |
| `POST` | `/api/account/transfer` | Transfer funds |
| `GET` | `/api/account/transactions` | Transaction history |

---

## 🔐 Authentication

This API uses **JWT (JSON Web Token)** for stateless authentication.

**Step 1 — Register:**
```json
POST /api/auth/register

{
  "fullName": "Japhet Akubuko",
  "email": "japhet@example.com",
  "password": "password123"
}
```

**Step 2 — Login (get your token):**
```json
POST /api/auth/login

{
  "email": "japhet@example.com",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Step 3 — Use token on protected routes:**

Add this header to every protected request:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 💳 Request Examples

**Deposit:**
```json
POST /api/account/deposit
Authorization: Bearer <token>

{
  "amount": 5000
}
```

**Transfer:**
```json
POST /api/account/transfer
Authorization: Bearer <token>

{
  "receiverEmail": "victor@example.com",
  "amount": 2000
}
```

---

## 🗄 Database Schema

### users table

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated ID |
| full_name | VARCHAR | User's full name |
| email | VARCHAR (unique) | Login email |
| password | VARCHAR | BCrypt hashed password |
| balance | DOUBLE | Current account balance |
| created_at | TIMESTAMP | Account creation time |

### transactions table

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated ID |
| sender_email | VARCHAR | Who sent the money |
| receiver_email | VARCHAR | Who received the money |
| amount | DOUBLE | Transaction amount |
| transaction_type | VARCHAR | DEPOSIT or TRANSFER |
| created_at | TIMESTAMP | Transaction timestamp |

---

## 🐳 Docker

### Build JAR

```bash
./mvnw clean package -DskipTests
```

### Build Docker Image

```bash
docker build -t mini-banking-api .
```

### Run Docker Container

```bash
docker run -p 9090:8080 mini-banking-api
```

### Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 🐳 Docker Compose

Run PostgreSQL + Spring Boot together:

```bash
docker compose up --build -d
```

Stop containers:

```bash
docker compose down
```

### docker-compose.yml

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: mini_bank
      POSTGRES_USER: bank_user
      POSTGRES_PASSWORD: BankPass123
    ports:
      - "5432:5432"

  app:
    build: .
    ports:
      - "9090:8080"
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/mini_bank
      SPRING_DATASOURCE_USERNAME: bank_user
      SPRING_DATASOURCE_PASSWORD: BankPass123
```

---

## ☁️ Deployment (AWS EC2)

### Server Details

| Property | Value |
|----------|-------|
| Public IP | 3.81.116.79 |
| Private IP | 172.31.37.53 |
| OS | Ubuntu 22.04 LTS |
| Instance Type | t2.micro |
| Port | 9090 |

### Server Setup

```bash
# SSH into EC2
ssh -i your-key.pem ubuntu@3.81.116.79

# Install Docker and Git
sudo apt update
sudo apt install docker.io git -y
sudo systemctl start docker

# Clone and run
git clone https://github.com/akubukojaphet/mini-banking-api.git
cd mini-banking-api
docker compose up --build -d
```

### EC2 Security Group Rules

| Type | Port | Source |
|------|------|--------|
| SSH | 22 | Your IP |
| HTTP | 80 | 0.0.0.0/0 |
| Custom TCP | 9090 | 0.0.0.0/0 |

Live API accessible at:
```
http://3.81.116.79:9090/api
```

---

## 🔒 Security Features

- BCrypt password hashing
- JWT token authentication
- Protected REST endpoints
- Environment variable support
- Dockerized isolated services

---

## 📈 Future Improvements

- 📄 Swagger/OpenAPI Documentation
- 🔁 Refresh Tokens
- 👥 Role-Based Access Control
- ⚙️ CI/CD with GitHub Actions
- ☸️ Kubernetes Deployment
- 🔐 HTTPS with Nginx Reverse Proxy
- 🗄️ AWS RDS Integration
- 📊 Monitoring with Prometheus & Grafana

---

## 📸 Screenshots

### AWS EC2 Deployment
![AWS EC2](<img width="1919" height="996" alt="Screenshot 2026-05-09 162148" src="https://github.com/user-attachments/assets/d2b2b45a-7d87-489b-9ff7-ec29454bcea6" />
)

### Docker Containers Running
![Docker](<img width="1919" height="346" alt="Screenshot 2026-05-09 170057" src="https://github.com/user-attachments/assets/e0453929-379d-44f3-bbf6-a40e0e72b946" />
)

### Postman — Register API
![Register](<img width="1918" height="1130" alt="Screenshot 2026-05-09 175559" src="https://github.com/user-attachments/assets/763fdcad-c32a-4372-b08f-1c9572260968" />
)

### Postman — Login + JWT Token
![JWT Token](<img width="1919" height="1127" alt="Screenshot 2026-05-09 183655" src="https://github.com/user-attachments/assets/2f7b74bf-a712-4737-bb25-5e7330337684" />
)

### PostgreSQL Tables with Data
![Database](<img width="1919" height="399" alt="Screenshot 2026-05-09 165305" src="https://github.com/user-attachments/assets/313ef235-4ae6-4417-bc02-7f56fabfa17d" />
)

### GitHub Repository
<img width="1919" height="1061" alt="image" src="https://github.com/user-attachments/assets/cc062b08-a8b1-438a-8f1d-117dc9308409" />


---

## 👨‍💻 Author

**Japhet Akubuko**

Cloud & DevOps Engineer | Backend Developer

- 🐙 GitHub: [@akubukojaphet](https://github.com/akubukojaphet)
- 💼 LinkedIn: [akubuko-japhet](https://www.linkedin.com/in/akubuko-japhet)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

> Built with ☕ Java, 🔐 Spring Security, 🐳 Docker, and ☁️ AWS
