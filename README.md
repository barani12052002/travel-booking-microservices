# 🌍 Travel Booking Microservices

[![Build and Deploy](https://github.com/barani12052002/travel-booking-microservices/actions/workflows/deploy.yml/badge.svg)](https://github.com/barani12052002/travel-booking-microservices/actions/workflows/deploy.yml)

A production-style Travel Booking Microservices application built with Spring Boot, Spring Cloud, Docker, GitHub Actions, and AWS EC2.

## 🚀 Features

- JWT Authentication & Authorization
- Spring Security
- API Gateway
- Eureka Discovery Server
- OpenFeign Communication
- Resilience4j Circuit Breaker
- Swagger/OpenAPI Documentation
- Docker & Docker Compose
- GitHub Actions CI/CD
- AWS EC2 Deployment
- MySQL Database
- PDF Generation
- Email Notifications

---

## 🛠 Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security, JWT |
| Database | MySQL 8 |
| ORM | Spring Data JPA, Hibernate |
| Microservices | Eureka, API Gateway, OpenFeign |
| Documentation | Swagger OpenAPI |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |
| PDF | OpenPDF |
| Email | Spring Mail |

---

## 🏗 Architecture

```
                  Client
                     │
                     ▼
              API Gateway (8080)
                     │
     ┌───────────────┼───────────────┐
     ▼                               ▼
Booking Service                Provider Service
     │
     ▼
 MySQL Database
     │
     ▼
 HTML Email + PDF Ticket + QR Code

              Eureka Discovery Server
```

---

## 🔐 Authentication

- User Registration
- User Login
- BCrypt Password Encryption
- JWT Token Authentication
- Role-Based Authorization

---

## 📄 Booking Features

- Create Booking
- Get Booking
- Cancel Booking
- Booking History
- Email Confirmation
- PDF Ticket
- QR Code Verification

---

## 🐳 Docker

```bash
docker compose up --build
```

---

## 📚 Swagger

Booking Service

```
http://localhost:8081/swagger-ui/index.html
```

Eureka

```
http://localhost:8761
```

---

## 📸 Screenshots

### Swagger UI

(Add Screenshot)

### Booking Confirmation Email

(Add Screenshot)

### PDF Ticket

(Add Screenshot)

### Eureka Dashboard

(Add Screenshot)

---

## 👨‍💻 Author

**Barani K**

Java Backend Developer

LinkedIn:
(Add LinkedIn URL)

GitHub:
https://github.com/barani12052002
