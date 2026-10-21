# CP5 — Service Level Objectives (SLOs)

## 1. Purpose

This document defines proposed Service Level Objectives (SLOs) for the critical Futbolix reservation and payment journey. SLOs establish measurable reliability targets and help the team evaluate whether the services meet user expectations.

**Implementation status:** These objectives are initial proposals. They have not been validated against production traffic or real service measurements.

## 2. Critical User Journey

The critical journey is creating a court reservation and initiating its payment.

1. The client sends a reservation request through the API Gateway.
2. The Reservation Service validates and processes the request.
3. The reservation flow is intended to notify the Payment Service through a `ReservationCreated` event.
4. The Payment Service initiates a Wompi Sandbox checkout.

The journey depends on the correct behavior of the involved services and their communication paths.

## 3. Proposed SLOs

| SLO | Service or Journey | Target | Measurement |
|---|---|---|---|
| Availability | Reservation API | 99.5% successful eligible requests per 30-day window | Successful eligible requests divided by total eligible requests |
| Latency | Reservation API | 95% of eligible requests complete within 1 second | 95th percentile of request duration |
| Availability | Payment checkout initiation | 99.0% successful technical checkout initiation attempts per 30-day window | Successful technical initiations divided by eligible initiation attempts |
| Latency | Payment checkout initiation | 95% of eligible requests complete within 2 seconds | 95th percentile of request duration |

These targets are provisional and should be reviewed after collecting real measurements and agreeing on service expectations.

## 4. Definitions and Exclusions

### 4.1 Successful Requests

A successful request is one that completes according to the endpoint's technical contract.

Expected business outcomes, such as a rejected reservation because a slot is unavailable, must be distinguished from unexpected technical failures. They should not automatically be counted as service errors.

### 4.2 Availability

Availability is calculated as:

`Availability = (Successful eligible requests / Total eligible requests) × 100`

The team must define eligible requests consistently and exclude invalid monitoring traffic where appropriate.

### 4.3 Latency

Latency measures the elapsed time between receiving a request and completing its response.

The p95 target means that 95% of eligible requests should complete within the specified duration during the measurement window.

### 4.4 Payment Provider Dependencies

A technical failure from Wompi Sandbox may affect checkout initiation. Provider errors, timeouts, and application errors should be recorded separately to help identify the responsible component.

A successful checkout initiation does not necessarily mean that a payment has been approved.

## 5. Error Budget

The error budget represents the proportion of eligible requests that may fail while the service remains within its availability SLO.

For an availability SLO of 99.5%, the error budget is 0.5% of eligible requests.

For an availability SLO of 99.0%, the error budget is 1.0% of eligible requests.

The team should review error-budget consumption over the same 30-day window used to evaluate availability.

## 6. Monitoring Requirements

To evaluate these SLOs, the implementation should collect:

- Total eligible HTTP requests.
- Successful and failed technical requests.
- Request duration observations.
- Endpoint and service identifiers using bounded labels.
- Relevant dependency errors and timeouts.

Metrics should not use trace identifiers, reservation codes, customer identifiers, or payment identifiers as labels.

## 7. Review and Response

If an SLO is breached or its error budget is being consumed unusually quickly, the team should:

1. Check service dashboards and structured logs.
2. Use trace identifiers to investigate the affected requests when distributed tracing is available.
3. Identify whether the problem originates in the gateway, reservation service, payment service, or an external dependency.
4. Prioritize reliability fixes before introducing additional changes when necessary.
5. Document the incident, cause, and corrective actions.

## 8. Current Limitations

The current workshop implementation contains standalone Java utilities for trace identifiers and structured logging. The proposed service metrics and SLO calculations have not yet been integrated into running Futbolix APIs or validated using real traffic.

Therefore, the targets in this document are design objectives, not claims about current service performance.

## 9. Acceptance Criteria

- The critical user journey is identified.
- Availability and latency SLOs have explicit targets and measurement windows.
- Successful technical outcomes are distinguished from expected business rejections.
- Error budgets are defined.
- Required metrics and review actions are documented.
- Unverified targets are clearly identified as provisional.

**Status:** SLO documentation drafted; instrumentation and measurement validation pending.