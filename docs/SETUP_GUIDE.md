# Participant Setup Guide

## Purpose

This guide explains how to set up and run the workshop banking API from scratch without needing external help.

It covers:

- required software
- recommended versions
- installation steps
- VS Code setup
- how to run the project
- how to verify it works
- common troubleshooting issues

This guide is intended for workshop participants using Windows, though most of the project setup is similar on macOS and Linux.

---

## Project Overview

This project is a Java Spring Boot application with:

- Java 17 target
- Maven build
- H2 in-memory database
- REST API on port `8080`
- zero external database setup

Important behavior:

- the database is in memory
- data is recreated on each app restart
- no Docker is required
- no environment variables are required

---

## Minimum Requirements

Participants need:

- a computer that can run Java and VS Code
- internet access for first-time dependency downloads
- permission to install development tools locally

Recommended machine profile:

- 8 GB RAM or more
- 2+ CPU cores
- at least 2 GB of free disk space

---

## Required Software

Install these before opening the project.

## 1. Java

Required:

- JDK 17 recommended

Accepted:

- OpenJDK 17
- Amazon Corretto 17
- Eclipse Temurin 17

Notes:

- the project compiles with Java 17 target
- a newer JDK may work, but Java 17 is the recommended workshop baseline

How to verify:

```powershell
java -version
```

You should see Java 17 or another compatible JDK installed.

If multiple Java versions are installed, make sure VS Code and Maven are using the correct one.

---

## 2. Maven

Required:

- Apache Maven 3.9.x recommended

How to verify:

```powershell
mvn -v
```

You should see:

- a Maven version
- the Java version Maven is using

If `mvn` is not recognized, Maven is either not installed or not added to your `PATH`.

---

## 3. Visual Studio Code

Required:

- Visual Studio Code

Recommended extensions:

- Extension Pack for Java
- Spring Boot Extension Pack
- GitHub Copilot
- GitHub Copilot Chat

These are not all strictly required to run the app, but they are strongly recommended for the workshop because much of the course relies on code understanding, navigation, and AI assistance.

---

## Optional But Helpful Tools

- Git
- Postman or another API client
- PowerShell 7 or later

Notes:

- Windows PowerShell 5 works, but command examples are sometimes easier in PowerShell 7
- `curl.exe` can also be used for API testing

---

## Install Checklist

Before continuing, confirm:

- Java is installed
- Maven is installed
- VS Code is installed
- VS Code Java extensions are installed

Verify with:

```powershell
java -version
mvn -v
```

---

## Opening The Project In VS Code

## Option 1: Open From Terminal

From PowerShell:

```powershell
code "C:\Users\anura\Desktop\Java Sample"
```

If `code` is not recognized, open VS Code manually and use `File -> Open Folder`.

## Option 2: Open From VS Code

1. Open VS Code
2. Click `File`
3. Click `Open Folder...`
4. Select the project folder

Project folder:

`C:\Users\anura\Desktop\Java Sample`

---

## First-Time VS Code Setup

When VS Code opens the project:

1. wait for the Java extension pack to initialize
2. allow Maven project import if prompted
3. let VS Code finish indexing the project

What you should see:

- Java source folders recognized
- `pom.xml` recognized as a Maven project
- no major import errors in Java files

This first load can take a minute or two depending on network speed and machine performance.

---

## Project Structure Participants Should Know

Important locations:

- `pom.xml` - Maven configuration and dependencies
- `src/main/java` - application code
- `src/main/resources/application.properties` - runtime configuration
- `src/test/java` - tests
- `docs/` - workshop documentation
- `scripts/` - simple project scripts

Main application entry point:

- `src/main/java/com/workshop/banking/BankingApiApplication.java`

---

## First Build

Run this in the integrated VS Code terminal or PowerShell:

```powershell
mvn test
```

Why this is a good first check:

- it downloads dependencies
- it compiles the project
- it confirms the test setup is working

Expected result:

- Maven downloads dependencies on first run
- tests pass
- build ends with `BUILD SUCCESS`

If this succeeds, your local environment is in good shape.

---

## Running The Application

There are two recommended ways.

## Option 1: Run From VS Code

1. Open:
   `src/main/java/com/workshop/banking/BankingApiApplication.java`
2. Click the `Run` link above the `main()` method

This is the easiest method for most workshop participants.

## Option 2: Run From Terminal

```powershell
mvn spring-boot:run
```

Expected result:

- Spring Boot starts successfully
- server runs on `http://localhost:8080`

---

## Verifying The App Is Running

When startup succeeds, you should be able to access:

- API base: `http://localhost:8080`
- H2 console: `http://localhost:8080/h2-console`

The terminal should show Spring Boot startup logs and a message indicating Tomcat started on port `8080`.

---

## H2 Database Setup

The project uses H2 in-memory database.

This means:

- no local database install is needed
- schema is created automatically
- sample data is seeded automatically
- all data resets when the app restarts

H2 console connection values:

- JDBC URL: `jdbc:h2:mem:fisbank`
- username: `sa`
- password: leave blank

How to use it:

1. open `http://localhost:8080/h2-console`
2. enter the JDBC URL above
3. use username `sa`
4. leave password empty
5. connect

Useful sample queries:

```sql
select * from accounts;
select * from bank_transactions;
```

