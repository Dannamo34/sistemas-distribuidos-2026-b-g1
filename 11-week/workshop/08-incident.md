# CP9 — Incident Drill and Postmortem

## 1. Incident Summary

**Incident:** Reservation API latency and availability degradation.

**System:** Futbolix.

**Severity:** High.

**Status:** Simulated incident for the distributed systems workshop.

**Affected components:** `api-gateway`, `reservation-service`, `payment-service`, and the `ReservationCreated` event flow.

**Scenario:** A dependency called by `reservation-service` takes three seconds to respond for 25% of requests over a 40-minute period. This causes slow reservation requests and increases the risk of exhausting the reservation availability error budget.

This is a simulation, not a report of an incident observed in a running production system.

## 2. Critical Journey and Trace

The critical journey is the creation of a reservation followed by payment initiation.

```text
Client
  |
  v
api-gateway
  |
  v
reservation-service
  |
  v
Slow dependency (3 seconds for 25% of requests)
  |
  v
ReservationCreated event
  |
  v
payment-service
  |
  v
Wompi Sandbox checkout
```
Example Slow Trace
The following trace illustrates the expected behavior for one affected request. Durations are illustrative and must be validated against actual instrumentation.
Span	Component	Duration
1	api-gateway request	3,100 ms
2	reservation-service processing	3,050 ms
3	Slow dependency call	3,000 ms
4	payment-service checkout initiation	100 ms

The spans overlap because the dependency call occurs inside the reservation processing span. Their durations must not be added together as if they were sequential.
3. Structured Log Examples
The following four log entries illustrate the expected structured log format. They are simulated examples, not logs collected from a running service.
{
  "timestamp": "2026-10-21T10:00:00-05:00",
  "level": "INFO",
  "service": "api-gateway",
  "trace_id": "trace-demo-001",
  "event": "request_received",
  "route": "/api/v1/reservations",
  "message": "Reservation request received"
}


{
  "timestamp": "2026-10-21T10:00:03-05:00",
  "level": "WARN",
  "service": "reservation-service",
  "trace_id": "trace-demo-001",
  "event": "dependency_slow",
  "duration_ms": 3000,
  "message": "Dependency exceeded expected response time"
}


{
  "timestamp": "2026-10-21T10:00:03-05:00",
  "level": "INFO",
  "service": "reservation-service",
  "trace_id": "trace-demo-001",
  "event": "reservation_processed",
  "duration_ms": 3050,
  "message": "Reservation processing completed"
}


{
  "timestamp": "2026-10-21T10:00:03-05:00",
  "level": "INFO",
  "service": "payment-service",
  "trace_id": "trace-demo-001",
  "event": "checkout_initiation_requested",
  "message": "Payment checkout initiation requested"
}


4. SLI Impact Calculation
Assumptions
- Incident duration: 40 minutes.
- Slow dependency response: 3 seconds.
- Affected requests: 25%.
- Reservation availability SLO: 99.5% over a rolling 30-day window.
- Estimated reservation traffic in CP6: 100 eligible requests per day.
- Estimated traffic during 40 minutes at that rate: approximately 2.78 requests.
- Expected affected requests at that rate: approximately 0.69 requests.
This traffic estimate is too low to model the incident precisely using whole requests. For the calculations below, a separate illustrative scenario of 60 eligible requests per minute is used. This is an exercise assumption, not measured Futbolix traffic.

At 60 requests per minute over 40 minutes:
- Total eligible requests: 60 × 40 = 2,400.
- Requests affected by the slow dependency: 25% × 2,400 = 600.
- Slow requests: 600 requests experience an additional three-second dependency delay.
Availability Impact
A slow response is not automatically a failed request. If all 2,400 requests succeed, the availability SLI remains 100%, although latency degrades significantly.

