# Software Design and Engineering Enhancement Narrative
**Author:** Keith Pottratz
**Course:** CS320 - Software Testing, Automation, and Quality Assurance
**Date:** January 2026
**Artifact:** Contact Management System

---

## 1. Artifact Description

### What is it?
The artifact is a **Contact Management System** written in Java. It is a software application that allows users to store, retrieve, update, and delete contact information (contact ID, first name, last name, phone number, and address). The system demonstrates core software engineering principles including data validation, object-oriented design, and comprehensive unit testing.

### When was it created?
The original artifact was created in **December 2024** as part of my CS320 coursework. The original implementation consisted of four files:
- `Contact.java` - A domain model class with field validation
- `ContactService.java` - A service class managing contacts using a HashMap
- `ContactTest.java` - Unit tests for the Contact class
- `ContactServiceTest.java` - Unit tests for the ContactService class

The **enhancements** were implemented in **January 2026** as part of my Computer Science Capstone portfolio project, transforming the basic implementation into a professionally architected, secure, and production-ready system.

---

## 2. Justification for Inclusion in ePortfolio

### Why did you select this item?
I selected the Contact Management System for my ePortfolio because it represents an ideal candidate for demonstrating growth in **Software Design and Engineering**. The original artifact was functional but lacked the architectural sophistication expected in professional software development. This created an opportunity to showcase my ability to:

1. **Recognize architectural limitations** in existing code
2. **Apply industry-standard design patterns** to improve code quality
3. **Implement security best practices** to protect against common vulnerabilities
4. **Refactor code** while maintaining full functionality (all tests passing)
5. **Document technical decisions** with clear rationale

### What specific components showcase your skills and abilities?

#### Design Patterns and Architecture
| Component | Skills Demonstrated |
|-----------|---------------------|
| **Repository Pattern** (`IContactRepository`, `InMemoryContactRepository`) | Abstraction, interface design, separation of concerns |
| **Service Layer with Dependency Injection** (`IContactService`, `ContactServiceImpl`) | SOLID principles, loose coupling, testability |
| **Builder Pattern** (`ContactBuilder`) | Fluent API design, object creation patterns |
| **Custom Exception Hierarchy** (5 exception classes) | Error handling strategy, API design |

#### Security Implementation
| Component | Skills Demonstrated |
|-----------|---------------------|
| **Thread Safety** (ConcurrentHashMap) | Concurrent programming, race condition prevention |
| **Input Sanitization** (ContactValidator) | XSS prevention, SQL injection prevention, defensive programming |
| **Resource Limits** (MAX_CONTACTS) | DoS prevention, resource management |
| **Audit Logging** (SLF4J/Logback) | Security monitoring, compliance, observability |

#### Testing and Quality
| Component | Skills Demonstrated |
|-----------|---------------------|
| **70 Unit Tests** | Comprehensive test coverage |
| **Mockito Integration** | Mock-based isolated testing, behavior verification |
| **Security Tests** | Thread safety verification, input validation testing |

### How was the artifact improved?

The artifact was transformed through **seven major enhancements**:

| # | Enhancement | Before | After |
|---|-------------|--------|-------|
| 1 | Exception Handling | Generic `IllegalArgumentException` | Custom hierarchy with 5 specific exception types |
| 2 | Data Access | Direct HashMap in service class | Repository pattern with interface abstraction |
| 3 | Service Architecture | Tightly coupled service | Dependency injection with interface-based design |
| 4 | Object Creation | Constructor only | Builder pattern with fluent interface |
| 7 | Logging | None | SLF4J/Logback with audit trail |
| 8 | Testing | Basic JUnit tests (14 tests) | Mockito mocking + comprehensive coverage (70 tests) |
| 9 | Security | No security considerations | Thread safety, input sanitization, DoS prevention |

**Quantitative Improvements:**
- Test count: 14 → 70 (400% increase)
- Source files: 4 → 14 (250% increase)
- Security vulnerabilities addressed: 9 critical issues mitigated
- Design patterns applied: 3 (Repository, Builder, Dependency Injection)

---

## 3. Course Outcomes Assessment

### Did you meet the course outcomes you planned to meet?

**Yes.** The enhancements successfully demonstrate progress toward all five course outcomes, with particular strength in Outcomes 3, 4, and 5.

### Course Outcome 1: Collaborative Environments
*"Employ strategies for building collaborative environments that enable diverse audiences to support organizational decision making in the field of computer science"*

