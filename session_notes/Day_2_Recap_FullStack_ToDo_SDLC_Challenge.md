# Full-Stack Challenge: From Requirement to Running App

## The Idea

```
I want a simple To-Do application where users can add tasks with
a title and optional description, mark tasks as complete, delete
tasks, and filter the list by status (all, active, completed).
The app should have a backend API with a database and a frontend
web interface.
```

That's it. One paragraph. Let's turn it into a running application.

---

## Step 1: Create User Stories (5 minutes)

**Ask Copilot:**

```
Break this requirement into user stories using
"As a [user], I want [action] so that [benefit]" format.

"I want a simple To-Do application where users can add tasks
with a title and optional description, mark tasks as complete,
delete tasks, and filter the list by status (all, active,
completed). The app should have a backend API with a database
and a frontend web interface."

Include stories for:
- End user functionality
- Backend/API needs
- Frontend/UI needs
- Testing needs

Also list any ambiguities or missing requirements.

Save to `.scratch/USER-STORIES.md`.
```


---

## Step 2: Arrange into Sprints (3 minutes)

**Ask Copilot:**

```
Take these user stories and organize them into sprints.
Each sprint should be small enough to demo and test
before moving to the next one.

Sprint 1: Basic backend — entity, API, seed data
Sprint 2: Frontend — UI that connects to the API
Sprint 3: Full features — complete CRUD, filtering, polish
Sprint 4: Testing & documentation

For each sprint, list the user stories it covers and
how to verify it works.

Save to `.scratch/SPRINT-PLAN.md`.
```



---

## Step 3: Execute Sprint by Sprint

Your trainer will walk you through each sprint on screen share. Follow along.

---

### Sprint 1: Backend API

**Ask Copilot (Agent mode):**

```
Execute Sprint 1 from .scratch/SPRINT-PLAN.md.

```

**If it fails:** paste the error into Copilot Chat and ask it to fix it.

---

**Repeat for remaining sprints**

---

## The Moment

Look at what just happened:

```
Input:    One paragraph
Output:   User stories → Sprint plan → Backend API → Frontend UI
          → Full CRUD → Filtering → Validation → Tests → Docs

Time:     ~30-45 minutes
AI role:  Generated everything
Your role: Made every decision
```

**How long would this take without AI?**

---

## Bonus (If You Finish Early)

```
Add a "due date" field. Show overdue tasks in red.
Add a "Due Today" filter.
```

```
Add user authentication. Each user sees only their own tasks.
```

```
Using the same process, build an Expense Tracker:
- Add expenses with amount, category, date
- View in a table with total
- Filter by category and date range
```

---

*From one sentence to a running full-stack app. That's AI-augmented engineering.*
