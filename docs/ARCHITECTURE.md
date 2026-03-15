# Architecture

This project is a standard Spring Boot application built for workshop use. It follows a simple layered structure:

- controllers expose REST endpoints
- services contain business logic
- repositories use Spring Data JPA for persistence
- H2 runs in memory for zero-setup local development

## Main Flow

1. A request enters through a controller.
2. The controller passes DTOs into a service.
3. The service reads and writes JPA entities through repositories.
4. Exceptions are translated into a common error response by the global exception handler.

## Persistence

- `Account` stores seeded customer accounts and balances
- `Transaction` stores historical and newly created transfer records
- the database is recreated on startup and seeded automatically

## Workshop Design Notes

- `TransferService.executeTransfer()` is intentionally long and mixed in abstraction level
- `AccountService` is intentionally only partly aligned with the final desired design
- tests are intentionally incomplete so participants can extend them
- there is no CI/CD, Dockerfile, rate limiting, or caching yet
