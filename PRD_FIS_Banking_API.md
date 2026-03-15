# PRD: FIS Banking API — Workshop Codebase

**Version:** 1.0 | **Date:** March 2026
**Stack:** Java 17 | Spring Boot 3.x | Maven | H2 Database
**IDE:** VS Code with GitHub Copilot + Copilot Chat

---

## 1. Executive Summary

This PRD defines the requirements for a purpose-built Java Spring Boot codebase that serves as the running project for the 4-day GitHub Copilot workshop at FIS. The codebase must be simple enough for participants to understand in minutes, yet rich enough to support exercises across all 8 sessions: coding, testing, requirements analysis, code review, CLI automation, agentic development, and governance.

The project simulates a **Simple Banking API** with three core features: Account Lookup, Fund Transfer, and Transaction History. It is intentionally designed with specific characteristics that make it ideal for AI-assisted development exercises — including areas of deliberate complexity, incomplete documentation, and code that benefits from refactoring.

### Tool Recommendation

This PRD is designed to be handed directly to either **Claude Code** or **OpenAI Codex**.

| Criteria | Claude Code | Codex |
|----------|-------------|-------|
| **Best for** | Full project scaffold with tests, docs, and config in one pass | Iterative feature-by-feature development with PR workflow |
| **Strengths** | Strong at generating complete, consistent project structures; excellent with Spring Boot conventions | Good at isolated feature generation; integrates with GitHub Issues/PRs natively |
| **Approach** | Feed entire PRD as a single prompt; get complete project | Break PRD into tasks/issues; assign to Codex one at a time |
| **Recommendation** | Use for initial full scaffold generation | Use for iterative additions and refinements post-scaffold |

**Suggested workflow:** Use Claude Code to generate the complete project scaffold from this PRD, then use Codex for incremental refinements, feature additions, or to create GitHub Issues that map to workshop exercises.

---

## 2. Project Overview

### 2.1 Tech Stack

| Component | Choice & Rationale |
|-----------|-------------------|
| Language | Java 17 (LTS, widely used at FIS, good Copilot support) |
| Framework | Spring Boot 3.2+ (industry standard, rich ecosystem, excellent for demos) |
| Build Tool | Maven (standard at FIS; pom.xml provides good context for Copilot) |
| Database | H2 in-memory (zero setup, auto-creates on startup, perfect for workshops) |
| Testing | JUnit 5 + Mockito + MockMvc (standard stack for Spring Boot testing) |
| API Style | REST with JSON (simple, universally understood) |
| IDE | VS Code with Java Extension Pack + GitHub Copilot + Copilot Chat |
| Java Runtime | OpenJDK 17 or Amazon Corretto 17 |

### 2.2 Project Structure

The project must follow standard Spring Boot Maven conventions. Every file should be in a predictable location so Copilot can leverage repository context effectively.

```
fis-banking-api/
├── pom.xml
├── README.md
├── docs/
│   ├── REQUIREMENTS.md          # Raw business requirements (Session 3 input)
│   ├── API_SPEC.md              # API documentation
│   └── ARCHITECTURE.md          # Architecture overview
├── src/
│   ├── main/
│   │   ├── java/com/fis/banking/
│   │   │   ├── BankingApiApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── AccountController.java
│   │   │   │   ├── TransactionController.java
│   │   │   │   └── TransferController.java
│   │   │   ├── service/
│   │   │   │   ├── AccountService.java
│   │   │   │   ├── TransactionService.java
│   │   │   │   ├── TransferService.java
│   │   │   │   ├── FraudCheckService.java
│   │   │   │   └── AuditService.java
│   │   │   ├── repository/
│   │   │   │   ├── AccountRepository.java
│   │   │   │   └── TransactionRepository.java
│   │   │   ├── model/
│   │   │   │   ├── Account.java
│   │   │   │   ├── Transaction.java
│   │   │   │   └── TransactionStatus.java
│   │   │   ├── dto/
│   │   │   │   ├── AccountDTO.java
│   │   │   │   ├── TransferRequest.java
│   │   │   │   ├── TransferResponse.java
│   │   │   │   ├── TransactionDTO.java
│   │   │   │   └── ErrorResponse.java
│   │   │   ├── exception/
│   │   │   │   ├── InsufficientFundsException.java
│   │   │   │   ├── AccountNotFoundException.java
│   │   │   │   ├── InvalidTransferException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── config/
│   │   │       └── DataInitializer.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
│       └── java/com/fis/banking/
│           ├── controller/
│           │   └── TransferControllerTest.java
│           ├── service/
│           │   ├── TransferServiceTest.java    # Intentionally incomplete
│           │   └── AccountServiceTest.java     # Intentionally incomplete
│           └── BankingApiApplicationTests.java
└── scripts/
    ├── build.sh                     # Build script (Session 5)
    └── run-tests.sh                 # Test runner (Session 5)
```

