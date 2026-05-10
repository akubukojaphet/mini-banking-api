# 🏦 Mini Banking Backend API

> A production-style banking backend API built with **Java Spring Boot**, PostgreSQL, JWT Authentication, Docker, and deployed on **AWS EC2**.
> This project simulates core banking operations such as user registration, authentication, balance checks, deposits, transfers, and transaction history management using secure REST APIs.

---

## 🚀 Live Demo

```
Base URL: http://YOUR_EC2_PUBLIC_IP:9090/api
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
CREATE USER bank_user WITH PASSWORD '${POSTGRES_PASSWORD}';
GRANT ALL PRIVILEGES ON DATABASE mini_bank TO bank_user;
\q
```

### 3. Configure environment

Open `src/main/resources/application.properties` and update:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mini_bank
spring.datasource.username=bank_user
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
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
    image: postgres:16
    container_name: mini-bank-postgres
    restart: unless-stopped
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    ports:
      - "5433:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${POSTGRES_USER} -d ${POSTGRES_DB}"]
      interval: 5s
      timeout: 3s
      retries: 5
    networks:
      - app-net

  app:
    build: .
    container_name: mini-banking-api
    restart: unless-stopped
    depends_on:
      postgres:
        condition: service_healthy
    ports:
      - "9090:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/${POSTGRES_DB}
      SPRING_DATASOURCE_USERNAME: ${POSTGRES_USER}
      SPRING_DATASOURCE_PASSWORD: ${POSTGRES_PASSWORD}
      JWT_SECRET: ${JWT_SECRET}
      JWT_EXPIRATION: ${JWT_EXPIRATION}
    networks:
      - app-net

volumes:
  postgres_data:

networks:
  app-net:
```
> Sensitive values are stored in a local `.env` file and are not committed to GitHub.
---

## ☁️ Deployment (AWS EC2)

### Server Details

| Property | Value |
|----------|-------|
| Public IP | YOUR_EC2_PUBLIC_IP |
| Private IP | YOUR_PRIVATE_IP |
| OS | Ubuntu 22.04 LTS |
| Instance Type | t2.micro |
| Port | 9090 |

### Server Setup

```bash
# SSH into EC2
ssh -i your-key.pem ubuntu@YOUR_EC2_PUBLIC_IP

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
http://YOUR_EC2_PUBLIC_IP:9090/api
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
<img width="1281" height="663" alt="image" src="https://github.com/user-attachments/assets/785e7688-8d7a-4854-9321-c2c811fc588d" />


### Docker Containers Running
<img width="1417" height="615" alt="image" src="https://github.com/user-attachments/assets/ff01fb42-f8b9-4ce9-9f4f-9e6bfbf4fa85" />


### Postman — Register API
<img width="1124" height="658" alt="image" src="https://github.com/user-attachments/assets/caefaeea-e357-4c8f-9d31-492a1478a886" />


### Postman — Login + JWT Token
<img width="1124" height="660" alt="image" src="https://github.com/user-attachments/assets/9fd354fa-64a8-47b4-ac6e-29ff388776ee" />


### PostgreSQL Tables with Data
<img width="1415" height="292" alt="image" src="https://github.com/user-attachments/assets/934cdb36-799c-4510-9632-18bf30dbf5bd" />


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
