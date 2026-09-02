 ```csharp
# Corte 1 — Summary
## Unit 2: Containerization with Docker + Release MVP 1

---

# Session 1 — Containerization with Docker

## 1. Main Objective

The goal of containerization is to make distributed systems run consistently on different machines and environments.

Docker packages a service with everything it needs:

- Runtime
- Libraries
- Dependencies
- Configuration
- Application

This avoids the problem of:

> "It works on my machine."

The same containerized artifact can run in:

**Development → QA → Production**

---

# 2. Image vs Container vs Registry

These three concepts are fundamental in Docker.

### Dockerfile

A **Dockerfile** is the recipe that describes how to build an image.

### Image

An **image** is an immutable template containing the application and everything required to run it.

### Container

A **container** is a running instance of an image.

### Registry

A **registry** stores Docker images so they can be downloaded and used in different environments.

Examples:

- Docker Hub
- GitHub Container Registry (GHCR)

### Simple flow

Dockerfile
   ↓
Build
   ↓
Image
   ↓
Run
   ↓
Container

---

# 3. Good Dockerfile

A good Dockerfile should create a small, secure and efficient image.

The recommended approach is a **multi-stage build**.

## Multi-stage build

It uses:

### Stage 1 — Build

Contains:

- Maven
- JDK
- Dependencies
- Source code
- Build tools

Its purpose is to compile/package the application.

### Stage 2 — Runtime

Contains only what is necessary to run the application.

For example:

- JRE
- Application JAR

This makes the final image:

- Smaller
- Faster
- Safer
- Easier to deploy

### Example

FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src
RUN mvn -q clean package -DskipTests

FROM eclipse-temurin:21-jre

COPY --from=build /app/target/app.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]

---

# 4. Dockerfile Layer Caching

Docker builds images in layers.

Therefore, the Dockerfile should be organized from:

**Least changing → Most changing**

For example:

1. Copy pom.xml
2. Download dependencies
3. Copy source code
4. Build application

This allows Docker to reuse cached layers and makes builds faster.

---

# 5. Docker Compose

A distributed system usually contains several services.

For example:

- User Service
- Court Service
- Reservation Service
- Payment Service
- PostgreSQL
- RabbitMQ

Starting each container manually would be inconvenient.

**Docker Compose** allows us to define and run all services together.

The configuration is normally stored in:

`docker-compose.yml`

Docker Compose manages:

- Multiple containers
- Networks
- Dependencies
- Environment variables
- Volumes
- Service configuration

### Basic architecture

                    Docker Compose
                         |
        ---------------------------------
        |        |         |             |
      User     Court   Reservation    Payment
     Service   Service    Service      Service
        |        |         |             |
        ---------------------------------
                         |
                    PostgreSQL

All services can communicate through the same Docker network.

---

# 6. Communication Between Services

Services in Docker Compose should communicate using their **service names**.

Example:

http://inventory-api:8080

Do NOT depend on hard-coded IP addresses.

### Correct

http://reservation-service:8080

### Incorrect

http://192.168.1.25:8080

Docker Compose provides service discovery through the shared network.

---

# 7. Configuration with Environment Variables

Configuration should NOT be hard-coded inside the image.

Examples of configuration:

- Database URL
- Database username
- Database password
- Service URLs
- Secrets

These should be provided through **environment variables**.

Example:

DB_URL
DB_USERNAME
DB_PASSWORD

### Why?

Because the same image can be used in different environments.

For example:

Development:
DB_URL = development database

QA:
DB_URL = QA database

Production:
DB_URL = production database

The image does not need to change.

---

# 8. Data and Volumes

Containers are **disposable**.

If a database stores its data only inside the container, the data may be lost when the container is removed.

Therefore, persistent database data should be stored in a **Docker volume**.

### Correct

PostgreSQL
    ↓
Docker Volume
    ↓
Persistent Data

### Important

**Container = temporary**

**Volume = persistent data**

---

# 9. Secrets Must Not Be Inside Images

Never copy sensitive information into the Docker image.

For example:

- `.env`
- Passwords
- API keys
- Tokens
- Credentials

If a secret is copied into an image layer, someone may be able to extract it.

Configuration and secrets should be provided at runtime.

---

# 10. .dockerignore

A `.dockerignore` file prevents unnecessary or sensitive files from being copied into the Docker build context.

Typical exclusions:

.env
.git
node_modules
target
logs
temporary files

Example:

.env
.git
node_modules
target

This helps make images:

- Smaller
- Faster
- Safer

---

# 11. Real-Life Scenario

### Problem

A team uses:

COPY . .

and copies:

- `.env`
- `.git`
- Source code
- Build tools
- Dependencies
- Temporary files

into a single-stage image.

The result can be:

- Very large image
- Slow deployment
- Secret leakage
- Unnecessary files
- Poor security

### Solution

Use:

**Multi-stage Dockerfile**
+
**.dockerignore**
+
**Environment variables**
+
**Docker volumes**

The final image should contain only what is required to run the application.

---

# 12. Common Docker Mistakes

### Mistake 1

Using a single-stage image containing the entire build toolchain.

### Mistake 2

Copying `.env` into the image.

### Mistake 3

Storing database data only inside the container.

### Mistake 4

Using hard-coded IP addresses between services.

### Mistake 5

Hard-coding configuration inside the image.

---

# 13. Self-Check Answers

### Question 1

An image versus a container is:

**Image = immutable template**
**Container = running instance of the image**

### Question 2

A multi-stage Dockerfile is used to:

**Build in one stage and ship a small runtime image in another.**

### Question 3

Configuration should be provided through:

**Environment variables at runtime.**

### Question 4

Persistent database data belongs in:

**A Docker volume.**

### Question 5

Docker Compose services communicate using:

**Service names on the shared network.**

Example:

http://inventory-api:8080

### Question 6

Copying `.env` and the whole toolchain into a single-stage image:

**Makes the image large and can leak secrets.**

The solution is:

**Multi-stage build + .dockerignore + runtime configuration.**

---

# Session 2 — Release: Shipping MVP 1

## 1. Main Objective

The objective of this session is to release **MVP 1** correctly.

A release is not just a presentation or a collection of code.

A release is:

> A versioned, running increment of the product that meets its acceptance criteria and can be deployed by someone else.

The software must actually work.

---

# 2. What Is an MVP Release?

An MVP is a **Minimum Viable Product**.

However:

**MVP reduces scope, NOT quality standards.**

This means that the MVP can have fewer features, but the implemented features must still work correctly.

A valid MVP should:

- Run successfully
- Meet acceptance criteria
- Have tests
- Connect to the real database
- Run using Docker
- Handle the happy path
- Handle important error paths
- Have updated documentation
- Have a version tag

---

# 3. Promotion Through Environments

The increment moves through different stages.

Typical flow:

HU branch
   ↓
develop
   ↓
qa
   ↓
main
   ↓
version tag
   ↓
Release

For example:

`hu-xxx-dev`

      ↓

`develop`

      ↓

`qa`

      ↓

`main`

      ↓

`v1.0.0`

The final tag identifies the released version.

---

# 4. Git Branches in the Release Process

## HU Branch

Each User Story can be developed in its own branch.

Example:

`hu-001-dev`

## Develop

Integrates the completed HUs.

Example:

`develop`

## QA

The system is validated and tested.

Example:

`qa`

## Main

Contains the released version.

Example:

`main`

---

# 5. Version Tag

A release should have a version tag.

Example:

`v1.0.0`

The tag identifies the exact version that was released.

This makes the release:

- Reproducible
- Identifiable
- Traceable

The containers built from that version are the ones that should run.

---

# 6. Release Checklist

Before declaring MVP 1 released, verify the following:

### Acceptance Criteria

All committed acceptance criteria must be completed.

### Tests

Unit tests and integration tests must be green.

### Coverage

Coverage must meet the previously declared minimum.

### Docker

The complete system must run with:

`docker compose up`

### Real Database

Services must connect to a real database.

The system should not start with blank or broken database configuration.

### Happy Path

The main successful flow must work.

Example:

Create reservation
   ↓
Validate availability
   ↓
Save reservation
   ↓
Return successful response

### Error Path

Important errors must also work correctly.

Example:

Out of stock
   ↓
HTTP 409 Conflict

### Security

There must be:

- No secrets in the repository
- No secrets inside Docker images
- Configuration through environment variables

### Documentation

Update:

- README
- ADRs
- CHANGELOG

### Version

Create the release tag:

`v1.0.0`

---

# 7. Definition of Done

The **Definition of Done (DoD)** describes when a piece of work is truly finished.

For MVP 1, the DoD should include:

- Acceptance criteria completed
- Unit tests passing
- Integration tests passing
- Service running correctly
- Docker Compose working
- Real database connection
- Happy path working
- Important error path working
- No secrets exposed
- Documentation updated
- Version tagged
- CHANGELOG updated

### Important

Compiling is NOT enough.

The service must actually work at runtime.

---

# 8. Demo Working Software

The final demo should show the **running system**, not only slides.

The demo should prove:

1. The service starts.
2. The API works.
3. Data is persisted.
4. The main flow works.
5. Important errors are handled.
6. The sprint goal was achieved.

Example:

Client
   ↓
API
   ↓
Use Case
   ↓
Database
   ↓
Response

The team should also be able to explain:

- What was built
- Why it was built
- Why the architecture was designed that way
- What ADR supports the architectural decision
- What comes next

---

# 9. Retrospective

After the release, the team performs a **retrospective**.

The retrospective asks:

### What went well?

Things that worked successfully.

### What hurt?

Problems, blockers or difficulties.

### What should we change?

Concrete improvements for the next cycle.

The objective is to improve the team's process.

---

# 10. Backlog for the Next Corte

Not everything has to be completed in MVP 1.

Unfinished:

- Should
- Could

stories can move to the next backlog.

However, they should be:

**Re-estimated**

and not simply moved without review.

Example:

MVP 1
   ↓
Finished Must
   ↓
Unfinished Should/Could
   ↓
Corte 2 Backlog
   ↓
Re-estimate
   ↓
Plan next sprint

---

# 11. Scope Control

The team must control the scope of the MVP.

New features should not automatically be added at the end of the sprint.

Example:

Sprint already planned:

- Reservations
- Availability
- Payments

New request:

- Promotions

Instead of adding it immediately:

Promotions
   ↓
Backlog
   ↓
Re-plan
   ↓
Future Sprint

This prevents uncontrolled scope growth.

---

# 12. How MVP 1 Is Evaluated

MVP 1 is evaluated using more than functionality.

The important areas are:

### 1. Functionality / Demo

Does the system meet the sprint goal?

### 2. Code Quality

Does the code follow good practices?

Examples:

- Clean Code
- Tests
- Maintainability

### 3. Architecture

Does the implementation respect the defined architecture?

For example:

- DDD
- Hexagonal Architecture
- Clear boundaries
- Separation of responsibilities

### 4. Scrum Compliance

Did the team follow the Scrum process?

Examples:

- Backlog
- User Stories
- HUs
- Sprint
- Ceremonies
- Evidence

---

# 13. Individual Evidence

Each team member should maintain their weekly evidence.

Example:

`NN-week/hu-status/`

This evidence contributes to the individual evaluation.

It should show the work completed during the sprint/corte.

---

# 14. Common Release Mistakes

### Mistake 1

Calling the project "released" when tests are failing.

### Mistake 2

Calling it released when the service does not start.

### Mistake 3

Showing only slides instead of working software.

### Mistake 4

Not creating a version tag.

### Mistake 5

Not updating the CHANGELOG.

### Mistake 6

Skipping the retrospective.

### Mistake 7

Moving unfinished work to the next sprint without re-estimating it.

---

# 15. Self-Check Answers

### Question 1

A release (MVP) is:

**A versioned, running increment that meets its acceptance criteria.**

### Question 2

The release is marked by:

**Promoting to main and tagging a version such as v1.0.0.**

### Question 3

If tests are red or the service will not start:

**It is NOT released. It is still a draft.**

### Question 4

A good demo shows:

**The running system completing the sprint goal, including an important error path.**

### Question 5

Unfinished Should/Could stories:

**Roll into the next backlog and are re-estimated.**

### Question 6

Besides functionality, MVP evaluation includes:

**Code quality, architecture compliance and Scrum compliance.**

---

# 16. Relationship Between the Two Sessions

The two sessions are connected.

## Session 1 — Build the Runtime

Docker allows the services to run consistently.

Main concepts:

Dockerfile
   ↓
Image
   ↓
Container
   ↓
Docker Compose
   ↓
Multiple Services
   ↓
Real Database
   ↓
Running System

## Session 2 — Release the Product

Once the system can run, it must be validated and released.

Running System
   ↓
Tests
   ↓
Acceptance Criteria
   ↓
Definition of Done
   ↓
QA
   ↓
Main
   ↓
v1.0.0
   ↓
MVP 1 Release
   ↓
Demo
   ↓
Retrospective
   ↓
Corte 2

---

# 17. Final Concept Map

## SESSION 1 — CONTAINERIZATION

Docker
│
├── Dockerfile
│   └── Recipe
│
├── Image
│   └── Immutable template
│
├── Container
│   └── Running instance
│
├── Registry
│   └── Stores images
│
├── Docker Compose
│   └── Runs multiple services
│
├── Environment Variables
│   └── Configuration
│
├── Volumes
│   └── Persistent data
│
├── .dockerignore
│   └── Excludes unnecessary/sensitive files
│
└── Multi-stage Build
    └── Small and secure runtime image


## SESSION 2 — RELEASE MVP 1

MVP 1
│
├── Develop
│
├── QA
│
├── Main
│
├── Version Tag
│   └── v1.0.0
│
├── Definition of Done
│   ├── Acceptance Criteria
│   ├── Tests
│   ├── Docker
│   ├── Real DB
│   ├── Happy Path
│   ├── Error Path
│   ├── Documentation
│   └── No Secrets
│
├── Demo
│   └── Working Software
│
├── Retrospective
│   ├── What went well?
│   ├── What hurt?
│   └── What should change?
│
└── Corte 2
    └── Re-estimated Backlog


# 18. Ultra-Short Exam Summary

## Session 1

**Docker = reproducible runtime**

Dockerfile
→ Image
→ Container

Docker Compose
→ Multiple services

Environment Variables
→ Configuration

Volumes
→ Persistent data

Multi-stage
→ Small and secure image

.dockerignore
→ Avoid unnecessary files and secrets


## Session 2

**Release = versioned + running + validated software**

Develop
→ QA
→ Main
→ v1.0.0

Definition of Done
→ Acceptance Criteria + Tests + Docker + Real DB + Happy/Error Paths + Documentation

Demo
→ Show working software

Retrospective
→ Learn and improve

Unfinished Should/Could
→ Next backlog + re-estimation


# 19. Key Ideas to Memorize

### Docker

**Dockerfile = recipe**

**Image = template**

**Container = running instance**

**Registry = image storage**

**Volume = persistent data**

**Environment variables = runtime configuration**

**Docker Compose = multiple services together**

**Multi-stage = build separately, run with a smaller image**


### Release

**MVP reduces scope, not standards.**

**A release must actually work.**

**Red tests = not released.**

**Service does not start = not released.**

**Demo = working software, not slides.**

**v1.0.0 = identifies the release.**

**Retrospective = improve the next cycle.**

---

# FINAL MEMORY TRICK

## SESSION 1 = PACKAGE AND RUN

Dockerfile
→ Image
→ Container
→ Compose
→ Environment
→ Volume
→ Running System


## SESSION 2 = VALIDATE AND RELEASE

Develop
→ QA
→ Main
→ Tag
→ DoD
→ Demo
→ Retrospective
→ Corte 2


# KEY PHRASE

**Session 1 = MAKE IT RUN**

**Session 2 = PROVE IT WORKS AND RELEASE IT**
```