---

## 3. Domain Model

### 3.1 Account Entity

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| id | Long | @Id, @GeneratedValue | Auto-generated PK |
| accountNumber | String | @Column(unique=true) | Format: FIS-XXXX-XXXX |
| holderName | String | @NotBlank | Customer full name |
| balance | BigDecimal | precision=15, scale=2 | Use BigDecimal, never double |
| currency | String | Default: "USD" | ISO 4217 code |
| status | String | ACTIVE, FROZEN, CLOSED | Only ACTIVE can transact |
| createdAt | LocalDateTime | Auto-set on creation | Audit field |

### 3.2 Transaction Entity

| Field | Type | Constraints | Notes |
|-------|------|-------------|-------|
| id | Long | @Id, @GeneratedValue | Auto-generated PK |
| referenceId | String | UUID, unique | External tracking ID |
| fromAccountId | Long | @NotNull | Source account FK |
| toAccountId | Long | @NotNull | Destination account FK |
| amount | BigDecimal | precision=15, scale=2 | Must be > 0 |
| currency | String | Default: "USD" | ISO 4217 code |
| description | String | Optional, max 255 | Transfer memo |
| status | TransactionStatus | Enum | PENDING, COMPLETED, FAILED, REVERSED |
| createdAt | LocalDateTime | Auto-set | When initiated |
| completedAt | LocalDateTime | Nullable | When settled |

### 3.3 Seed Data

The application must auto-seed the following test accounts on startup via `data.sql` or `DataInitializer.java`:

| Account Number | Holder | Balance | Status |
|---------------|--------|---------|--------|
| FIS-1001-0001 | Alice Johnson | $25,000.00 | ACTIVE |
| FIS-1001-0002 | Bob Smith | $15,500.00 | ACTIVE |
| FIS-1001-0003 | Charlie Davis | $42,000.00 | ACTIVE |
| FIS-1001-0004 | Diana Lee | $8,750.00 | ACTIVE |
| FIS-1001-0005 | Frozen Corp | $100,000.00 | FROZEN |
| FIS-1001-0006 | Closed Account LLC | $0.00 | CLOSED |

Also seed **10–15 historical transactions** across these accounts so Transaction History queries return meaningful data from the start.

---

## 4. API Endpoints

### 4.1 Account Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/accounts` | List all accounts (return AccountDTO list) |
| GET | `/api/accounts/{id}` | Get account by ID (return AccountDTO or 404) |
| GET | `/api/accounts/search?holder={name}` | Search accounts by holder name (partial match) |

### 4.2 Transfer Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/transfers` | Initiate a fund transfer (accepts TransferRequest, returns TransferResponse) |

**TransferRequest body:**

```json
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 500.00,
  "currency": "USD",
  "description": "Rent payment"
}
```

**TransferResponse body:**

```json
{
  "referenceId": "a1b2c3d4-...",
  "status": "COMPLETED",
  "fromAccount": "FIS-1001-0001",
  "toAccount": "FIS-1001-0002",
  "amount": 500.00,
  "timestamp": "2026-03-15T10:30:00"
}
```

### 4.3 Transaction History Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/transactions/{id}` | Get transaction by ID |
| GET | `/api/transactions/account/{accountId}` | Get all transactions for an account |
| GET | `/api/transactions/account/{accountId}?from=&to=` | Filter transactions by date range |

### 4.4 Error Response Format

All errors must return a consistent `ErrorResponse` DTO:

```json
{
  "timestamp": "2026-03-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Insufficient funds in account FIS-1001-0001",
  "path": "/api/transfers"
}
```

---

## 5. Business Rules

