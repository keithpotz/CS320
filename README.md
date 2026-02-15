# Contact Management Service

A secure, enterprise-grade contact management system built with Java, demonstrating software engineering best practices including clean architecture, comprehensive testing, and security-first design principles.

## Project Overview

This project implements a robust contact management service that provides CRUD (Create, Read, Update, Delete) operations for contact records. The system is designed with a focus on maintainability, testability, and security, making it suitable for integration into larger enterprise applications.

**Course:** CS320 - Software Testing, Automation, and Quality Assurance
**Author:** Keith Pottratz
**Date:** January 2026

## Features

### Architecture
- **Layered Architecture** - Clear separation between service, repository, and domain layers
- **Dependency Injection** - Loose coupling between components for improved testability
- **Interface-Driven Design** - Programming to interfaces rather than implementations
- **Builder Pattern** - Fluent API for constructing Contact objects

### Security
- **Input Validation** - Comprehensive validation of all user inputs
- **XSS Prevention** - Detection and rejection of cross-site scripting patterns
- **SQL Injection Prevention** - Protection against SQL injection attacks
- **Control Character Filtering** - Sanitization of potentially dangerous control characters
- **Thread Safety** - ConcurrentHashMap-based storage for safe multi-threaded access
- **Resource Limits** - DoS prevention through configurable maximum contact limits
- **Audit Logging** - Security-relevant events are logged for compliance and monitoring

### Testing
- **Unit Tests** - Comprehensive coverage of domain objects and business logic
- **Integration Tests** - Service layer testing with repository integration
- **Mock Tests** - Isolated testing using Mockito for dependency mocking
- **Security Tests** - Dedicated test suite for security features and thread safety
- **Validation Tests** - Extensive input validation and edge case testing

## Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| Language | Java | 11 |
| Build Tool | Maven | 3.x |
| Testing Framework | JUnit | 5.10.0 |
| Mocking Framework | Mockito | 5.8.0 |
| Logging API | SLF4J | 2.0.9 |
| Logging Implementation | Logback | 1.4.14 |

## Project Structure

```
src/
├── main/java/com/example/contact/
│   ├── Contact.java                 # Domain entity with validation
│   ├── ContactBuilder.java          # Builder pattern implementation
│   ├── ContactService.java          # Legacy service (deprecated)
│   ├── exception/
│   │   ├── ContactException.java            # Base exception class
│   │   ├── ContactNotFoundException.java    # Entity not found
│   │   ├── ContactValidationException.java  # Validation failures
│   │   ├── DuplicateContactException.java   # Duplicate ID handling
│   │   └── ResourceLimitException.java      # Resource limit exceeded
│   ├── repository/
│   │   ├── IContactRepository.java          # Repository interface
│   │   └── InMemoryContactRepository.java   # Thread-safe implementation
│   ├── service/
│   │   ├── IContactService.java             # Service interface
│   │   └── ContactServiceImpl.java          # Business logic implementation
│   └── validation/
│       └── ContactValidator.java            # Security validation
├── main/resources/
│   └── logback.xml                  # Logging configuration
└── test/java/com/example/contact/
    ├── ContactTest.java             # Domain entity tests
    ├── ContactBuilderTest.java      # Builder pattern tests
    ├── ContactServiceTest.java      # Service integration tests
    ├── ContactServiceMockTest.java  # Mocked dependency tests
    ├── ContactValidatorTest.java    # Validation logic tests
    └── SecurityTest.java            # Security feature tests
```

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher

### Building the Project
```bash
mvn clean compile
```

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ContactServiceTest

# Run tests with coverage report
mvn test jacoco:report
```

### Packaging
```bash
mvn package
```

## API Reference

### Contact Entity
| Field | Type | Constraints |
|-------|------|-------------|
| contactId | String | Required, max 10 characters, immutable |
| firstName | String | Required, max 10 characters |
| lastName | String | Required, max 10 characters |
| phone | String | Required, exactly 10 digits |
| address | String | Required, max 30 characters |

### Service Operations
| Method | Description |
|--------|-------------|
| `addContact(Contact)` | Adds a new contact to the system |
| `getContact(String)` | Retrieves a contact by ID |
| `getAllContacts()` | Returns all contacts |
| `updateContact(...)` | Updates specified fields of a contact |
| `deleteContact(String)` | Removes a contact from the system |

## Design Patterns

- **Repository Pattern** - Abstracts data access logic from business logic
- **Service Layer Pattern** - Encapsulates business logic and transaction management
- **Builder Pattern** - Provides flexible object construction with validation
- **Dependency Injection** - Enables loose coupling and testability

## Security Considerations

This application implements multiple security measures:

1. **Input Validation** - All inputs are validated before processing
2. **Pattern Detection** - Regular expressions detect XSS and SQL injection attempts
3. **Immutable IDs** - Contact IDs cannot be modified after creation
4. **Audit Trail** - All security-relevant events are logged
5. **Resource Limits** - Maximum of 10,000 contacts to prevent DoS attacks
6. **Thread Safety** - Concurrent access is safely handled

## Testing Strategy

The project employs a comprehensive testing approach:

- **100% coverage goal** for critical business logic
- **Boundary testing** for all field constraints
- **Exception testing** for error handling paths
- **Concurrency testing** for thread safety verification
- **Security testing** for vulnerability prevention

## License

This project was developed as part of academic coursework at Southern New Hampshire University.

## Acknowledgments

- Southern New Hampshire University - CS320 Course
- JUnit 5 Documentation
- Mockito Framework Documentation
