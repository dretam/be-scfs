# Backend Dashboard TMG - Comprehensive Documentation

## Project Overview

Backend Dashboard TMG is a Spring Boot application built with hexagonal architecture (also known as ports and adapters architecture). It serves as a starter kit for corporate system development at Bank Mega, featuring robust security, multiple database support, and cloud integration capabilities.

### Key Features
- Hexagonal Architecture (Ports and Adapters)
- JWT-based Authentication & Authorization
- Multi-database Support (MySQL, PostgreSQL, MariaDB)
- AWS S3 Integration
- Scheduled Task Management with db-scheduler
- Resilience4j for Circuit Breaker & Rate Limiting
- OpenAPI/Swagger Documentation
- Flyway Database Migration
- Docker & Docker Compose Support

### Technology Stack
- **Java 21**: Modern Java runtime
- **Spring Boot 3.5.9**: Application framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database access layer
- **Flyway**: Database migration management
- **AWS SDK**: Cloud storage integration
- **db-scheduler**: Task scheduling
- **Resilience4j**: Fault tolerance
- **Lombok**: Code reduction
- **Maven**: Build automation

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── bank_mega/
│   │       └── corsys/
│   │           ├── application/          # Application layer (use cases)
│   │           │   ├── accesslog/        # Access log management
│   │           │   ├── assembler/        # DTO conversion utilities
│   │           │   ├── auth/             # Authentication use cases
│   │           │   ├── common/           # Common application utilities
│   │           │   ├── role/             # Role management use cases
│   │           │   └── user/             # User management use cases
│   │           ├── domain/               # Domain layer (business logic)
│   │           │   ├── exception/        # Custom exceptions
│   │           │   ├── model/            # Domain entities/models
│   │           │   └── repository/       # Domain repositories
│   │           ├── infrastructure/       # Infrastructure layer
│   │           │   ├── adapter/          # Input/output adapters
│   │           │   │   ├── in/           # Input adapters (controllers)
│   │           │   │   └── out/          # Output adapters (repositories, external services)
│   │           │   ├── config/           # Configuration classes
│   │           │   └── util/             # Utility classes
│   │           └── HexagonalApplication.java  # Main application entry point
│   └── resources/
│       ├── db/
│       │   └── migration/                # Database migration scripts
│       │       ├── mysql/                # MySQL-specific migrations
│       │       ├── postgresql/           # PostgreSQL-specific migrations
│       │       └── mariadb/              # MariaDB-specific migrations
│       └── application.yaml              # Application configuration
└── test/
    └── java/
        └── bank_mega/
            └── corsys/
                └── HexagonalApplicationTests.java  # Main application tests
```

## Architecture Layers

### 1. Application Layer (`application/`)
Contains use cases and business rules orchestration. Organized by functional domains:

- **accesslog/**: Manages access logging functionality
- **assembler/**: Handles DTO conversions between layers
- **auth/**: Authentication-related use cases
- **common/**: Shared application utilities
- **role/**: Role management use cases
- **user/**: User management use cases

Each domain follows the same structure:
- `command/`: Command objects for input validation
- `dto/`: Data Transfer Objects for API contracts
- `usecase/`: Business logic implementations

### 2. Domain Layer (`domain/`)
Contains pure business logic without external dependencies:

- **exception/**: Custom domain exceptions
- **model/**: Domain entities and value objects
- **repository/**: Domain repository interfaces (ports)

### 3. Infrastructure Layer (`infrastructure/`)
Handles external concerns and technical implementations:

- **adapter/in/**: Input adapters (REST controllers, message listeners)
- **adapter/out/**: Output adapters (database repositories, external service clients)
- **config/**: Configuration classes and properties
- **util/**: General utility classes

## Configuration

### Environment Variables
The application uses extensive environment variable configuration:

#### Server Configuration
- `SERVER_PORT`: HTTP server port (default: 8080)
- `SPRING_APPLICATION_NAME`: Application name identifier
- `SPRING_PROFILES_ACTIVE`: Active Spring profile

#### Database Configuration
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_DATASOURCE_DRIVERCLASSNAME`: JDBC driver class
- `SPRING_FLYWAY_LOCATIONS`: Migration script locations
- `SPRING_JPA_PROPERTIES_HIBERNATE_*`: JPA/Hibernate settings

#### JWT Configuration
- `JWT_SECRET`: Secret key for token signing (min 256 bits)
- `JWT_ALGORITHM`: Signing algorithm (default: HmacSHA256)
- `JWT_ISSUER`: Token issuer identifier
- `JWT_AUDIENCE`: Token audience identifier
- `JWT_EXPIRES_SECONDS`: Token expiration time

#### AWS S3 Configuration
- `AWS_S3_ACCESS_KEY_ID`: AWS access key
- `AWS_S3_SECRET_ACCESS_KEY`: AWS secret key
- `AWS_S3_REGION`: AWS region
- `AWS_S3_BUCKET_NAME`: S3 bucket name
- `AWS_S3_ENDPOINT`: Custom endpoint (for services like MinIO)

#### Mail Configuration
- `SPRING_MAIL_*`: SMTP server settings for email functionality

### Database Migration
The application uses Flyway for database migration with support for multiple databases:
- MySQL migrations in `db/migration/mysql/`
- PostgreSQL migrations in `db/migration/postgresql/`
- MariaDB migrations in `db/migration/mariadb/`

## Security Features

### JWT Authentication
- Token-based authentication using JWT
- Configurable algorithms and expiration times
- Secure token validation and refresh mechanisms

### Spring Security
- Role-based access control
- OAuth2 resource server configuration
- Secure endpoint protection

### Rate Limiting
- Resilience4j-based rate limiting
- Configurable limits (default: 50 requests per second)

## External Integrations

### AWS S3
- File upload/download capabilities
- Configurable regions and endpoints
- Support for custom S3-compatible services (MinIO)

### Scheduled Tasks
- db-scheduler for background job processing
- Web UI for monitoring scheduled tasks
- Task persistence and logging

### Monitoring & Observability
- Spring Boot Actuator endpoints
- Health check monitoring
- Request/response logging

## API Documentation
- Swagger/OpenAPI documentation available at `/swagger-ui.html`
- Multiple API groups supported
- Syntax highlighting and filtering options

## Deployment Options

### Local Development
```bash
mvn clean install
mvn spring-boot:run
```

### Docker Deployment
```bash
docker-compose up --build -d
```

### Production Deployment
- Package as executable JAR
- Configure environment variables
- Deploy with container orchestration or traditional deployment

## Development Guidelines

### Hexagonal Architecture Principles
1. Domain layer contains pure business logic without external dependencies
2. Application layer orchestrates business use cases
3. Infrastructure layer handles external concerns (databases, APIs, etc.)
4. Dependencies flow inward (infrastructure depends on application, application depends on domain)

### Code Organization
- Each business domain has its own package structure
- Use Cases follow Command-Query Responsibility Segregation (CQRS)
- DTOs handle API contracts separately from domain models
- Exception handling is centralized and consistent

### Testing Strategy
- Unit tests for domain logic
- Integration tests for infrastructure components
- API contract testing for external interfaces

## Best Practices Implemented

1. **Separation of Concerns**: Clear boundaries between architecture layers
2. **Dependency Injection**: Managed by Spring Framework
3. **Configuration Management**: Externalized via environment variables
4. **Security First**: Built-in authentication, authorization, and rate limiting
5. **Observability**: Logging, monitoring, and health checks
6. **Database Migrations**: Automated schema management
7. **Cloud Native**: Container-ready with externalized configuration
8. **Documentation**: API documentation with OpenAPI/Swagger