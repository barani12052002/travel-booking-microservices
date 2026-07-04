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

<img width="1342" height="638" alt="Swagger UI" src="https://github.com/user-attachments/assets/28211ae1-797e-4db4-9d78-0bf5be1b16a6" />

---

## JWT Authorization

<img width="1332" height="474" alt="JWT" src="https://github.com/user-attachments/assets/f0c69bd0-193f-4435-a9ac-362d409ac272" />

<img width="1334" height="641" alt="JWT 2" src="https://github.com/user-attachments/assets/9fa6a5b4-1fb4-42c2-bf3d-dda5f8cf1c0c" />

---

## Booking Created Successfully

<img width="1346" height="639" alt="booking reesponse" src="https://github.com/user-attachments/assets/9a2a5d57-d26f-404a-901f-9294a0259878" />

---

## Booking Confirmation Email

<img width="1018" height="630" alt="booking mail" src="https://github.com/user-attachments/assets/20104b85-9744-4b1f-9c9b-6b04b93726ed" />

---

## PDF Ticket

<img width="332" height="604" alt="ticket pdf" src="https://github.com/user-attachments/assets/af678562-ec99-43d2-9724-ef08d6de1b17" />

---

## QR Code

<img width="1072" height="627" alt="QR Code" src="https://github.com/user-attachments/assets/6c27d04b-420e-4e59-9670-ddbf232fe51f" />

---

## Eureka Dashboard

<img width="1350" height="642" alt="eureka-dashboard" src="https://github.com/user-attachments/assets/b3f35d4c-71bf-442e-9501-e0fe493f4c55" />

---

## GitHub Actions

<img width="1359" height="617" alt="github action" src="https://github.com/user-attachments/assets/aa62551c-0d86-4b89-8fce-80cda0a87e6e" />

---

## Docker Containers

<img width="1366" height="768" alt="docker_desktop" src="https://github.com/user-attachments/assets/add6e697-6715-4e16-9ffe-cea67d809265" />

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

https://www.linkedin.com/in/k-barani
---

# ⭐ Support

If you found this project useful, please consider giving it a ⭐ on GitHub.

It helps others discover the project and supports future improvements.

---

## 📄 License

This project is licensed under the MIT License.