**Met through:**
- **Interface-based design** allows different team members to work on implementations independently
- **Repository pattern** separates data access concerns, enabling parallel development
- **Dependency injection** allows components to be developed and tested in isolation
- **Clear package structure** (`exception/`, `repository/`, `service/`, `validation/`) organizes code for team navigation

**Evidence:**
```
src/main/java/com/example/contact/
├── repository/
│   ├── IContactRepository.java     ← Interface defines contract
│   └── InMemoryContactRepository.java  ← Team A implements this
├── service/
│   ├── IContactService.java        ← Interface defines contract
│   └── ContactServiceImpl.java     ← Team B implements this
```

### Course Outcome 2: Professional Communication
*"Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts"*

**Met through:**
- **Comprehensive JavaDoc documentation** on all public interfaces and methods
- **ENHANCEMENT_SUMMARY.md** providing technical documentation for developers
- **JAVADOC_GUIDE.md** documenting code comment requirements
- **Clear exception messages** that communicate specific validation failures
- **Audit logging** that creates readable operational records

**Evidence:**
```java
/**
 * Service interface for Contact business operations.
 * Defines the contract for contact management operations,
 * allowing for different service implementations.
 */
public interface IContactService {
    /**
     * Adds a new contact to the system.
     * @param contact the contact to add
     * @throws DuplicateContactException if a contact with the same ID already exists
     * @throws ContactValidationException if the contact is null or invalid
     */
    void addContact(Contact contact);
```

### Course Outcome 3: Design and Evaluation
*"Design and evaluate computing solutions that solve a given problem using algorithmic principles and computer science practices and standards appropriate to its solution, while managing the trade-offs involved in design choices"*

**Met through:**
- **Repository Pattern:** Chose abstraction over simplicity, trading initial development time for long-term flexibility and testability
- **Builder Pattern:** Chose readability over constructor simplicity, making object creation self-documenting
- **Custom Exceptions:** Chose specificity over generic exceptions, enabling precise error handling at the cost of additional classes
- **ConcurrentHashMap:** Chose thread safety over raw performance, accepting slight overhead for correctness

**Trade-off Analysis Example:**
| Decision | Trade-off | Rationale |
|----------|-----------|-----------|
| Interface-based design | More files/complexity | Enables mocking, future implementations |
| ConcurrentHashMap | ~10% slower than HashMap | Prevents data corruption in concurrent access |
| Input validation | Performance overhead | Prevents security vulnerabilities |

### Course Outcome 4: Industry Techniques and Tools
*"Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals"*

**Met through:**
- **Maven** for build management and dependency management
- **JUnit 5** for modern unit testing
- **Mockito** for mock-based isolated testing
- **SLF4J/Logback** for production-grade logging
- **Design Patterns** (Repository, Builder, Dependency Injection)
- **SOLID Principles** throughout the architecture

**Industry Tools Used:**
```xml
<!-- pom.xml dependencies -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.8.0</version>
</dependency>
```

### Course Outcome 5: Security Mindset
*"Develop a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws, and ensure privacy and enhanced security of data and resources"*

**Met through identification and mitigation of 9 security vulnerabilities:**

| Vulnerability | OWASP Category | Mitigation |
|--------------|----------------|------------|
| Thread Safety Issues | A04: Insecure Design | ConcurrentHashMap |
| No Input Sanitization | A03: Injection | ContactValidator with XSS/SQL patterns |
| No Authentication | A01: Broken Access Control | Foundation for future implementation |
| No Audit Logging | A09: Security Logging Failures | SLF4J audit logger |
| Information Disclosure | A05: Security Misconfiguration | Secure error messages |
| No Data Protection | A02: Cryptographic Failures | Foundation for encryption |
| Direct Object Reference | A01: Broken Access Control | Validation layer |
| Null Pointer Vulnerability | - | Null parameter validation |
| Denial of Service | A04: Insecure Design | MAX_CONTACTS = 10000 limit |

**Security Code Example:**
```java
// XSS Prevention Pattern
private static final Pattern XSS_PATTERN = Pattern.compile(
    ".*(<script|javascript:|onerror=|onclick=|onload=).*",
    Pattern.CASE_INSENSITIVE
);

// Audit Logging
auditLogger.warn("Security: XSS pattern detected in {} - input rejected", fieldName);
```

### Updates to Outcome-Coverage Plans
My original plan focused primarily on Outcomes 3 and 4. Through the enhancement process, I discovered that **security (Outcome 5)** deserved equal emphasis. The addition of the security enhancements (thread safety, input sanitization, resource limits) significantly strengthened the portfolio piece and better demonstrates professional-level thinking.