### 5.1 Transfer Validation Rules

The transfer service must enforce the following validation rules in order. Each failed rule should return a clear, specific error message:

| # | Rule | Limit | Error Message |
|---|------|-------|--------------|
| 1 | Amount must be positive | > $0.00 | Transfer amount must be greater than zero |
| 2 | Minimum transfer amount | >= $1.00 | Minimum transfer amount is $1.00 |
| 3 | Maximum single transfer | <= $50,000 | Maximum single transfer is $50,000.00 |
| 4 | Source account must exist | - | Source account not found |
| 5 | Destination account must exist | - | Destination account not found |
| 6 | Cannot self-transfer | from != to | Cannot transfer to the same account |
| 7 | Source must be ACTIVE | - | Source account is not active |
| 8 | Destination must be ACTIVE | - | Destination account is not active |
| 9 | Sufficient balance | balance >= amount | Insufficient funds in source account |

### 5.2 Fraud Check (Stub)

Implement `FraudCheckService` as a simple stub that participants will enhance during the workshop. The initial implementation should:

- Flag any transfer above $10,000 as requiring review (log a warning, but still allow it)
- Flag any transfer where the description contains suspicious keywords (e.g., "urgent", "wire immediately")
- Return a `FraudCheckResult` with fields: `flagged` (boolean), `reason` (String), `riskScore` (int 0–100)
- Default risk score: 0 for normal transfers, 50 for high-amount, 75 for suspicious keywords

> *This stub is intentionally simple so participants can enhance it during Sessions 2, 4, and 7.*

### 5.3 Audit Logging

Implement `AuditService` that logs every transfer attempt with:

- Timestamp, source account, destination account, amount
- Result (SUCCESS, FAILED, FLAGGED)
- Reason for failure if applicable
- Use SLF4J logging (`logger.info` for success, `logger.warn` for flagged, `logger.error` for failures)

---

## 6. Intentional Design Choices for Workshop Exercises

> **This section is critical.** The codebase is not meant to be perfect — it is meant to be a realistic teaching tool. Specific areas are intentionally left incomplete, complex, or undocumented so participants can improve them using Copilot during the workshop.

### 6.1 For Session 1 (Explain & Understand)

- `TransferService.executeTransfer()` should be a **moderately complex method (~40–60 lines)** that handles validation, fraud check, debit, credit, and audit in a single method. This gives participants something substantial to ask Copilot to explain.
- Include some **non-obvious business logic** (e.g., balance check happens after fraud check, not before) so Copilot's explanation reveals important ordering.
- Add a few **comments that are vague or outdated** (e.g., `// TODO: fix this later`) to show realistic code.

### 6.2 For Session 2 (Code Generation)

- Leave `AccountService` **partially implemented**: only `getAccountById()` is complete. Participants will generate the remaining methods (search, list) using Copilot prompts.
- DTO classes should have fields but **no validation annotations** initially. Participants will add them.
- The controller layer should be complete for transfers but **missing endpoints for account search**.

### 6.3 For Session 3 (Requirements)

