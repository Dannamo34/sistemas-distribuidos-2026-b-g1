# CP1 — Attack Surface Map

## 1. System overview

Futbolix is a court reservation platform. This map identifies its main components, data flows, assets and trust boundaries. Security controls must be verified against the current implementation before they are described as active.

## 2. Data-flow diagram

```mermaid
flowchart LR
    CUSTOMER[Customer]
    ADMIN[Administrator]
    FRONT[ftx-front<br/>React + Vite]
    GW[api-gateway<br/>port 8080]

    USER[user-service<br/>port 8081]
    COURT[court-service<br/>port 8082]
    RES[reservation-service<br/>port 8083]
    PAY[payment-service<br/>port 8084]

    PGUSER[(PostgreSQL<br/>user schema)]
    PGCOURT[(PostgreSQL<br/>court schema)]
    PGRES[(PostgreSQL<br/>reservation schema)]
    PGPAY[(PostgreSQL<br/>payment schema)]

    WOMPI[Wompi Sandbox<br/>external API]
    MQ[[RabbitMQ<br/>planned async messaging]]
    WORKER[Notification worker<br/>planned]
    MONGO[(MongoDB<br/>notifications)]

    CUSTOMER -->|Web requests| FRONT
    ADMIN -->|Admin requests| FRONT
    FRONT -->|API requests| GW

    GW --> USER
    GW --> COURT
    GW --> RES
    GW --> PAY

    USER --> PGUSER
    COURT --> PGCOURT
    RES -->|SQL reservation data| PGRES
    PAY --> PGPAY

    PAY -->|Checkout integration| WOMPI

    RES -.->|ReservationCreated| MQ
    MQ -.->|Payment processing flow| PAY
    PAY -.->|PaymentApproved / PaymentRejected| MQ
    MQ -.-> WORKER
    WORKER -.-> MONGO

    TB1{{TB1: Internet to application}}
    TB2{{TB2: Gateway to backend}}
    TB3{{TB3: reservation-service to PostgreSQL}}
    TB4{{TB4: payment-service to Wompi}}
    TB5{{TB5: Async messaging}}

    TB1 --- FRONT
    TB2 --- GW
    TB3 --- PGRES
    TB4 --- WOMPI
    TB5 --- MQ

    classDef selected fill:#ffe2a8,stroke:#9a5b00,color:#111;
    class RES,PGRES selected;
```
Claimed boundary: TB3 — reservation-service to PostgreSQL.
3. Trust boundaries
ID	Boundary	Security concern
TB1	Customer or administrator to the application	Requests from outside the system must be validated.
TB2	API gateway to backend services	Only permitted operations should reach each service.
TB3	reservation-service to PostgreSQL	Reservation records must be protected from unauthorized reading or modification.
TB4	payment-service to Wompi Sandbox	Checkout requests and payment results must be validated.
TB5	Services to the messaging system	Events must be protected against unauthorized publication or consumption.

4. Asset inventory
Asset	Location	Classification	Reason
Reservation details and status	reservation-service and its PostgreSQL schema	Confidential	Unauthorized changes could affect reservations.
Reservation lookup code	Reservation records	Confidential	It must not expose reservation information to unauthorized callers.
Administrator JWT	Admin client and authenticated requests	Restricted	It grants access to privileged operations.
Payment status and transaction references	payment-service and its PostgreSQL schema	Confidential	They affect the recorded payment result.
Wompi credentials	payment-service configuration or environment	Restricted	Credentials must not be exposed in code or logs.
Court and schedule availability	court-service and its PostgreSQL schema	Internal	It supports court availability and reservation decisions.
Notification data	MongoDB notification storage	Confidential	Notifications may contain reservation-related information.

5. Entry points for the selected boundary
Entry point	Caller	Authentication or control to verify
API gateway, port 8080	Frontend or external client	Verify public routes, authentication and request validation in the current gateway configuration.
POST /api/v1/reservations	Client through the API gateway	Verify validation, permitted access and how reservation data is handled.
Public reservation lookup by code	Customer	Confirm the exact route and verify that only the intended reservation information is returned.
Database connection from reservation-service	reservation-service	Verify database credentials, schema permissions and network access.

The exact public lookup route and the current authentication rules must be confirmed in the team API contract and implementation. This map does not assume that an unverified control already exists.
6. Security observations
- The selected boundary protects reservation data between reservation-service and PostgreSQL.
- The database account should have only the permissions required by the reservation service. Verify the real configuration before stating that this is enforced.
- The project uses Wompi Sandbox for payment integration.
- RabbitMQ is part of the planned asynchronous flow. Confirm which messaging components are implemented before treating that flow as active.
- The team architecture includes MongoDB for notifications. Verify the current deployment and worker before describing them as running.
- The next step is to analyze this boundary with STRIDE and prioritize its risks.