---

## 4. Reflection on the Enhancement Process

### What did you learn?

#### Technical Learning

1. **Design Patterns Have Real Value**

   Before this project, I understood design patterns theoretically. Implementing the Repository pattern showed me how abstraction enables testability—I could mock the repository and test service logic in complete isolation. The Builder pattern made my test code more readable and maintainable.

2. **Security Requires Proactive Thinking**

   The original code had no security considerations. Learning to think like an attacker—"What if someone passes `<script>` in the name field?"—fundamentally changed how I approach code. I now consider security at design time, not as an afterthought.

3. **Thread Safety is Non-Negotiable for Shared State**

   Understanding why HashMap can corrupt under concurrent access, and how ConcurrentHashMap prevents this, taught me that correct behavior under concurrency must be designed in, not patched later.

4. **Testing Strategy Matters**

   Writing 70 tests taught me that different types of tests serve different purposes:
   - Unit tests with mocks verify behavior in isolation
   - Integration tests verify components work together
   - Security tests verify protection against attacks

#### Professional Learning

1. **Documentation is Part of the Code**

   Writing JavaDoc forced me to think about my API from the user's perspective. If I couldn't clearly explain what a method does, that was a sign the method needed refactoring.

2. **Refactoring Requires Discipline**

   Making changes while keeping all tests passing required careful, incremental work. I learned to make one change, run tests, commit, then make the next change.

3. **Trade-offs Are Everywhere**

   Every design decision involves trade-offs. Choosing an interface adds a file but enables flexibility. Choosing validation adds overhead but prevents attacks. Professional engineering means making these trade-offs consciously.

### What challenges did you face?

#### Challenge 1: Maintaining Backward Compatibility
**Problem:** The original tests expected `IllegalArgumentException`, but my new code throws `ContactValidationException`.

**Solution:** I updated the tests to expect the new exception types while ensuring `ContactValidationException` extends `RuntimeException`, maintaining behavioral compatibility for code that catches generic exceptions.

**Learning:** API evolution requires careful consideration of existing consumers.

#### Challenge 2: Input Validation vs. Usability
**Problem:** My initial SQL injection pattern rejected names like "O'Brien" because it contained an apostrophe.

**Solution:** I refined the regex pattern to detect actual SQL injection (`' OR '1'='1`) while allowing legitimate apostrophes in names.

**Learning:** Security measures must be precise—overly aggressive validation creates usability problems.

#### Challenge 3: Test Isolation with Dependencies
**Problem:** Testing `ContactServiceImpl` was difficult because it depended on the repository.

**Solution:** Used Mockito to create mock repositories, allowing me to test service logic independently and verify specific method calls.

**Learning:** Dependency injection isn't just about flexibility—it's essential for testability.

#### Challenge 4: Concurrent Testing
**Problem:** How do you test that code is thread-safe?

**Solution:** Created tests that spawn multiple threads performing concurrent operations, then verify no exceptions occurred and all data is consistent.

**Learning:** Thread safety testing requires creative approaches since race conditions are non-deterministic.

---

## 5. Conclusion

The enhancement of the Contact Management System demonstrates substantial growth in software design and engineering competency. The transformation from a basic, tightly-coupled implementation to a professionally architected, secure, and well-tested system showcases:

- **Architectural thinking** through design patterns and SOLID principles
- **Security mindset** through vulnerability identification and mitigation
- **Professional practices** through comprehensive testing and documentation
- **Technical proficiency** with industry-standard tools and frameworks

The 70 passing tests, the secure input validation, the audit logging, and the clean separation of concerns all represent not just code changes, but a fundamental shift in how I approach software development—thinking about maintainability, security, and collaboration from the start.

This artifact demonstrates that I can take existing code, identify its limitations, and systematically improve it using industry best practices while maintaining full functionality. This is precisely the kind of work professional software engineers do daily.

---

## Appendix: Enhancement Summary

| Metric | Before | After |
|--------|--------|-------|
| Source Files | 4 | 14 |
| Test Files | 2 | 6 |
| Total Tests | 14 | 70 |
| Design Patterns | 0 | 3 |
| Security Features | 0 | 5 |
| Logging | None | Full audit trail |
| Thread Safety | No | Yes |
| Build Tool | None | Maven |

**All 70 tests pass. BUILD SUCCESS.**
