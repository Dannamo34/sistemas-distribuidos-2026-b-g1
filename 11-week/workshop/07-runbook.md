# CP8 — Runbook for Reservation Availability Fast Burn

## 1. Purpose

This runbook explains how to respond when the `ReservationAvailabilityFastBurn` alert fires in Futbolix. The critical journey includes `api-gateway`, `reservation-service`, the `ReservationCreated` event, and `payment-service`.

## 2. Symptoms

- The reservation API returns more failed requests than expected.
- Reservation creation latency increases.
- Users may be unable to complete a reservation.
- The fast-burn alert reports rapid consumption of the availability error budget.

## 3. Confirm the Incident

First, verify that the alert is active in Prometheus and inspect the reservation API error ratio.

```promql
sum(rate(http_server_requests_total{service="reservation-service",route="/api/v1/reservations",outcome="failure"}[5m]))
/
sum(rate(http_server_requests_total{service="reservation-service",route="/api/v1/reservations"}[5m]))
Check the reservation request logs using the same trace_id to follow a request across services. Example query for a structured log platform:
trace_id="<TRACE_ID>" AND service IN ("api-gateway", "reservation-service", "payment-service")
```


The query syntax depends on the log platform. Futbolix still needs its logging and metrics integrated into the running services.
4. Initial Mitigations
Apply the least risky action first and verify the effect after each step.
1. Inspect recent changes: Check deployment history, configuration changes, and error logs. If a recent change clearly correlates with the incident, pause further releases.
2. Reduce additional impact: If a faulty instance or configuration is identified, remove it from traffic or restore the last known-good configuration, following the team's deployment procedure.
3. Rollback if necessary: If errors continue and a recent release is the likely cause, roll back that release using the approved process. Preserve logs and trace identifiers for investigation.
Do not disable authentication, remove reservation validation, or modify production data as an emergency shortcut.
5. Escalation
- Developer — Miguel Andres Gomez Gutierrez: Investigate application exceptions, reservation logic, and dependencies.
- DevOps — Edwin Melendez Palomino: Check deployment health, infrastructure, metrics, logs, and rollback execution.
- Tech Lead — Hernando Antonio Martin Herrera: Coordinate the response and approve higher-risk technical decisions.
- Product Owner — Danna Michelle Morales Losada: Assess user impact and coordinate product communication.
Escalate to the Tech Lead if the cause is unclear, the first mitigation does not reduce failures, or the incident requires a high-risk change.
6. Recovery Verification
The incident can be considered recovered when:
- The fast-burn alert clears and does not immediately return.
- The reservation API failure ratio returns below the configured alert threshold.
- Reservation creation succeeds in a controlled test.
- The ReservationCreated event and payment checkout flow behave as expected.
- Logs and traces show no continuing errors for the affected journey.
Record the incident timeline, affected requests, root cause, and follow-up actions in 08-incident.md.
7. Limitations
This runbook is a documented response procedure, not evidence of a live incident-management system. Futbolix's proposed metrics, alert rules, and trace-based log searches must be integrated and tested before this procedure can be executed against a running environment.