For this error-budget exercise, assume that 10% of affected requests ultimately fail:
- Failed requests: 10% × 600 = 60.
- Availability SLI: (2,400 − 60) / 2,400 × 100 = 97.5%.
- Error ratio: 60 / 2,400 × 100 = 2.5%.
This simulated 2.5% error ratio exceeds the 0.5% error ratio allowed by the 99.5% availability SLO.
Error Budget Impact
Assume 60 eligible requests per minute continuously throughout the 30-day SLO window:
- Total eligible requests: 60 × 60 × 24 × 30 = 2,592,000.
- Allowed failed requests: 0.5% × 2,592,000 = 12,960.
- Simulated failed requests during the incident: 60.
- Error budget consumed: 60 / 12,960 × 100 ≈ 0.46%.
The incident consumes approximately 0.46% of the request-based error budget under these assumptions.

The 40-minute incident duration is not automatically 40 minutes of availability downtime. Request-based error budget and time-based equivalent downtime are different measures.
5. Alert Evaluation
The CP7 rules define:
- Fast burn: burn rate greater than 14.4 over one hour, confirmed by a five-minute window.
- Slow burn: burn rate greater than 6 over six hours, confirmed by a 30-minute window.
The simulated error ratio of 2.5% is below the fast-burn threshold of 7.2% and the slow-burn threshold of 3%. Therefore, the stated error ratio alone does not guarantee that either alert will fire.

If the observed error ratio rises above 7.2% and remains elevated, the fast-burn condition can be met first, subject to the rule's evaluation and pending duration. Otherwise, neither alert may fire, even while latency is degraded. A separate latency alert would be useful for this scenario.
6. Runbook Action
The first response is to inspect recent deployments, dependency logs, and trace durations. If the slow dependency is linked to a recent release or configuration change, pause further releases and restore the last known-good configuration when justified.

The team should then verify that dependency latency falls, reservation requests recover, and the ReservationCreated event and payment checkout flow continue working.

These steps follow the response procedure documented in 07-runbook.md.
7. Blameless Postmortem
Timeline
Time	Event
10:00	The simulated dependency begins responding in three seconds for 25% of requests.
10:05	The team observes increased reservation latency in the simulated traces.
10:10	The team investigates request logs and dependency response times.
10:15	The team identifies the dependency as the likely source of latency.
10:20	The team applies a simulated mitigation, such as restoring the last known-good configuration.
10:40	The simulated incident ends after dependency latency returns to normal.

Impact
The exercise models 2,400 eligible requests, 600 slow requests, and 60 failed requests. The resulting availability SLI is 97.5% for the simulated incident window. These values are scenario assumptions rather than production measurements.
Root Cause
The simulated root cause is a dependency whose response time increases to three seconds for 25% of requests. The extra delay propagates through reservation-service and affects the critical reservation journey.
Contributing Factors
- Dependency latency has not yet been verified through live metrics.
- A dedicated alert for dependency latency has not been demonstrated.
- End-to-end trace and log correlation must be validated across the services.
Corrective Actions
Action 1 — Add Dependency Latency Instrumentation
User story: As a developer, I want to measure dependency latency in reservation-service so that slow calls can be identified before they significantly affect reservations.

Acceptance criteria:
- A latency metric records dependency call duration.
- The metric uses bounded labels and does not include customer identifiers.
- An automated test verifies that the metric is recorded for successful and slow calls.
Action 2 — Add a Dependency Latency Alert
User story: As the DevOps engineer, I want an alert for sustained dependency latency so that the team can investigate degradation before it causes widespread failures.

Acceptance criteria:
- A Prometheus rule defines a documented latency threshold and evaluation window.
- The alert includes severity, team ownership, and a runbook reference.
- The rule passes promtool check rules after the metrics are implemented.
Action 3 — Validate Distributed Trace Correlation
User story: As the Tech Lead, I want requests to share a trace identifier across the gateway, reservation service, and payment service so that the team can identify where delays originate.

Acceptance criteria:
- A controlled test sends a request through the critical journey.
- The logs for participating services contain the same trace_id.
- The trace identifies the slow dependency span and its duration.
8. Limitations
This document is a simulated incident drill. The request volume, failure ratio, timestamps, and span durations are illustrative assumptions, not production telemetry. Futbolix's metrics, alerts, and trace correlation must be integrated and tested before the incident calculations can be compared with actual system behavior.