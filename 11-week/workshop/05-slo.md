
# CP6 — Service Level Objectives and Error Budget

## 1. Purpose

This document defines three provisional Service Level Objectives (SLOs) for the Futbolix critical journey: creating a court reservation and initiating its payment.

The targets are design proposals. The current workshop metrics have not been integrated into running Futbolix APIs or validated with production traffic.

## 2. Critical User Journey

1. The `api-gateway` receives the reservation request.
2. The `reservation-service` validates and processes `POST /api/v1/reservations`.
3. The `ReservationCreated` event is intended to connect reservation creation with payment processing.
4. The `payment-service` initiates checkout through Wompi Sandbox using `POST /api/v1/payments/checkouts`.

The journey depends on the gateway, reservation processing, event delivery, and payment checkout initiation.

## 3. SLO Definitions

### SLO 1 — Reservation API availability

- **Service:** `reservation-service`
- **Endpoint:** `POST /api/v1/reservations`
- **SLI formula:** Successful eligible technical requests divided by all eligible technical requests.
- **Target:** At least 99.5% successful requests.
- **Window:** Rolling 30 days.
- **Good event:** A completed request with `outcome="success"`.
- **Exclusion:** Expected business rejections, such as an unavailable court slot, must not be counted as technical failures.

PromQL:

```promql
sum(increase(http_server_requests_total{service="reservation-service",route="/api/v1/reservations",outcome="success"}[30d]))
/
sum(increase(http_server_requests_total{service="reservation-service",route="/api/v1/reservations"}[30d]))
```

### SLO 2 — Reservation API latency

- **Service:** `reservation-service`
- **Endpoint:** `POST /api/v1/reservations`
- **SLI formula:** Eligible requests completed in one second or less divided by all eligible requests.
- **Target:** At least 95% of eligible requests complete within one second.
- **Window:** Rolling 30 days.

PromQL for the p95 latency:

```promql
histogram_quantile(
  0.95,
  sum by (le) (
    rate(http_server_request_duration_seconds_bucket{service="reservation-service",route="/api/v1/reservations"}[30d])
  )
)
```

The target is met when the resulting p95 is at most `1` second. The histogram must expose the `_bucket` series for this query to work.

### SLO 3 — Payment checkout initiation success

- **Service:** `payment-service`
- **Endpoint:** `POST /api/v1/payments/checkouts`
- **SLI formula:** Successful technical checkout initiations divided by all eligible checkout initiation attempts.
- **Target:** At least 99% successful technical initiations.
- **Window:** Rolling 30 days.
- **Exclusion:** A successful checkout initiation does not mean that Wompi has approved the payment.

PromQL:

```promql
sum(increase(payment_checkouts_total{service="payment-service",outcome="success"}[30d]))
/
sum(increase(payment_checkouts_total{service="payment-service"}[30d]))
```

These queries depend on the proposed metrics using the documented labels consistently. They must be tested after instrumentation is connected to the real services.

## 4. Traffic Estimate and Error Budget

Futbolix does not yet have verified production traffic measurements for this journey. For initial planning only, this document assumes 100 eligible reservation requests and 30 eligible checkout initiation attempts per day. These are estimates, not measured facts, and must be replaced with observed traffic.

For a 30-day window:

- Reservation requests: 100 × 30 = 3,000.
- Checkout initiation attempts: 30 × 30 = 900.

### Reservation availability budget

The 99.5% SLO permits a 0.5% error budget.

- Request budget: 3,000 × 0.005 = **15 failed eligible requests**.
- Time budget: 30 × 24 × 60 × 0.005 = **216 minutes**.

The time budget represents the equivalent of 216 minutes of complete unavailability in a 30-day window. Request failures and outage minutes are different measures; actual failures must be counted from the SLI.

### Reservation latency budget

The 95% latency target allows up to 5% of eligible requests to exceed one second.

- Slow-request budget: 3,000 × 0.05 = **150 requests above one second**.

This is a latency budget, not an availability outage budget.

### Payment checkout success budget

The 99% target permits a 1% error budget.

- Request budget: 900 × 0.01 = **9 failed eligible checkout initiations**.
- Time equivalent: 30 × 24 × 60 × 0.01 = **432 minutes**.

The 432-minute figure is an availability-style time equivalent, not a prediction that checkout failures cause full-system downtime.

## 5. Error Budget Policy

### 50% consumed

Review the affected SLI, inspect structured logs and trace context, and prioritize investigation of recurring failures. Continue planned work only if reliability is not deteriorating.

### 75% consumed

Prioritize reliability fixes over nonessential feature work. Review recent changes and dependencies involving `api-gateway`, `reservation-service`, `ReservationCreated`, and `payment-service`.

### 100% consumed

Freeze nonessential releases affecting the critical journey. Focus on restoring SLO compliance, verify recovery using the SLIs, and document corrective actions before resuming normal delivery.

## 6. Limitations and Validation

The workshop currently contains standalone Java utilities for trace context and structured logging. It does not yet expose the proposed HTTP metrics from running Futbolix APIs.

Before using these SLOs for operational decisions, the team must implement and test the metrics, verify the label values, validate the PromQL queries, and replace planning traffic assumptions with observed data.

## 7. Acceptance Criteria

- Three SLOs cover availability, latency, and payment checkout success.
- Each SLO has an SLI formula, PromQL query, target, and measurement window.
- Traffic estimates and request budgets are calculated transparently.
- Availability budgets include request and time equivalents.
- The policy defines actions at 50%, 75%, and 100% consumption.
- Unimplemented metrics and unverified assumptions are clearly identified.

**Status:** SLO proposal documented; metric integration and validation pending.
