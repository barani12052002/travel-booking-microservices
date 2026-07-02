# 🌍 Travel Booking Microservices

A production-style Travel Booking Microservices application built using Java 17, Spring Boot 3, Spring Security, JWT Authentication, Docker, and MySQL.

## 🚀 Features

- 🔐 JWT Authentication & Authorization
- 👤 Role-Based Access Control (ADMIN / USER)
- 📧 HTML Email Confirmation
- 📄 PDF Ticket Generation
- 📱 QR Code in PDF Ticket
- 🌐 API Gateway
- 🔍 Eureka Service Discovery
- 🔗 OpenFeign Client
- 🐳 Docker & Docker Compose
- 📚 Swagger API Documentation
- 🗄️ MySQL Database

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
