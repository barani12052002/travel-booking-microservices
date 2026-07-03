# 🌍 Travel Booking Microservices

[![Build and Deploy](https://github.com/barani12052002/travel-booking-microservices/actions/workflows/deploy.yml/badge.svg)](https://github.com/barani12052002/travel-booking-microservices/actions/workflows/deploy.yml)

A production-ready **Travel Booking Microservices** application built using **Spring Boot**, **Spring Cloud**, **Spring Security (JWT)**, **Docker**, **GitHub Actions CI/CD**, and **AWS EC2**.

This project demonstrates how a real-world microservices architecture can be designed, secured, containerized, and automatically deployed using modern backend development practices.

---

# 🚀 Features

### 🔐 Authentication & Authorization
- User Registration
- User Login
- JWT Authentication
- Role-Based Authorization (USER / ADMIN)
- BCrypt Password Encryption
- Stateless Security

### ✈️ Booking Management
- Create Booking
- Get Booking Details
- Cancel Booking
- Customer Booking History
- Booking Reference Generation

### 📧 Notifications
- Email Confirmation
- PDF Ticket Generation
- QR Code Generation

### 🌐 Microservices
- API Gateway
- Eureka Discovery Server
- Booking Service
- Provider Service
- Service Discovery
- OpenFeign Client Communication
- Resilience4j Circuit Breaker

### 📄 API Documentation
- Swagger UI
- OpenAPI 3
- JWT Authorization Support

### ☁️ DevOps
- Docker
- Docker Compose
- GitHub Actions CI/CD
- Docker Hub
- AWS EC2 Deployment

---

# 🛠 Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Build Tool | Maven |
| Security | Spring Security, JWT |
| Database | MySQL 8 |
| ORM | Spring Data JPA, Hibernate |
| Microservices | Spring Cloud, Eureka |
| API Gateway | Spring Cloud Gateway |
| Communication | OpenFeign |
| Fault Tolerance | Resilience4j |
| Documentation | Swagger OpenAPI |
| Containerization | Docker |
| Orchestration | Docker Compose |
| CI/CD | GitHub Actions |
| Cloud | AWS EC2 |
| Email | Spring Mail |
| PDF | iText PDF |
| QR Code | ZXing |

---

# 🏗 Microservices Architecture

```
                        Client
                           │
                           ▼
                  API Gateway (8080)
                           │
          ┌────────────────┴────────────────┐
          ▼                                 ▼
 Booking Service                     Provider Service
          │
          ▼
      MySQL Database
          │
          ▼
 Email + PDF Ticket + QR Code

        Eureka Discovery Server
```

---

# 📂 Project Structure

```
travel-booking-microservices
│
├── api-gateway
├── booking-service
├── provider-service
├── discovery-server
├── config-server
├── docker-compose.yml
├── .github
│   └── workflows
│       └── deploy.yml
└── README.md
```

---

# 🔐 Authentication Flow

```
User
 │
 ▼
Register/Login
 │
 ▼
JWT Token
 │
 ▼
Authorization Header

Bearer <JWT_TOKEN>

 │
 ▼
Protected APIs
```

---

# 📚 REST APIs

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | `/auth/register` |
| POST | `/auth/login` |

---

## Booking APIs

| Method | Endpoint |
|---------|----------|
| POST | `/booking` |
| GET | `/booking/{bookingReference}` |
| GET | `/booking/customer/{email}` |
| PUT | `/booking/cancel/{bookingReference}` |
| GET | `/booking` (ADMIN) |

---

# 🔑 JWT Authorization

After login, you'll receive:

```json
{
  "accessToken": "YOUR_JWT_TOKEN"
}
```

Use it in requests:

```
Authorization: Bearer YOUR_JWT_TOKEN
```

Swagger also supports JWT authentication through the **Authorize** button.

---

# 🐳 Docker

## Build

```bash
docker compose build
```

## Start

```bash
docker compose up -d
```

## Stop

```bash
docker compose down
```

---

# ☁️ AWS Deployment

This project is deployed on an AWS EC2 instance using Docker Compose.

Deployment workflow:

```
Developer
     │
     ▼
Git Push
     │
     ▼
GitHub Actions
     │
     ▼
Build Maven Projects
     │
     ▼
Build Docker Images
     │
     ▼
Push Images to Docker Hub
     │
     ▼
SSH into EC2
     │
     ▼
docker compose pull
docker compose up -d
```

---

# 🔄 GitHub Actions CI/CD

Every push to the **main** branch automatically:

- Checks out the project
- Builds all microservices
- Creates Docker images
- Pushes images to Docker Hub
- Connects to AWS EC2
- Pulls latest images
- Restarts containers

Workflow file:

```
.github/workflows/deploy.yml
```

---

# 📖 Swagger Documentation

## API Gateway

```
http://localhost:8080
```

## Booking Service

```
http://localhost:8081/swagger-ui/index.html
```

## Provider Service

```
http://localhost:8082/swagger-ui/index.html
```

---

# 🎯 Eureka Dashboard

```
http://localhost:8761
```

---

# 📸 Screenshots

## Swagger UI

> Add screenshot here

---

## JWT Authorization

> Add screenshot here

---

## Booking Created Successfully

> Add screenshot here

---

## Booking Confirmation Email

> Add screenshot here

---

## PDF Ticket

> Add screenshot here

---

## QR Code

> Add screenshot here

---

## Eureka Dashboard

> Add screenshot here

---

## GitHub Actions

> Add screenshot here

---

## Docker Containers

> Add screenshot here

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/barani12052002/travel-booking-microservices.git
```

```
cd travel-booking-microservices
```

---

## Configure MySQL

Update:

```
application.properties
```

```
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

---

## Run Services

```bash
docker compose up --build
```

---

# 📈 Future Improvements

- Refresh Token Authentication
- Redis Cache
- Spring Cloud Config Server
- RabbitMQ / Kafka Event Messaging
- Centralized Logging (ELK Stack)
- Prometheus Monitoring
- Grafana Dashboard
- AWS RDS
- AWS S3 Ticket Storage
- Kubernetes Deployment
- Terraform Infrastructure

---

# 👨‍💻 Author

## Barani K

**Java Backend Developer**

### Skills

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Cloud
- REST APIs
- Microservices
- Hibernate
- JPA
- MySQL
- Docker
- GitHub Actions
- AWS EC2
- Maven
- OpenFeign
- Resilience4j

### GitHub

https://github.com/barani12052002

### LinkedIn

> Add your LinkedIn profile URL here

---

# ⭐ Support

If you found this project useful, please consider giving it a ⭐ on GitHub.

It helps others discover the project and supports future improvements.

---

## 📄 License

This project is licensed under the MIT License.
