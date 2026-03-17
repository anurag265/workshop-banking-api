# Getting Started with Copilot + Banking API

## Your First Conversation with the Codebase

Welcome to the workshop! Before we dive into today's session, let's get you comfortable with the project and with Copilot. Follow these steps at your own pace. If you get stuck at any point — that's the whole point. Use Copilot to get unstuck.

---

### Step 1: Talk to Your Code

Open the `fis-banking-api` project in VS Code. Open Copilot Chat (Ctrl+Alt+I) and make sure you're in **Ask** mode.

**Ask Copilot:**

```
Look at this project and tell me:
- What does this application do?
- What are the main features?
- What tech stack is it built on?
```

Read the response. Does it match what you see in the project? Did it miss anything?

---

### Step 2: Make It Personal

Now personalize the conversation. Add your role to the prompt.

**Pick the one that matches you and ask Copilot:**

**If you're a Developer:**
```
I am a Java developer. Based on this codebase, tell me:
1. Three things I can improve in this code
2. What patterns or best practices are being used
3. What's missing that a production app would need
```

**If you're a QA Engineer:**
```
I am a QA engineer. Based on this codebase, tell me:
1. Three areas I should test first
2. What edge cases are likely hiding in this code
3. What testing infrastructure is currently in place (or missing)
```

**If you're a Functional Analyst:**
```
I am a functional analyst. Based on this codebase, tell me:
1. Three features this application currently supports
2. What business rules are implemented
3. What requirements seem incomplete or ambiguous
```

**If you're an Architect / Tech Lead:**
```
I am a software architect. Based on this codebase, tell me:
1. Three architectural decisions that were made here
2. What design patterns are used
3. What would you change to make this production-ready at scale
```

**If you're a Full-Stack Developer:**
```
I am a full-stack developer. Based on this codebase, tell me:
1. What API endpoints are available for a frontend to consume
2. What's missing on the backend for a complete user experience
3. How would you add a simple UI for this API
```

> **Note:** You can also combine roles or make up your own prompt. The point is to see how Copilot tailors its response to your perspective.

---

### Step 3: Ask Copilot to Write Setup Instructions

Don't look at the README. Don't Google it. Ask Copilot.

**Ask Copilot:**

```
I just cloned this project and I've never seen it before.
Write me step-by-step instructions to:
1. Install any prerequisites I need
2. Build the project
3. Run it locally
4. Verify it's working by hitting an endpoint

Be specific — include the exact commands I should run.
```

Read the instructions Copilot gives you. Do they look right? Are the commands specific to this project?

---

### Step 4: Actually Run It

