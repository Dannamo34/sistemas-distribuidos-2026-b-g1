# Week 3 — DDD, Hexagonal Architecture & Planning

## Corte 1

In this unit, I learned how to organize a distributed system so it is easier to maintain, change, and scale.

The main idea is to separate the business from the infrastructure, define clear responsibilities for each service, and establish how services communicate with each other.

---

### 1. Domain-Driven Design (DDD)

DDD focuses on making the **domain the center of the system**, meaning that business rules should live in the domain instead of being scattered across controllers, services, or databases.

It also uses a **Ubiquitous Language**, so the code and the team use the same business terminology.

#### Main elements

* **Entity:** has its own identity. Example: `Order`, `Customer`.
* **Value Object:** has no identity, is compared by value, and is usually immutable. Example: `Money`, `Address`.
* **Aggregate:** groups objects that need to remain consistent.
* **Aggregate Root:** controls the aggregate and protects its rules.
* **Domain Event:** represents something that already happened. Example: `OrderCreated`.

### Invariants

Business rules should be inside the domain.

```text
Order.confirm()
      ↓
Does it have items?
   ↙       ↘
 No         Yes
 ↓           ↓
Error     Confirm
```

> **The Aggregate Root is responsible for protecting its own rules.**

---

### 2. Hexagonal Architecture

Hexagonal Architecture separates the **domain from everything external**.

It is also known as **Ports & Adapters**.

```text
Adapters
   ↓
Application
   ↓
Domain
```

The main rule is:

> **Dependencies point toward the domain.**

The domain should not know whether we are using PostgreSQL, MongoDB, Spring, JPA, REST, etc.

#### Ports

Ports are interfaces that define what the domain needs.

```java
interface OrderRepository {
    Optional<Order> byId(OrderId id);
    void save(Order order);
}
```

Infrastructure implements the port.

#### Adapters

**Inbound:**

* REST Controller
* Message Consumer
* GraphQL
* CLI

**Outbound:**

* Database
* External APIs
* Message Broker
* External services

---

### 3. Clean Domain

The domain **must not depend on infrastructure**.

We should avoid things like:

```text
@Entity
JPA
Hibernate
Spring
HTTP
PostgreSQL
```

inside `domain/`.

This allows us to change technologies without changing the business rules.

> **The business should not depend on technology.**

---

### 4. Anemic Domain Model

An anemic domain model happens when entities only contain:

```text
data + getters + setters
```

while all the business logic is placed inside services.

**Bad:**

```text
Order
  → data

OrderService
  → all business rules
```

**Better:**

```text
Order
  → data
  → behavior
  → business rules
```

For example:

```text
order.confirm()
order.cancel()
order.addItem()
```

> **Behavior should stay together with the data it protects.**

---

### 5. Planning

After modeling the domain, Planning defines how to turn it into **real services**.

We need to decide:

* Who owns each piece of data.
* How services communicate.
* What contracts they provide.
* How to protect our domain from external systems.
* What functionality we will build first for the MVP.

---

### 6. Data Ownership

The main rule is:

> **Each piece of data must have one owning service.**

Example:

```text
Customer Service → Customer

Order Service → Order

Inventory Service → Stock
```

Other services should not directly access another service's database.

**Bad:**

```text
Order ──────┐
            ↓
       Customer DB
            ↑
Marketing ──┘
```

**Good:**

```text
Order
  ↓
Customer Contract
  ↓
Customer Service
```

This reduces coupling and prevents creating a **distributed monolith**.

---

### 7. Service Contracts

A contract defines **how services communicate**.

It should specify:

* Method / endpoint or event.
* Request.
* Response.
* Errors.
* Version.

Example:

```text
GET /api/v1/stock/{sku}
```

```json
{
  "sku": "A-1",
  "available": 42
}
```

> **Services should know the contract, not the internal implementation of another service.**

---

### 8. Sync vs Async

#### Synchronous

Used when we need an immediate response.

```text
Order
  ↓
"Is there stock?"
  ↓
Inventory
  ↓
"Yes"
```

Example: checking stock before creating an order.

#### Asynchronous

Used when we do not need an immediate response.

```text
Customer Service
      ↓
CustomerRegistered
      ↓
Marketing
```

Advantages:

* Less coupling.
* Greater resilience.
* More independent services.

---

### 9. Anti-Corruption Layer (ACL)

The **ACL** protects our domain when consuming an external or legacy system.

It works as a translator:

```text
External System
      ↓
     ACL
      ↓
Our Domain
```

Example:

```text
External:
customer_code
cust_name
cust_status

       ↓ ACL ↓

Our Domain:
CustomerId
CustomerName
CustomerStatus
```

> **The external model should not contaminate our domain.**

---

### 10. Vertical Slicing — MVP

For the MVP, we should build **complete features**, instead of working on entire technical layers separately.

**Horizontal approach:**

```text
Sprint 1 → All databases
Sprint 2 → All controllers
Sprint 3 → All services
```

**Vertical Slice:**

```text
API
 ↓
Application
 ↓
Domain
 ↓
Infrastructure
```

Example:

> **A customer can create an order for a product that is in stock.**

```text
POST /orders
      ↓
Application
      ↓
Order Aggregate
      ↓
Inventory
      ↓
Save Order
      ↓
201 Created
```

The functionality should be **small, complete, and demoable**.

---

## What I Need to Remember

### DDD

> **Models and protects the business.**

```text
Entity       → Identity
Value Object → Value
Aggregate    → Consistency
Root         → Protects rules
Event        → Something that happened
```

### Hexagonal Architecture

> **Protects the domain from infrastructure.**

```text
Adapters → Application → Domain
```

### Planning

> **Defines how services work and communicate.**

```text
Data Ownership → One owner per piece of data

Contracts      → How services communicate

Sync           → I need an immediate response

Async          → I do not need an immediate response

ACL            → Translates external models

Vertical Slice → Complete feature for the MVP
```

---

## Final Idea

> **DDD defines the business rules, Hexagonal Architecture protects them from infrastructure, and Planning turns those boundaries into services with clear data ownership, contracts, and responsibilities.**
