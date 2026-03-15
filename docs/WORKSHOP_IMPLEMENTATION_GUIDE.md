# Workshop Implementation Guide

## Purpose

This document explains what has been built across the first 6 implementation phases of the workshop banking API, why those choices were made, and what remains intentionally incomplete for workshop participants to explore during the course.

The project is a workshop codebase, not a production banking system. It is designed to:

- start with zero setup
- be understandable in a short time
- contain realistic patterns and tradeoffs
- include deliberate gaps, smells, and incomplete areas for hands-on exercises

The implementation follows the workshop PRD as closely as possible while using the neutral package namespace `com.workshop.banking`.

---

## Current Project Summary

The application is a Java 17 Spring Boot banking API with:

- Spring Boot 3.2.x
- Maven build
- H2 in-memory database
- seeded sample accounts and transactions
- account lookup endpoints
- transfer endpoint
- transaction history endpoints
- error handling
- basic audit logging
- a simple fraud-check stub
- intentionally incomplete tests and workshop artifacts

The system is designed to reset easily by restarting the application because the database is in memory.

---

## Phase 1: Foundation

### Goal

Create a runnable Spring Boot baseline with no external setup required.

### What Was Added

- `pom.xml`
- main Spring Boot application entry point
- `application.properties`
- bare `README.md`
- startup smoke test
- standard Maven/Spring project structure

### Key Decisions

- Java target set to 17
- H2 in-memory database configured for zero setup
- Spring Boot devtools included for workshop convenience
- a deliberately old dependency was added early for security scanning exercises
- a hardcoded workshop API key was included intentionally for governance/security sessions

### Outcome

At the end of Phase 1, the project could:

- compile successfully
- start on port `8080`
- expose the H2 console

### Files Introduced or Established

- `pom.xml`
- `src/main/java/com/workshop/banking/BankingApiApplication.java`
- `src/main/resources/application.properties`
- `README.md`
- `src/test/java/com/workshop/banking/BankingApiApplicationTests.java`

### Why This Matters For The Workshop

This phase ensures every participant can clone the repository and run the application quickly, which is one of the most important workshop requirements.

---

## Phase 2: Domain And Data

### Goal

Introduce the persistence model and sample banking data so the application has a realistic domain foundation.

### What Was Added

- `Account` entity
- `Transaction` entity
- `TransactionStatus` enum
- `AccountRepository`
- `TransactionRepository`
- startup data seeding with accounts and transactions

### Seed Data Included

Accounts:

- Alice Johnson
- Bob Smith
- Charlie Davis
- Diana Lee
- Frozen Corp
- Closed Account LLC

Transactions:

- 12 historical transactions were seeded
- includes both successful and failed history
- enough data to support transaction history demos immediately

### Important Implementation Notes

- transaction data uses the table name `bank_transactions`
- this avoids keyword conflicts that can happen with `transaction`
- seed data is created through `DataInitializer`

### Outcome

At the end of Phase 2, the application could:

- create the schema on startup
- seed accounts automatically
- seed meaningful transaction history automatically

### Files Added

- `src/main/java/com/workshop/banking/model/Account.java`
- `src/main/java/com/workshop/banking/model/Transaction.java`
- `src/main/java/com/workshop/banking/model/TransactionStatus.java`
- `src/main/java/com/workshop/banking/repository/AccountRepository.java`
- `src/main/java/com/workshop/banking/repository/TransactionRepository.java`
- `src/main/java/com/workshop/banking/config/DataInitializer.java`

### Why This Matters For The Workshop

Participants need a system that already has realistic data so they can focus on prompts, requirements, testing, and refactoring instead of setup work.

---

## Phase 3: Core API

### Goal

Build the main REST API surface so the application is functionally usable.

### What Was Added

DTOs:

- `AccountDTO`
- `TransferRequest`
- `TransferResponse`
- `TransactionDTO`
- `ErrorResponse`

Controllers:

- `AccountController`
- `TransferController`
- `TransactionController`

Services:

- `AccountService`
- `TransferService`
- `TransactionService`
- `FraudCheckService`
- `AuditService`

Exceptions:

- `AccountNotFoundException`
- `InsufficientFundsException`
- `InvalidTransferException`
- `TransactionNotFoundException`
- `GlobalExceptionHandler`

### Working Endpoints

- `GET /api/accounts`
- `GET /api/accounts/{id}`
- `POST /api/transfers`
- `GET /api/transactions/{id}`
- `GET /api/transactions/account/{accountId}`
- `GET /api/transactions/account/{accountId}?from=&to=`

