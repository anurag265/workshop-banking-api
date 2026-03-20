# Day 4: Hands-On Walkthrough

## Advanced Copilot Tooling — AGENT.md, CLI, MCP, Skills, Playwright

---

## Recap Exercise: Onboard the Banking API (20 minutes)

Before new content, let's properly onboard the Banking API project for AI agents.

### Exercise 1: Create AGENT.md + copilot-instructions.md

I'll walk you through this on screen share.

**Step 1:** Open the Banking API project in VS Code.

**Step 2:** Open Copilot Chat in Agent mode and use this prompt:

```
Onboard this repository by creating two files:

1. AGENT.md (project root)
2. .github/copilot-instructions.md

To do this properly:
- Inventory the entire codebase: README, docs, scripts, configs, build files
- Search for TODOs, HACKs, and workarounds
- Validate every build/test/run command by actually running it
- Document the exact sequence of commands that work
- Document any errors you encounter and their fixes
- List key file locations and project structure
- Note coding conventions visible in the code
- Keep each file under 2 pages
- Be specific, not generic — this must be useful for an AI agent
  seeing this repo for the first time

After creating the files, instruct any future AI agent to trust
these instructions and only search if the info is incomplete.
```

**Step 3:** Let Agent mode work. It will explore the codebase, run commands, and create both files.

**Step 4:** Review what it created. Check:
- Are the build commands correct?
- Did it find the right project structure?
- Did it catch the intentional security issue in application.properties?
- Are the coding conventions accurate?

**Step 5:** Start a new chat session. Ask a question about the project. Notice how much better the response is — Copilot now knows your project.

---

### Exercise 2: "How Can I Contribute?"

**Ask Copilot (with AGENT.md now in place):**

```
Read AGENT.md and .github/copilot-instructions.md.

I am a [pick your actual role]:
- Java Developer
- QA Engineer
- Functional Analyst
- Architect / Tech Lead
- Full-Stack Developer
- DevOps Engineer

Based on the current state of this project, give me
5 specific things I can do to contribute right now.
Prioritize by impact. Be concrete — tell me exactly
which files to look at and what to change.
```

Read the response. Compare with someone in a different role — you'll get completely different recommendations.

Save your personal contribution plan to `.scratch/MY-CONTRIBUTIONS.md`.

---

## Copilot CLI: The AI Operating System (20 minutes)

### Exercise 3: Experience CLI as an OS

I'll demonstrate each of these. Follow along in your terminal.

**Open a terminal in the Banking API project directory.**

**Task 1: Explore (File Manager replacement)**
```
"Show me the project structure and tell me what each top-level folder contains"
```

**Task 2: Understand (Documentation replacement)**
```
"Explain what this project does based on the code, not the README"
```

**Task 3: Build (Build tool replacement)**
```
"Build this project and tell me if there are any issues"
```

**Task 4: Test (Test runner replacement)**
```
"Run all tests and give me a summary of what passed and what failed"
```

**Task 5: Find (Search replacement)**
```
"Find all TODO comments in the codebase and list them with file locations"
```

**Task 6: Generate (Script writer replacement)**
```
"Generate a Dockerfile for this Spring Boot application"
```

**Task 7: Automate (CI/CD replacement)**
```
"Create a GitHub Actions workflow that builds, tests, and deploys this app"
```

**Task 8: Version Control (Git client replacement)**
```
"Show me what files have changed, then commit with a descriptive message"
```

**Task 9: Multi-step (Orchestration)**
```
"Build the project, run the tests, if they all pass generate a release
script, if any fail tell me what's broken and suggest fixes"
```

**Reflect:** You just did 9 different tasks without typing a single traditional command. File browsing, documentation, building, testing, searching, generating, CI/CD, git, and orchestration — all from natural language.

That's not a terminal tool. That's an operating system.

---

## Playwright Skill: Instruction-Driven Testing (20 minutes)

### Exercise 4: Set Up and Run Instruction-Driven Tests

**Step 1: Set up Playwright**

Ask Copilot CLI or Agent mode:
```
Set up Playwright in this project for browser-based testing.
Install the necessary dependencies and create a basic
configuration file.
```

**Step 2: Start the application**

Make sure the Banking API (and the To-Do app frontend if available) is running.

**Step 3: Write test instructions**

Create a file called `tests/ui-test-instructions.md`:

```markdown
# UI Test Instructions

## Test 1: Verify API is accessible
1. Open http://localhost:8080/api/accounts in the browser
2. Verify the page shows JSON data with account information
3. Verify at least 5 accounts are listed
4. Take a screenshot as evidence

## Test 2: Verify H2 Console
1. Open http://localhost:8080/h2-console
2. Verify the H2 Console login page loads
3. Take a screenshot

## Test 3: Test Transfer Endpoint (if Swagger is available)
1. Open http://localhost:8080/swagger-ui.html
2. Find the POST /api/transfers endpoint
3. Click "Try it out"
4. Enter a valid transfer request
5. Click "Execute"
6. Verify the response shows status COMPLETED
7. Take a screenshot of the response
```

**Step 4: Ask Copilot to execute the tests**

```
Read tests/ui-test-instructions.md and execute each test
using Playwright. For each test:
1. Follow the instructions exactly
2. Take a screenshot after each step
3. Report pass/fail for each verification
4. Save screenshots to tests/screenshots/
```

**Step 5: Review the results**

Look at the screenshots. Check the pass/fail report. Did AI correctly:
- Navigate to the right URLs?
- Verify the content?
- Take screenshots?
- Report accurate results?

**The key learning:**

You just wrote a test plan in plain English — no Selenium scripts, no test framework configuration, no page objects. AI read your instructions, controlled the browser, executed the tests, and produced evidence.

This is instruction-driven testing. QA engineers write what to test. AI figures out how to test it.

---

## Wrap-Up Checklist

- [ ] AGENT.md created and reviewed for the Banking API
- [ ] copilot-instructions.md created and reviewed
- [ ] Personalized contribution plan saved to .scratch/MY-CONTRIBUTIONS.md
- [ ] Completed 9 CLI tasks without traditional commands
- [ ] Playwright set up and configured
- [ ] UI test instructions written in plain English
- [ ] Instruction-driven tests executed with screenshots

---

## Key Takeaways

**AGENT.md + copilot-instructions.md** — You onboard AI once, it remembers forever. Every future interaction is better because of the 20 minutes you invested today.

**Copilot CLI as an OS** — The terminal isn't where you type commands anymore. It's where you have conversations with your machine. File management, build, test, deploy, git — all from natural language.

**Skills > MCP** — Skills are portable, composable, and come with instructions. MCP gives you raw power. Skills give you packaged intelligence.

**Instruction-Driven Testing** — You don't write test scripts. You write test intentions. AI handles the automation. This changes QA workflows fundamentally.

---