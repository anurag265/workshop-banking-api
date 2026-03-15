# API Spec

## Base URL

`http://localhost:8080`

## Accounts

### `GET /api/accounts`

Returns all seeded accounts.

### `GET /api/accounts/{id}`

Returns a single account by numeric id.

## Transfers

### `POST /api/transfers`

Request body:

```json
{
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 500.00,
  "currency": "USD",
  "description": "Rent payment"
}
```

Response body:

```json
{
  "referenceId": "uuid-value",
  "status": "COMPLETED",
  "fromAccount": "FIS-1001-0001",
  "toAccount": "FIS-1001-0002",
  "amount": 500.00,
  "timestamp": "2026-03-15T10:30:00"
}
```

## Transactions

### `GET /api/transactions/{id}`

Returns one transaction by id.

### `GET /api/transactions/account/{accountId}`

Returns transaction history for an account.

Optional query params:
- `from=YYYY-MM-DD`
- `to=YYYY-MM-DD`

## Errors

Error responses use this shape:

```json
{
  "timestamp": "2026-03-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Transfer amount must be greater than zero",
  "path": "/api/transfers"
}
```