### Intentional Design Choice Preserved

The account search endpoint was not added even though it appears in the PRD endpoint list. This was left out intentionally because the workshop design calls for incomplete account functionality that participants can generate later.

### Outcome

At the end of Phase 3, the system could:

- return account data
- accept transfer requests
- update balances
- persist transfer transactions
- return transaction history
- return common JSON error responses

### Why This Matters For The Workshop

This phase provides the main live system participants will inspect, query, test, and extend throughout the course.

---

## Phase 4: Business Rules And Workshop Behavior

### Goal

Align the transfer flow more closely with the PRD business rules while preserving intentional code quality issues for training purposes.

### What Was Refined

The transfer process now follows the intended validation flow more closely:

1. amount must be greater than zero
2. minimum amount check
3. maximum amount check
4. source account must exist
5. destination account must exist
6. self-transfer is rejected
7. source account must be active
8. destination account must be active
9. fraud check runs
10. balance sufficiency check runs after fraud check
11. funds are moved and transaction is saved

### Workshop-Specific Choices Preserved On Purpose

- `TransferService.executeTransfer()` remains long and mixed in abstraction level
- hardcoded transfer thresholds remain in the service
- vague comments remain in the method
- the method is not overly refactored
- the fraud check remains intentionally simple

### Fraud Check Behavior

The stub flags:

- transfers above `10000.00`
- descriptions containing suspicious language such as `urgent` or `wire immediately`

The transfer still proceeds even when flagged. This is intentional and useful for future exercises.

### Audit Logging

The audit service logs:

- success
- flagged outcomes
- failures

This gives participants something concrete to review, interpret, and improve later.

### Outcome

At the end of Phase 4, the application had behavior that was closer to the PRD while still preserving the workshop’s teaching value through intentional code smells.

### Why This Matters For The Workshop

This phase creates the core material for explanation, review, debugging, and refactoring exercises.

---

## Phase 5: Tests, Scripts, And Workshop Docs

### Goal

Add the supporting project assets required for workshop exercises while keeping them intentionally incomplete where the PRD expects that.

### What Was Added

Documentation:

- `docs/REQUIREMENTS.md`
- `docs/API_SPEC.md`
- `docs/ARCHITECTURE.md`

Scripts:

- `scripts/build.sh`
- `scripts/run-tests.sh`

Tests:

- `TransferServiceTest`
- `AccountServiceTest`
- `TransferControllerTest`

### Intentional Incompleteness Preserved

- `TransferServiceTest` covers only a small set of scenarios
- `AccountServiceTest` is basically a placeholder
- no full integration test suite was added
- docs are useful but still concise rather than polished end-user documentation
- scripts are minimal wrappers around Maven

### Environment Note

The machine used for implementation runs Java 25. Mockito inline mocking on this machine did not behave reliably for service-class mocking, so the tests were shaped to avoid those JVM-specific issues while preserving the intended workshop simplicity.

### Outcome

At the end of Phase 5:

- `mvn test` passed
- workshop docs existed in the expected locations
- basic test artifacts existed for participants to extend
- scripts existed for CLI exercises

### Why This Matters For The Workshop

This phase sets up the material for sessions on requirements, testing, CLI workflows, and documentation generation.

---

## Phase 6: Final Workshop Readiness

### Goal

Perform a final pass to make sure the codebase is workshop-ready and aligned with the overall acceptance intent.

### What Was Verified

- `mvn clean install` succeeds
- the application packages correctly
- the seeded database and H2 configuration are in place
- the workshop docs and scripts exist
- the intentional hardcoded API key remains in place
- the old dependency remains in place for security scanning exercises
- the intentionally incomplete areas remain incomplete

### Important Workshop Constraints Still Intentionally True

- no CI/CD pipeline
- no Dockerfile
- no rate limiting
- no retries
- no circuit breaker logic
- no caching layer
- no polished production-grade documentation
- no fully completed account search functionality

### Remaining Imperfections That Are Deliberate

- the account search endpoint is still missing
- malformed request-body handling can still fall into a generic `500`
- transfer logic is intentionally not elegantly structured
- the project still contains workshop-only security weaknesses

### Outcome

The project is workshop-ready and behaves like a purposeful training codebase rather than a production platform.

---

## What Participants Will Cover In The Workshop

Below is the recommended mapping between workshop sessions and the current codebase.

## Session 1: Explain And Understand

Primary focus:

