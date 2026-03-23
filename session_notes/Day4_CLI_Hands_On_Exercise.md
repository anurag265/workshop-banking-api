# Copilot CLI: Hands-On Exercise

## The Rule

**You will not type a single traditional command in this exercise.**

No `mvn`, no `npm`, no `git`, no `docker`, no `cat`, no `ls`, no `cd`.

Everything goes through Copilot CLI in natural language. If you don't know how to do something — ask. If something breaks — ask. If you need to install something — ask.

This is not a CLI tool exercise. This is an OS exercise.

---

## Phase 1: Orientation — Learn the Machine (10 minutes)

Open your terminal in the Banking API project directory. Start Copilot CLI.

### Task 1.1: Understand the project
```
What is this project? Give me a summary based on the actual 
code, not the README.
```

### Task 1.2: Explore the structure
```
Show me the project structure. List the main packages and 
explain what each one does.
```

### Task 1.3: Check dependencies
```
What dependencies does this project use? List the key ones 
from pom.xml and tell me what each is for.
```

### Task 1.4: Find something specific
```
Find all files related to "transfer" in this project. 
Show me where the transfer logic lives.
```

### Task 1.5: Check the health
```
Is this project in a buildable state right now? Try building 
it and tell me the result.
```

**Checkpoint:** You now know the project structure, dependencies, and build status — without opening a single file manually.

---

## Phase 2: Development — Build with the OS (15 minutes)

### Task 2.1: Add a new feature
```
I want to add a "notes" field to the Transaction entity. 
It should be an optional String, max 500 characters.
Update the entity, DTO, and any relevant tests.
Show me what you plan to change before doing it.
```

Review the plan. If it looks good:
```
Go ahead and make the changes.
```

### Task 2.2: Verify the change
```
Build the project and run all tests. Tell me if anything 
broke because of the changes we just made.
```

### Task 2.3: Fix if needed
If tests failed:
```
These tests failed: [paste the failure output]
Fix them. The "notes" field is optional and should default 
to null if not provided.
```

### Task 2.4: Add a new endpoint
```
Add a GET endpoint at /api/transactions/search?notes={keyword}
that searches transactions by notes content (partial match, 
case-insensitive). Include input validation.
```

### Task 2.5: Test the new endpoint
```
Run the application and test the new search endpoint.
First add a transaction with notes "quarterly payment", 
then search for "quarterly" and verify it returns results.
```

**Checkpoint:** You just built a feature, ran tests, fixed failures, added an endpoint, and tested it — all from natural language.

---

## Phase 3: DevOps — Automate with the OS (15 minutes)

### Task 3.1: Generate a Dockerfile
```
Generate a Dockerfile for this Spring Boot application.
Use a multi-stage build: first stage builds with Maven, 
second stage runs with a slim JRE. Use Java 17.
```

### Task 3.2: Generate docker-compose
```
Create a docker-compose.yml that runs the Banking API.
Expose port 8080. Add a health check that hits /api/accounts.
```

### Task 3.3: Create a build script
```
Create a shell script called scripts/build-and-run.sh that:
1. Cleans the project
2. Runs all tests
3. If tests pass, builds a Docker image
4. Starts the container
5. Waits for it to be healthy
6. Hits /api/accounts and prints the response
7. If any step fails, stop and show the error

Make it executable.
```

### Task 3.4: Generate a CI pipeline
```
Create a GitHub Actions workflow at .github/workflows/ci.yml
that triggers on push and pull request. It should:
1. Check out the code
2. Set up Java 17
3. Build with Maven
4. Run unit tests
5. Run integration tests
6. Build the Docker image
7. Upload test results as artifacts

Use caching for Maven dependencies.
```

### Task 3.5: Validate the pipeline
```
Check if the GitHub Actions workflow I just created has any 
syntax errors or missing steps. Validate it against GitHub 
Actions best practices.
```

**Checkpoint:** You now have a Dockerfile, docker-compose, build script, and CI pipeline — none of which you wrote manually.

---

## Phase 4: Git & Documentation — Ship with the OS (10 minutes)

### Task 4.1: See what changed
```
Show me everything that changed since the last commit. 
Summarize the changes by category: features, config, 
tests, documentation, devops.
```

### Task 4.2: Stage and commit
```
Stage all the changes and commit with a descriptive message 
that follows conventional commit format. Group related 
changes logically — don't put everything in one commit 
if it makes more sense as multiple commits.
```

### Task 4.3: Generate a changelog
```
Based on the commits we just made, generate a CHANGELOG.md 
entry for today. Include: what was added, what was changed, 
and any breaking changes.
```

### Task 4.4: Update the README
```
Update README.md to include:
1. The new "notes" field on transactions
2. The new search endpoint with a curl example
3. Docker instructions (build and run)
4. How to run the CI pipeline locally

Keep the existing content, just add to it.
```