Now follow the instructions Copilot gave you. Open the terminal in VS Code (Ctrl+`) and run the commands one by one.

**What you should see if everything works:**
- `mvn clean install` → BUILD SUCCESS
- `mvn spring-boot:run` → Application starts on port 8080
- Open a browser or use curl: `http://localhost:8080/api/accounts` → JSON response with account data

**If something goes wrong — good.** That's the exercise.

Ask Copilot for help:

```
I ran [paste the command] and got this error:
[paste the error message]

What's wrong and how do I fix it?
```

Keep going until the application is running and you can see data from the API.

---

### Step 5: If You're Stuck, Ask Copilot to Document the Fix

If you hit a wall that took more than a few minutes to resolve, ask Copilot to capture the solution so the next person doesn't struggle.

**Ask Copilot:**

```
I had trouble getting this project running. Based on our conversation,
write a detailed INSTRUCTIONS.md file that covers:
1. Prerequisites (Java version, Maven, etc.)
2. Step-by-step build and run instructions
3. Common issues and their fixes
4. How to verify the app is running correctly

Make it clear enough that a new developer can follow it
without asking anyone for help.
```

If you're in Agent mode, Copilot will create the file directly. If you're in Ask mode, copy the output and save it as `INSTRUCTIONS.md` in the project root.

---

### Step 6: Build Your Personal Project Wiki

Create a `.scratch` folder in the project root. This is your personal knowledge base — a place to capture everything you learn about this codebase. Copilot can read these files and use them as context in future conversations.

**Ask Copilot:**

```
Create a .scratch folder in this project with the following files:

1. .scratch/OVERVIEW.md — A summary of what this project does,
   the tech stack, and key design decisions. Write it from
   my perspective as a [your role].

2. .scratch/ARCHITECTURE.md — How the code is organized.
   List the packages, what each one does, and how they
   connect to each other.

3. .scratch/MY-NOTES.md — Start this file with a header
   "My Learning Notes" and three sections:
   - Things I understand
   - Things I need to explore more
   - Questions I want to ask the team
```

If you're in Agent mode, Copilot will create all three files. If you're in Ask mode, create the folder manually and copy the outputs into each file.

> **Why this matters:** Throughout the rest of the workshop, you can tell Copilot `#file:.scratch/OVERVIEW.md` to give it your personalized context. Your wiki grows with you.

---

### Step 7: Get 5 Tasks to Make This Codebase Better

Now ask Copilot to audit the project and suggest improvements. This is like getting a code review from a senior engineer on your first day.

**Ask Copilot:**

```
#codebase Analyze this entire project and give me exactly
5 concrete tasks I can do to make this codebase better.

For each task:
- Title (one line)
- Why it matters
- Which file(s) to change
- Estimated effort (small / medium / large)

Prioritize by impact. Consider: code quality, testing,
documentation, security, and maintainability.

Format as a numbered list I can use as a task tracker.
```

Copy the response and save it to `.scratch/TASKS.md`.

Read through the 5 tasks. Do you agree with the priorities? Would you reorder them? This is your personal improvement backlog for the workshop.

---

### Step 8: Pick a Task and Fix It

Now for the fun part. You're going to let Copilot fix one of the tasks — but only with your permission.

**Pick the task that interests you most from your TASKS.md. Then ask Copilot:**

```
I want to work on Task [number]: [paste the task title].

Before making any changes:
1. Explain what you plan to do
2. List the files you'll modify
3. Describe any risks or side effects
4. Wait for my approval before proceeding
```

Review the plan. If it looks good, tell Copilot to go ahead:

```
Looks good. Go ahead and make the changes.
```

If you want to modify the approach:

```
I agree with steps 1 and 2, but for step 3, I'd rather
you [your alternative]. Proceed with that change.
```

After the changes are made, verify:
- Does the code still compile? (`mvn clean install`)
- Do the existing tests still pass? (`mvn test`)
- Does the app still start? (`mvn spring-boot:run`)

> **What you just did:** You followed the Plan → Review → Execute workflow that professional AI-augmented engineers use every day. You didn't blindly accept AI output. You reviewed the plan, gave permission, and verified the result.

If you have time, pick another task from your list and repeat.

---

### You're Done When...

- [ ] You've had a conversation with Copilot about the codebase
- [ ] You got a personalized response based on your role
- [ ] Copilot wrote you setup instructions
- [ ] The application is running on localhost:8080
- [ ] You can hit `/api/accounts` and see data
- [ ] You have a `.scratch/` folder with OVERVIEW.md, ARCHITECTURE.md, and MY-NOTES.md
- [ ] You have a TASKS.md with 5 improvement tasks
- [ ] You've completed at least one task with the Plan → Review → Execute workflow

If you finished early — try these bonus prompts:

```
What would happen if I transferred $60,000 between two accounts?
```

```
#codebase Which service handles fraud detection and what rules does it check?
```

```
Based on .scratch/TASKS.md, which two tasks would give the biggest
improvement for the least effort? Explain your reasoning.
```

---

*This exercise teaches you three things: (1) When you don't know something, ask Copilot first. (2) Build a personal knowledge base that makes every future conversation smarter. (3) Always review AI plans before letting them execute. These habits will serve you for the rest of this workshop — and beyond.*
