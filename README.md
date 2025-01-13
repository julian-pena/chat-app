<div align="center">
  <img src="https://github.com/user-attachments/assets/b589e78d-771b-4928-bd23-a448d625bd7f" alt="Chat App Tech Stack - Spring Boot, MongoDB" width="200" height="200"/>
</div>

# Real-Time Chat Application

A real-time chat application built with Spring Boot that enables instant messaging between users with a clean, modern interface and administrative capabilities.

## Demo

🔗 [Try the live demo](https://christian-fara-myprojects-julianp-58b37b45.koyeb.app/auth/login)

## Features

- Real-time messaging using WebSocket technology
- User authentication and authorization with Spring Security
- Persistent message storage using MongoDB
- Active users list display
- Admin dashboard with message history and user management
- Join/Leave notifications
- Clean and responsive user interface
- Message history tracking

## Technology Stack

- **Backend:**
  - Spring Boot
  - Spring Security
  - Spring WebSocket
  - MongoDB for data persistence
  - Thymeleaf template engine

- **Frontend:**
  - Thymeleaf for server-side rendering
  - WebSocket for real-time communication
  - Responsive design

## Getting Started

### Prerequisites

- Java 17 or higher
- MongoDB
- Maven

### MongoDB Setup

1. Install MongoDB Community Edition on your machine
2. Start MongoDB service
3. Configure MongoDB connection using one of these options:

#### Option 1: Using Environment Variables
Keep the default `application.properties` configuration:
```properties
spring.data.mongodb.uri=${MONGO_CONNECTION_STRING}
spring.data.mongodb.database=${MONGO_DATABASE}
```

Set your environment variables:
```bash
# Windows
set MONGO_CONNECTION_STRING=mongodb://localhost:27017
set MONGO_DATABASE=chatapp

# Unix/Mac
export MONGO_CONNECTION_STRING=mongodb://localhost:27017
export MONGO_DATABASE=chatapp
```

#### Option 2: Direct Configuration
Update `application.properties` with your values:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017
spring.data.mongodb.database=chatapp
```

### Installation

1. Clone the repository
```bash
git clone https://github.com/julian-pena/chat-app
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## Features in Detail

### User Features
- Real-time messaging
- Active users list
- Join/Leave notifications
- Message history
- User authentication

### Admin Features
- Message history viewing
- User management
- Delete all messages
- Message refresh capability

## Security

The application implements security measures using Spring Security, including:
- User authentication
- Session management
- Secure WebSocket connections

## Contributing

Feel free to fork the repository and submit pull requests.


## Contact

Julian David Peña Rojas - julianpr8@hotmail.com

Project Link: [https://github.com/julian-pena/chat-app](https://github.com/julian-pena/chat-app)