- understand how the transfer flow works
- inspect service layering and business-rule order
- identify surprising logic ordering

Recommended files:

- `src/main/java/com/workshop/banking/service/TransferService.java`
- `src/main/java/com/workshop/banking/service/FraudCheckService.java`
- `src/main/java/com/workshop/banking/service/AuditService.java`

Participant tasks:

- ask Copilot to explain `executeTransfer()`
- identify risks and edge cases
- reason about why fraud check happens before the balance check

---

## Session 2: Code Generation

Primary focus:

- generate missing or incomplete features

Recommended files:

- `src/main/java/com/workshop/banking/service/AccountService.java`
- `src/main/java/com/workshop/banking/controller/AccountController.java`
- DTO classes under `src/main/java/com/workshop/banking/dto`

Participant tasks:

- add account search behavior
- improve account service methods
- add DTO validation annotations
- extend controller behavior

---

## Session 3: Requirements Breakdown

Primary focus:

- turn messy business input into structured requirements and implementation tasks

Recommended files:

- `docs/REQUIREMENTS.md`
- `docs/API_SPEC.md`

Participant tasks:

- convert raw requirements into user stories
- identify ambiguity
- propose acceptance criteria
- identify missing decisions

---

## Session 4: Testing

Primary focus:

- expand the incomplete testing surface

Recommended files:

- `src/test/java/com/workshop/banking/service/TransferServiceTest.java`
- `src/test/java/com/workshop/banking/service/AccountServiceTest.java`
- `src/test/java/com/workshop/banking/controller/TransferControllerTest.java`

Participant tasks:

- add boundary tests
- add negative-path coverage
- add more controller tests
- introduce parameterized tests
- create stronger integration tests

---

## Session 5: CLI And DevOps

Primary focus:

- use CLI tooling and AI assistance to automate project workflows

Recommended files:

- `scripts/build.sh`
- `scripts/run-tests.sh`
- project root

Participant tasks:

- improve shell scripts
- generate CI pipeline files
- create a Dockerfile
- improve developer automation

---

## Session 6: Code Review And Refactoring

Primary focus:

- inspect code quality and propose sensible refactors

Recommended files:

- `src/main/java/com/workshop/banking/service/TransferService.java`
- `README.md`
- service and controller classes

Participant tasks:

- identify code smells
- propose extractions and cleaner abstractions
- improve naming and documentation
- discuss tradeoffs between readability and workshop intent

---

## Session 7: Agentic Development

Primary focus:

- ask an AI coding assistant to add larger features across the codebase

Recommended targets:

- rate limiting
- retry logic
- circuit breaker logic
- caching

Participant tasks:

- generate new architectural features
- evaluate multi-file changes
- inspect generated code critically

---

## Session 8: Governance And Security

Primary focus:

- find and reason about security and governance issues in the repo

Recommended files:

- `src/main/resources/application.properties`
- `pom.xml`

Participant tasks:

- detect the hardcoded API key
- detect the intentionally old dependency
- discuss secrets handling
- discuss dependency hygiene
- propose policy and governance improvements

---

## Intentionally Incomplete Or Imperfect Areas

These are not mistakes in the workshop design. They are teaching material.

- account search endpoint is missing
- `AccountService` is not fully evolved
- DTO validation annotations are not present
- transfer logic is intentionally long and rough
- tests are intentionally incomplete
- shell scripts are intentionally basic
- README is intentionally minimal
- security weaknesses remain in place for exercises

Participants should be encouraged to improve these areas during the workshop rather than treating them as accidental defects.

---

## How To Run The Project

Start the app:

```bash
mvn spring-boot:run
```

Run tests:

```bash
mvn test
```

Build the project:

```bash
mvn clean install
```

H2 console:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:fisbank`
- username: `sa`
- password: blank

---

## Suggested Next Improvements After The Workshop

If this repository is ever promoted beyond workshop use, the first priorities should be:

- rename Maven coordinates away from `com.fis`
- add request-body validation and better bad-request handling
- implement account search cleanly
- refactor transfer logic into smaller units
- add proper integration tests
- replace the demo key and old dependency
- add CI/CD and container support

---

## Final Status

The codebase now covers the six implementation phases needed to produce a usable workshop application:

- foundation
- domain and data
- core API
- business rules and workshop behavior
- tests, scripts, and docs
- final workshop readiness

It is ready to be used as a hands-on teaching repository for explanation, generation, testing, refactoring, agentic workflows, and governance/security exercises.