- Include `docs/REQUIREMENTS.md` with **raw, unstructured business requirements** that participants will break down using Copilot. Example: a few paragraphs describing the fund transfer feature in business language, without formal user stories.
- The requirements should contain **ambiguities** that Copilot can help identify (e.g., "customers can transfer funds" doesn't specify limits, currencies, or error handling).

### 6.4 For Session 4 (Testing)

- `TransferServiceTest.java` should have **3–4 basic test cases** but be clearly incomplete. Missing: boundary tests, negative tests, parameterized tests, mock setup for fraud service.
- `AccountServiceTest.java` should be a **near-empty test class** with just the `@SpringBootTest` annotation and one placeholder test.
- **No integration tests** exist initially. Participants will generate them.

### 6.5 For Session 5 (CLI & DevOps)

- Include `scripts/build.sh` as a **basic shell script** that just runs `mvn clean install`. Participants will enhance it using Copilot CLI.
- **No CI/CD configuration** exists. Participants will generate GitHub Actions workflows.
- **No Dockerfile** exists. Participants will generate one.

### 6.6 For Session 6 (Code Review & Refactoring)

- `TransferService.executeTransfer()` should contain identifiable **code smells**: long method, mixed abstraction levels, hardcoded values (the $50,000 limit as a magic number), and some duplicated validation logic.
- Javadoc should be **minimal or missing** on most methods. Only 2–3 methods should have documentation.
- `README.md` should exist but be a **bare skeleton** (project name, one-line description, nothing else). Participants will generate a comprehensive one.

### 6.7 For Session 7 (Agentic Development)

- The project should **not** have rate limiting, circuit breaker patterns, or retry logic. These are features participants will ask Copilot to add in agentic workflow exercises.
- **No caching layer** exists. This is another feature for agentic generation.

### 6.8 For Session 8 (Governance & Security)

- Include at least one **intentional security issue** for participants to find: a hardcoded API key or password in `application.properties` (clearly labeled as a workshop example, e.g., `fraud.service.api-key=WORKSHOP-DEMO-KEY-12345`).
- Include one **dependency in pom.xml that has a known vulnerability** (for dependency scanning exercises). Use an older version of a common library.
- **No `.gitignore` entries** for sensitive files initially. Participants will add them.

---

## 7. Non-Functional Requirements

### 7.1 Zero-Setup Startup

**This is the most critical non-functional requirement.** Participants must be able to:

1. Clone the repository
2. Run `mvn spring-boot:run` (or the VS Code run button)
3. See the API running at `http://localhost:8080` within 30 seconds
4. Hit any endpoint immediately with curl or a REST client

No Docker, no external databases, no environment variables required. H2 in-memory database auto-creates and seeds on startup.

### 7.2 Application Properties

```properties
# Server
server.port=8080

# H2 Database (in-memory, auto-create)
spring.datasource.url=jdbc:h2:mem:fisbank
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true

# Logging
logging.level.com.fis.banking=DEBUG

# Workshop Demo - Intentional security issue for Session 8
fraud.service.api-key=WORKSHOP-DEMO-KEY-12345
```

### 7.3 Maven Dependencies

The `pom.xml` should include:

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `h2` (runtime scope)
- `spring-boot-starter-test` (test scope, includes JUnit 5 + Mockito)
- `lombok` (optional, but useful for reducing boilerplate in demos)
- `spring-boot-devtools` (for hot reload during workshop)
- One older dependency for security scanning exercise (e.g., an older version of `jackson-databind` or `commons-text`)

---

## 8. Session-to-Code Mapping

This mapping ensures the codebase has the right entry points for every session's exercises:

| Session | Focus | Primary Files | Participant Action |
|---------|-------|--------------|-------------------|
| 1 | Explain & Understand | TransferService.java, FraudCheckService.java | Ask Copilot to explain, identify edge cases |
| 2 | Code Generation | AccountService.java (incomplete), DTOs (no validation) | Generate methods, add validation, build endpoints |
| 3 | Requirements | docs/REQUIREMENTS.md | Break down requirements, generate user stories |
| 4 | Testing | TransferServiceTest.java (incomplete), AccountServiceTest.java (stub) | Generate tests, mocks, parameterized tests |
| 5 | CLI & DevOps | scripts/build.sh, (no CI config) | Generate CI pipeline, Dockerfile, enhanced scripts |
| 6 | Code Review | TransferService.java (smells), README.md (bare) | Review, refactor, generate documentation |
| 7 | Agentic Dev | Entire project | Add rate limiting, circuit breaker, caching via spec |
| 8 | Governance | application.properties (secret), pom.xml (vuln dep) | Security audit, governance policy, .gitignore |

---

## 9. Acceptance Criteria

### 9.1 Must Pass

1. `mvn clean install` completes successfully with zero errors
2. `mvn spring-boot:run` starts the application on port 8080 within 30 seconds
3. All 3 account endpoints return correct data from seeded accounts
4. `POST /api/transfers` successfully transfers funds between two active accounts
5. `POST /api/transfers` returns appropriate 400/404 errors for each validation rule
6. `GET /api/transactions/account/{id}` returns seeded historical transactions
7. H2 console is accessible at `http://localhost:8080/h2-console`
8. All existing tests pass (even though test coverage is intentionally low)
9. The project opens cleanly in VS Code with Java Extension Pack
10. Copilot can provide relevant suggestions when editing any file in the project

### 9.2 Code Quality Checks

1. No compilation warnings
2. Standard Spring Boot project structure with clear package organization
3. Consistent Java naming conventions throughout
4. `README.md` exists (bare skeleton, not comprehensive — by design)
5. Intentional code smells are present only in designated files (TransferService)
6. Clean code exists in other files to show the contrast

---

## 10. Prompt for Code Generation Tool

### For Claude Code

Feed the entire PRD and use this prompt:

```
Build a complete Java Spring Boot project called fis-banking-api based on the
attached PRD. This is a workshop codebase for teaching GitHub Copilot, so it must:

1. Start with zero setup (H2 in-memory DB, auto-seed data)
2. Have clear, standard project structure
3. Include intentionally incomplete areas (see PRD Section 6):
   - TransferService with a long, smell-heavy executeTransfer() method
   - AccountService with only getAccountById() implemented
   - Incomplete test files (3-4 tests in TransferServiceTest, stub for AccountService)
   - Bare README.md
   - Missing Javadoc on most methods
   - No CI/CD, no Dockerfile, no rate limiting
   - A hardcoded API key in application.properties
4. Include complete areas:
   - All REST endpoints working
   - All validation rules enforced
   - FraudCheckService stub
   - AuditService with SLF4J logging
   - Seed data for 6 accounts + 10-15 transactions
   - docs/REQUIREMENTS.md with raw unstructured requirements
5. Use: Java 17, Spring Boot 3.2+, Maven, JUnit 5, Mockito
6. mvn spring-boot:run must work immediately after generation
```

### For Codex (break into GitHub Issues)

```
Create the following GitHub Issues for the fis-banking-api project:

Issue 1: Project scaffold - pom.xml, application.properties, main class, project structure
Issue 2: Domain model - Account and Transaction entities with JPA annotations
Issue 3: Seed data - DataInitializer with 6 accounts and 10-15 transactions
Issue 4: Account endpoints - Controller + Service for lookup, list, search
Issue 5: Transfer endpoint - Controller + Service with 9 validation rules + fraud stub
Issue 6: Transaction history - Controller + Service with date filtering
Issue 7: Error handling - GlobalExceptionHandler + ErrorResponse DTO
Issue 8: Test stubs - Incomplete test files for Sessions 4 exercises
Issue 9: Documentation - Bare README + raw REQUIREMENTS.md for Session 3
Issue 10: Workshop artifacts - build.sh, intentional security issue, old dependency
```

---

## 11. Pre-Workshop Testing Checklist

Run through this checklist after the codebase is generated to ensure it's workshop-ready.

### Startup & Infrastructure

- [ ] `mvn clean install` → BUILD SUCCESS
- [ ] `mvn spring-boot:run` → Application starts on :8080
- [ ] `http://localhost:8080/h2-console` → H2 web console accessible
- [ ] FISBANK database has Account and Transaction tables with seed data

### Account Endpoints

- [ ] `GET /api/accounts` → Returns 6 accounts
- [ ] `GET /api/accounts/1` → Returns Alice Johnson's account
- [ ] `GET /api/accounts/999` → Returns 404
- [ ] `GET /api/accounts/search?holder=alice` → Returns matching accounts

### Transfer Endpoint

- [ ] `POST` valid transfer → 200 with TransferResponse
- [ ] `POST` with amount $0 → 400 error
- [ ] `POST` with amount $60,000 → 400 error
- [ ] `POST` with same from/to → 400 error
- [ ] `POST` to FROZEN account → 400 error
- [ ] `POST` with insufficient funds → 400 error
- [ ] Verify balances update after successful transfer

### Transaction History

- [ ] `GET /api/transactions/account/1` → Returns seeded transactions
- [ ] `GET` with date filter → Returns filtered results

### Workshop-Specific Checks

- [ ] `TransferService.executeTransfer()` is visibly long and smell-heavy
- [ ] `AccountService` has only `getAccountById()` implemented; other methods are missing/stubbed
- [ ] `TransferServiceTest` has only 3–4 basic tests
- [ ] `docs/REQUIREMENTS.md` contains raw, unstructured business requirements
- [ ] `README.md` is a bare skeleton
- [ ] `application.properties` contains the intentional hardcoded key
- [ ] Copilot provides relevant suggestions when opening any Java file in VS Code
