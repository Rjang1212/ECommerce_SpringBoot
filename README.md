
# 🛒 Kafka-Based E-Commerce Microservices

A production-grade backend system built from scratch using **Spring Boot**, **Apache Kafka**, **Docker**, and **PostgreSQL** — designed to simulate real-world e-commerce flow with inventory checks, payments, and async communication across services.

---

## 📌 Features

- 🔐 **JWT-based authentication** (user registration & login)
- 🛍️ **Order processing microservice** decoupled from core API
- 📦 **Inventory validation service** via Kafka events
- 💳 **Payment simulation** using Stripe test API
- ☁️ **Dockerized architecture** with `docker-compose`
- 🚀 **Deployed to AWS EC2** (t3.micro)

---

## 🧱 Microservice Architecture

```
[ User ] → root-app (auth, create order)
                |
         Kafka Event → order-service
                          |
               → inventory-service
                          |
               → payment-service
```

---

## 🔧 Tech Stack

- Java 17, Spring Boot
- Apache Kafka + Zookeeper
- PostgreSQL
- Docker, Docker Compose
- Stripe API (test mode)
- AWS EC2 (Ubuntu 22.04)

---

## 📂 Project Structure

```
.
├── root-app/             # API Gateway - handles auth & order initiation
├── order-service/        # Listens to order events and coordinates workflow
├── inventory-service/    # Confirms product availability
├── payment-service/      # Simulates payment if inventory is valid
├── docker-compose.yml    # One-command startup
└── README.md
```

---

## ⚙️ How It Works

1. `root-app` exposes `/api/order/buy` after user authentication
2. It publishes an `OrderCreated` event to Kafka
3. `order-service` consumes it and initiates inventory validation
4. If inventory exists → it triggers `payment-service`
5. Final logs represent order success or failure path

---

## 🚀 Running the Project Locally

### 🛠️ Prerequisites

- Java 17
- Docker & Docker Compose
- Maven

### ▶️ Start the system

```bash
docker-compose up --build
```

### 📬 Test APIs

1. Register user:
```http
POST http://localhost:8081/api/auth/register
JSON Payload:-
{
    "email":"test",
    "name":"test",
    "password":"test",
    "role":"admin"
}
```

2. Login
```http
http://localhost:8081/api/auth/login
JSON Payload:-
{
    "name":"test",
    "password":"test"
}

It returns JWT token.
```

3. Create Order:
```http
POST http://localhost:8081/api/order/buy
Headers: Authorization: Bearer <your_jwt_token>
Body:
{
  "userId": 1,
  "productId": 1
}
```

---

## 🧪 Kafka Topics Used

| Topic Name       | Purpose                       |
|------------------|-------------------------------|
| `order-events`   | Publishes when order is created |
| `inventory-check`| Result of stock validation     |
| `payment-events` | Payment success/failure logs   |

---

## 🌍 Live Deployment (Now offline)

The project was previously deployed on **AWS EC2** and tested in a live production environment.  
To deploy again:
- Launch Ubuntu EC2 instance (t3.micro)
- SSH in, install Docker, clone repo
- Run `docker-compose up --build -d`

---

## 📘 Learnings

- Kafka event-driven design in a real e-commerce context
- Dockerizing Spring Boot microservices
- EC2 deployment, debugging, and cost optimization
- Stripe integration for payment processing
- JWT-based security layer for user-based workflows

---

## ✨ Author

**Rohit Jangir**  
Backend Developer | Kafka Enthusiast  
📫 [rohitjangir343@gmail.com](mailto:rohitjangir343@gmail.com)  
🔗 [LinkedIn Profile](https://www.linkedin.com/in/rohit-jangir)  
🔗 [GitHub Profile](https://github.com/Rjang1212)

---

## ⭐️ If you liked this project, give it a star!
