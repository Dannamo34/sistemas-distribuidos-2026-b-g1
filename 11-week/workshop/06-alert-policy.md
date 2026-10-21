
# CP7 — Alert Policy and Error Budget Consumption

## 1. Purpose

This policy defines two burn-rate alerts for the Futbolix reservation availability SLO. The target is 99.5% availability over a rolling 30-day window, with an error budget of 0.5%.

The alerts monitor the `reservation-service` endpoint `POST /api/v1/reservations`, which is part of the critical journey through `api-gateway`, `reservation-service`, and the `ReservationCreated` event toward `payment-service`.

## 2. Fast-Burn Alert

**Alert:** `ReservationAvailabilityFastBurn`

- Burn rate threshold: 14.4.
- Long window: 1 hour.
- Short confirmation window: 5 minutes.
- Pending duration: 5 minutes.
- Severity: `critical`.

A burn rate of 14.4 consumes approximately 2% of a 30-day error budget in one hour if the rate persists. The short window confirms that the failure rate is also currently elevated.

## 3. Slow-Burn Alert

**Alert:** `ReservationAvailabilitySlowBurn`

- Burn rate threshold: 6.
- Long window: 6 hours.
- Short confirmation window: 30 minutes.
- Pending duration: 30 minutes.
- Severity: `warning`.

A burn rate of 6 consumes approximately 5% of a 30-day error budget over six hours if the rate persists. The short window helps confirm that the problem has not already subsided.

## 4. Why These Alerts Matter

The reservation availability error budget is 0.5%. The Prometheus expressions compare the observed technical failure ratio with the corresponding burn-rate threshold multiplied by `0.005`.

The fast alert is intended to detect severe, rapidly growing failures. The slow alert is intended to detect sustained degradation that could consume the budget over time.

## 5. Alerts Not Created

### CPU usage above a fixed threshold

High CPU alone does not prove that the reservation availability SLO is being violated. CPU should be investigated as a possible cause when request latency or failures also increase.

### RabbitMQ queue depth above a fixed threshold

A growing queue may indicate delayed processing of `ReservationCreated`, but queue depth alone does not establish that the reservation availability SLO is burning. A separate event-processing SLO would be needed for a dedicated alert.

## 6. Implementation Limitations

The metrics in `04-metrics.md` are proposed instrumentation requirements and have not yet been integrated into running Futbolix APIs. The rules therefore describe the intended alert behavior and must be validated after metric names and labels are implemented.

Before production use, validate the rules with `promtool check rules`, confirm that `outcome="failure"` is emitted consistently, and test both alerts using representative metric data.

**Status:** Alert rules and policy documented; runtime validation pending.
