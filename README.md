# ✈️ Travel Booking Microservices

A microservices-based Travel Booking System built using **Java 17**, **Spring Boot**, and **Spring Cloud**. The project demonstrates service discovery, centralized configuration, API Gateway, inter-service communication using OpenFeign, booking management, and MySQL persistence.

---

## 🚀 Features

- ✅ Booking Creation
- ✅ Booking Retrieval
- ✅ Booking Cancellation
- ✅ Timeslot Availability
- ✅ Price Calculation
- ✅ OpenFeign Inter-Service Communication
- ✅ Eureka Service Discovery
- ✅ Spring Cloud Config Server
- ✅ Spring Cloud API Gateway
- ✅ MySQL Integration
- ✅ Spring Data JPA & Hibernate
- ✅ Request Validation
- ✅ Global Exception Handling
- ✅ Swagger / OpenAPI Documentation
- ✅ Structured Logging (SLF4J)

---

## 🏗️ Architecture

```
                   Client
                      │
                      ▼
               API Gateway
                      │
        ┌─────────────┴─────────────┐
        │                           │
        ▼                           ▼
 Booking Service            Provider Service
        │
        ▼
      MySQL

 Eureka Server
 Config Server
```

---

## 🛠️ Tech Stack

| Category | Technologies |
|----------|--------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Microservices | Spring Cloud |
| Service Discovery | Eureka Server |
| Configuration | Spring Cloud Config |
| API Gateway | Spring Cloud Gateway |
| Communication | OpenFeign |
| Database | MySQL |
| ORM | Spring Data JPA, Hibernate |
| Documentation | Swagger / OpenAPI |
| Build Tool | Maven |
| Version Control | Git & GitHub |
| Testing Tool | Postman |

---

## 📂 Project Structure

```
travel-booking-microservices
│
├── discovery-server
├── config-server
├── api-gateway
├── booking-service
└── provider-service
```

---

## 📌 Booking Flow

```
Client
   │
   ▼
Booking Service
   │
   ├── Validate Request
   │
   ├── Fetch Price (Provider Service)
   │
   ├── Create Booking (Provider Service)
   │
   ├── Save Booking (MySQL)
   │
   ▼
Response
```

---

## 📡 REST APIs

### Booking Service

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /booking | Create Booking |
| GET | /booking/{bookingReference} | Get Booking |
| PUT | /booking/cancel/{bookingReference} | Cancel Booking |

### Provider Service

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /provider/timeslots | Get Available Timeslots |
| POST | /provider/price | Calculate Price |
| POST | /provider/book | Create Provider Booking |

---

## 📷 Screenshots

### Swagger UI

_Add screenshot_

### Eureka Dashboard

_Add screenshot_

### Booking API Response

_Add screenshot_

### MySQL Database

_Add screenshot_

---

## ▶️ How to Run

### Start Services

1. Config Server
2. Eureka Discovery Server
3. API Gateway
4. Provider Service
5. Booking Service

---

## 💻 Clone Repository

```bash
git clone https://github.com/barani12052002/travel-booking-microservices.git
```

---

## 📚 Key Concepts Implemented

- Microservices Architecture
- Layered Architecture
- RESTful APIs
- DTO Mapping
- Spring Data JPA
- Service Discovery
- API Gateway
- Centralized Configuration
- OpenFeign
- Validation
- Exception Handling
- Logging
- Booking Lifecycle Management

---

## 🚀 Future Enhancements

- Docker
- Docker Compose
- Spring Security + JWT
- Resilience4j Circuit Breaker
- Unit Testing (JUnit & Mockito)
- CI/CD using GitHub Actions
- AWS Deployment

---

## 👨‍💻 Author

**Barani K**

📧 baraniec2002@gmail.com

🔗 LinkedIn: https://linkedin.com/in/k-barani

🔗 GitHub: https://github.com/barani12052002
