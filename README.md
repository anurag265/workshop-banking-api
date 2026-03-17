# Workshop Banking API

A Spring Boot banking REST API used as a hands-on codebase for GitHub Copilot workshop sessions.

---

## Overview

This project provides a realistic but self-contained banking API with:

- Accounts with balances and statuses
- Fund transfers between accounts with validation rules
- Transaction history with date-range filtering
- H2 in-memory database (no external DB setup required)
- Pre-seeded sample data on every startup

---

## Prerequisites

| Tool | Recommended version |
|------|-------------------|
| JDK  | 17 |
| Maven | 3.9.x |
| VS Code | Latest |

Recommended VS Code extensions:
- Extension Pack for Java
- Spring Boot Extension Pack
- GitHub Copilot
- GitHub Copilot Chat

---

## Quick Start

```bash
# 1. Clone the repository
git clone https://github.com/anurag265/workshop-banking-api.git
cd workshop-banking-api

# 2. Build and run tests
mvn clean install

# 3. Start the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## API Endpoints

### Accounts

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/accounts` | List all accounts |
| GET | `/api/accounts/{id}` | Get account by ID |
| GET | `/api/accounts/search?holder={name}` | Search accounts by holder name |

### Transfers

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/transfers` | Initiate a fund transfer |

**Transfer request body:**

```json
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 500.00,
  "currency": "USD",
  "description": "Rent payment"
}
```

### Transactions

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/transactions/{id}` | Get transaction by ID |
| GET | `/api/transactions/account/{accountId}` | Get transaction history for an account |
| GET | `/api/transactions/account/{accountId}?from=YYYY-MM-DD&to=YYYY-MM-DD` | Filter by date range |

---

## H2 Console

The in-memory database is accessible at:

```
http://localhost:8080/h2-console
```

Connection settings:

| Setting | Value |
|---------|-------|
| JDBC URL | `jdbc:h2:mem:fisbank` |
| Username | `sa` |
| Password | *(leave blank)* |

Useful queries:

```sql
SELECT * FROM accounts;
SELECT * FROM bank_transactions;
```

---

## Sample Data

Six accounts and twelve historical transactions are seeded automatically on startup.

| Account number | Holder | Balance | Status |
|---------------|--------|---------|--------|
| FIS-1001-0001 | Alice Johnson | $25,000 | ACTIVE |
| FIS-1001-0002 | Bob Smith | $15,500 | ACTIVE |
| FIS-1001-0003 | Charlie Davis | $42,000 | ACTIVE |
| FIS-1001-0004 | Diana Lee | $8,750 | ACTIVE |
| FIS-1001-0005 | Frozen Corp | $100,000 | FROZEN |
| FIS-1001-0006 | Closed Account LLC | $0 | CLOSED |

Because H2 is in-memory, data resets to these defaults each time the application restarts.

---

## Quick Smoke Tests

### PowerShell

```powershell
# List all accounts
Invoke-RestMethod http://localhost:8080/api/accounts

# Get a single account
Invoke-RestMethod http://localhost:8080/api/accounts/1

# Transfer funds
$body = @{
    fromAccountId = 1
    toAccountId   = 2
    amount        = 100.00
    currency      = "USD"
    description   = "Smoke test"
} | ConvertTo-Json

Invoke-RestMethod -Method Post `
    -Uri http://localhost:8080/api/transfers `
    -ContentType "application/json" `
    -Body $body
```

### curl

```bash
# List all accounts
curl http://localhost:8080/api/accounts

# Transfer funds
curl -X POST http://localhost:8080/api/transfers \
  -H "Content-Type: application/json" \
  -d '{"fromAccountId":1,"toAccountId":2,"amount":100.00,"currency":"USD","description":"Smoke test"}'
```

---

## Scripts

| Script | Description |
|--------|-------------|
| `scripts/build.sh` | Runs `mvn clean install` |
| `scripts/run-tests.sh` | Runs `mvn test` |

---

## Project Structure

```
workshop-banking-api/
├── pom.xml
├── docs/                        # Workshop documentation
│   ├── API_SPEC.md
│   ├── ARCHITECTURE.md
│   ├── REQUIREMENTS.md
│   ├── SETUP_GUIDE.md
│   └── WORKSHOP_IMPLEMENTATION_GUIDE.md
├── scripts/
│   ├── build.sh
│   └── run-tests.sh
└── src/
    ├── main/java/com/workshop/banking/
    │   ├── BankingApiApplication.java
    │   ├── config/DataInitializer.java
    │   ├── controller/
    │   ├── dto/
    │   ├── exception/
    │   ├── model/
    │   ├── repository/
    │   └── service/
    └── test/java/com/workshop/banking/
```

---

## Documentation

Detailed documentation is in the `docs/` folder:

- `docs/SETUP_GUIDE.md` — step-by-step setup for Windows
- `docs/API_SPEC.md` — full API reference
- `docs/ARCHITECTURE.md` — system design overview
- `docs/REQUIREMENTS.md` — business requirements
- `docs/WORKSHOP_IMPLEMENTATION_GUIDE.md` — guide for workshop facilitators

---

## Resetting Data

Stop and restart the application. The H2 database is re-created from scratch on every startup.

```bash
# Ctrl+C to stop, then:
mvn spring-boot:run
```