**Checkpoint:** Changes committed, changelog generated, documentation updated — all from conversation.

---

## Phase 5: Troubleshooting — Debug with the OS (10 minutes)

### Task 5.1: Break something intentionally

Open `TransferService.java` manually and delete a random line in the `executeTransfer` method. Save the file.

### Task 5.2: Ask CLI to find the problem
```
Something is broken in the project. Build it and tell me 
what's wrong.
```

### Task 5.3: Ask CLI to fix it
```
Fix the compilation error you just found. Explain what was 
wrong and what you changed.
```

### Task 5.4: Verify the fix
```
Build and run all tests. Confirm everything passes.
```

### Task 5.5: Go deeper
```
Beyond the error I just introduced, are there any other 
issues in this project? Check for: code smells, security 
issues, missing tests, outdated dependencies, and 
configuration problems. Give me the top 5.
```

**Checkpoint:** You diagnosed a bug, fixed it, verified the fix, and got a health report — all from asking.

---

## Phase 6: Set Up Playwright (15 minutes)

Now let's extend the OS with browser testing capabilities.

### Task 6.1: Install Playwright
```
I want to use Playwright for browser-based testing in this 
project. Set it up — install everything needed, download 
browsers, create a configuration file, and verify the 
installation works.
```

If it hits any errors (proxy, permissions, download blocked):
```
I got this error: [paste the error]
I'm on a corporate network. Help me work around this.
```

If browser downloads are blocked:
```
Playwright can't download browsers. I already have Chrome 
installed on this machine. Configure Playwright to use my 
existing Chrome browser instead of downloading a new one.
```

### Task 6.2: Create test instructions

```
Create a file called tests/e2e/test-instructions.md with 
these test scenarios for our Banking API:

Test 1: API Health Check
- Open http://localhost:8080/api/accounts in the browser
- Verify JSON data loads with at least 5 accounts
- Take a screenshot

Test 2: Account Lookup
- Navigate to http://localhost:8080/api/accounts/1
- Verify the response contains "Alice Johnson"
- Take a screenshot

Test 3: Invalid Transfer
- Send a POST to http://localhost:8080/api/transfers with 
  amount $60,000 (over the limit)
- Verify the error message says "Maximum single transfer"
- Take a screenshot

Test 4: Successful Transfer
- Send a valid POST transfer of $100 from account 1 to account 2
- Verify status is COMPLETED
- Check account 1 balance decreased
- Take a screenshot
```

### Task 6.3: Execute the tests
```
Read tests/e2e/test-instructions.md and execute each test 
using Playwright. For each test:
1. Follow the instructions exactly
2. Take a screenshot after each verification step
3. Report pass or fail with details
4. Save screenshots to tests/e2e/screenshots/
5. Generate a test report summary at the end
```

### Task 6.4: Generate a proper test file
```
Based on the test instructions that just passed, generate 
a proper Playwright test file at tests/e2e/banking-api.spec.ts
that I can run with npx playwright test. Convert each test 
instruction into a proper Playwright test with assertions.
```

### Task 6.5: Run the generated tests
```
Run the Playwright tests you just generated. Show me the 
results and any failures.
```

**Checkpoint:** You installed a testing framework, wrote tests in plain English, AI executed them, then AI converted your instructions into reusable automated tests.

---

## The Reflection

Count what you did in this exercise:

```
Phase 1: 5 tasks — Explored and understood the project
Phase 2: 5 tasks — Built a feature, tested it, added an endpoint
Phase 3: 5 tasks — Dockerfile, compose, build script, CI pipeline
Phase 4: 4 tasks — Commits, changelog, documentation
Phase 5: 5 tasks — Broke it, diagnosed, fixed, audited
Phase 6: 5 tasks — Set up Playwright, wrote tests, executed them

Total:  29 tasks
Traditional commands typed: 0
```

Every single task was a conversation.

You didn't use a tool. You used an operating system.

---

## Checklist

- [ ] Explored project structure without ls or find
- [ ] Built the project without typing mvn
- [ ] Added a feature without manually editing more than one file
- [ ] All tests pass after the feature addition
- [ ] Dockerfile and docker-compose created
- [ ] Build script created and validated
- [ ] GitHub Actions CI pipeline generated
- [ ] Changes committed without typing git
- [ ] README and CHANGELOG updated
- [ ] Intentionally broken code diagnosed and fixed by CLI
- [ ] Playwright installed (or configured with existing Chrome)
- [ ] Test instructions written in plain English
- [ ] Tests executed by AI with screenshots
- [ ] Proper Playwright test file generated from instructions

---

*You just completed an entire development cycle — explore, build, test, automate, document, debug, and ship — without typing a single traditional command. The terminal is now your conversation partner. That's the paradigm shift.*