Expected results:

- 6 seeded accounts
- 10+ seeded transactions

---

## Quick API Smoke Test

Once the app is running, test these endpoints.

## Accounts

```powershell
Invoke-RestMethod http://localhost:8080/api/accounts
```

```powershell
Invoke-RestMethod http://localhost:8080/api/accounts/1
```

## Transactions

```powershell
Invoke-RestMethod http://localhost:8080/api/transactions/account/1
```

## Transfer

```powershell
$body = @{
  fromAccountId = 1
  toAccountId = 2
  amount = 100.00
  currency = "USD"
  description = "Workshop smoke test"
} | ConvertTo-Json

Invoke-RestMethod -Method Post `
  -Uri http://localhost:8080/api/transfers `
  -ContentType "application/json" `
  -Body $body
```

Expected result:

- a successful transfer response
- balances update during that application run

---

## Resetting The Data

Because H2 is in-memory, resetting is simple:

1. stop the app
2. start the app again

That reloads the original seeded data.

This is useful for workshop exercises because participants can experiment freely and return to a clean state quickly.

---

## Required Network Access

Participants need internet access at least once for:

- Maven dependency downloads
- VS Code extension installs
- GitHub Copilot and Copilot Chat authentication

After dependencies are downloaded, the project itself runs locally without external services.

---

## GitHub Copilot Setup

If the workshop uses Copilot features, participants should:

1. install `GitHub Copilot`
2. install `GitHub Copilot Chat`
3. sign in with the GitHub account provided or approved for the workshop
4. confirm Copilot is active in VS Code

How to verify:

- open a Java file
- start typing a small code pattern
- check whether inline AI suggestions appear

If Copilot is not part of a participant’s environment, the project still runs fine without it.

---

## Common Problems And Fixes

## Problem 1: `java` Is Not Recognized

Cause:

- Java is not installed
- Java `bin` folder is not in `PATH`

Fix:

- install JDK 17
- restart the terminal
- run `java -version` again

---

## Problem 2: `mvn` Is Not Recognized

Cause:

- Maven is not installed
- Maven `bin` folder is not in `PATH`

Fix:

- install Apache Maven
- add Maven `bin` directory to `PATH`
- restart the terminal
- run `mvn -v`

---

## Problem 3: VS Code Shows Java Import Errors

Cause:

- project import not finished
- Java extensions not installed
- wrong JDK configured in VS Code

Fix:

1. install Java Extension Pack
2. wait for project import to finish
3. check the selected Java runtime in VS Code
4. reload VS Code if needed

---

## Problem 4: Port 8080 Already In Use

Cause:

- another app is using port `8080`
- a previous run of this app is still active

Fix:

- stop the running app in the terminal with `Ctrl+C`
- close the old terminal
- rerun the app

If something else is using `8080`, stop that process first.

---

## Problem 5: Maven Build Fails On First Run

Cause:

- network issue while downloading dependencies
- proxy/firewall restrictions
- local Maven cache problem

Fix:

1. check internet connectivity
2. rerun `mvn test`
3. if in a corporate environment, confirm Maven Central access is allowed

---

## Problem 6: H2 Console Does Not Open

Cause:

- app is not fully started
- wrong URL
- wrong JDBC URL

Fix:

1. confirm app is running on port `8080`
2. open `http://localhost:8080/h2-console`
3. use `jdbc:h2:mem:fisbank`
4. username `sa`
5. blank password

---

## Problem 7: API Testing In PowerShell Is Awkward

Cause:

- PowerShell command quoting can be tricky

Fix:

- prefer `Invoke-RestMethod` for JSON requests
- use PowerShell hashtables and `ConvertTo-Json`
- or use Postman if preferred

---

## Problem 8: Tests Use Java 25 But Workshop Target Is Java 17

Explanation:

- the project compiles for Java 17
- a newer JDK may still run it
- some tool behavior can differ on newer JDKs

Recommended fix:

- install and use JDK 17 if possible for the workshop

---

## Recommended Participant Workflow

Use this sequence on day one:

1. install Java
2. install Maven
3. install VS Code
4. install VS Code Java and Spring extensions
5. install Copilot extensions if required
6. open the project folder
7. run `mvn test`
8. run `mvn spring-boot:run`
9. open H2 console
10. test one or two API endpoints

If all of that works, the participant is ready for workshop exercises.

---

## Setup Validation Checklist

Participants are fully set up when all of these are true:

- `java -version` works
- `mvn -v` works
- VS Code opens the project without major Java errors
- `mvn test` ends with `BUILD SUCCESS`
- `mvn spring-boot:run` starts the app
- `http://localhost:8080/h2-console` opens
- `/api/accounts` returns account data

---

## What Participants Do Not Need

Participants do not need:

- Docker
- PostgreSQL, MySQL, or Oracle
- any external banking system
- cloud deployment setup
- environment variables
- secrets files

This is intentional. The workshop should be runnable with minimal setup.

---

## Summary

To participate successfully, a user only needs:

- JDK 17
- Maven
- VS Code
- Java/Spring extensions

After that, they can:

- open the project
- run tests
- run the app
- inspect H2
- call endpoints
- begin workshop exercises

This setup is intentionally lightweight so participants can focus on coding, testing, refactoring, prompting, and workshop discussions rather than environment issues.
