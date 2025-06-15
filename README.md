# BookStore Microservices

**BookStore** is a microservices-based application for managing an online bookstore. It provides a complete set of features including user authentication, book catalog management, shopping cart, order processing, shipping, and stock management. The system uses event-driven architecture, CQRS, and SAGA patterns to ensure scalability, fault tolerance, and clean separation of concerns.

---

## Features

- **Book Management**: Add, update, and remove books from the catalog.
- **Shopping Cart**: Allows users to add and remove items from their cart in real-time.
- **Order Processing**: Create, track, and manage user orders.
- **Shipping Service**: Handles shipping orders and delivery information.
- **User Authentication & JWT**: Secure login and JWT token generation and validation.
- **CQRS & Event Sourcing**: Separation of read and write operations with event storage.
- **SAGA Orchestration**: Distributed coordination across services to ensure transactional consistency.
- **Kafka Integration**: Asynchronous communication between services using Kafka.
- **API Gateway**: Centralized entry point with routing and security.
- **Service Discovery**: Dynamic service registration and discovery using Eureka.
- **Centralized Configuration**: All services load configuration from a central config server.

---

## Technologies Used

### Backend

- **Java & Spring Boot** – Core framework for microservice development.
- **Spring Cloud** – Including Config Server, Eureka, Gateway, and SAGA support.
- **Apache Kafka** – Event streaming and asynchronous communication.
- **Axon Framework** – For implementing CQRS and SAGA patterns.
- **MySQL** – Relational database for persistent storage.
- **Docker** – Containerization and service orchestration.
- **OpenFeign** – Declarative REST client for service-to-service communication.
