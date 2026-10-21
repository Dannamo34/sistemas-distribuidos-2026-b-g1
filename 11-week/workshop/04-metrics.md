# CP4 — Golden Signals and Metrics

## 1. Purpose

This document defines the four Golden Signals for monitoring the Futbolix reservation and payment journey: latency, traffic, errors, and saturation. These signals help identify performance problems, service failures, and resource constraints.

**Implementation status:** The metrics below are proposed instrumentation requirements. They must be connected to the actual services and verified before they can be considered implemented.

## 2. Critical Journey

The critical journey is creating a court reservation and initiating its payment.

1. The API Gateway receives the client request.
2. The Reservation Service validates and processes the reservation.
3. A `ReservationCreated` event is intended to notify the Payment Service.
4. The Payment Service initiates the Wompi Sandbox checkout.

Monitoring this journey helps identify where a request becomes slow or fails.

## 3. The Four Golden Signals

### 3.1 Latency

**Question:** How long does a request take to complete?

| Metric | Description | Unit |
|---|---|---|
| `http_server_request_duration_seconds` | HTTP request duration, measured with a histogram | Seconds |
| `reservation_creation_duration_seconds` | Time required to process a reservation creation request | Seconds |
| `payment_checkout_duration_seconds` | Time required to initiate a payment checkout | Seconds |

**Example:** If reservation creation becomes slower than usual, latency metrics can help identify a performance regression in the Reservation Service or a downstream dependency.

### 3.2 Traffic

**Question:** How much demand is the system receiving?

| Metric | Description | Unit |
|---|---|---|
| `http_server_requests_total` | Total HTTP requests received by a service | Requests |
| `reservations_created_total` | Total reservation creation attempts | Reservations |
| `payment_checkouts_total` | Total payment checkout initiation attempts | Checkouts |

**Example:** A sudden increase in requests can indicate higher demand and help the team decide whether additional capacity or investigation is needed.

### 3.3 Errors

**Question:** How many requests fail?

| Metric | Description | Unit |
|---|---|---|
| `http_server_errors_total` | Total HTTP requests that result in server errors | Errors |
| `reservation_failures_total` | Total reservation operations that fail | Failures |
| `payment_checkout_failures_total` | Total payment checkout initiation failures | Failures |

**Example:** An increase in checkout failures may indicate a problem with the payment integration or an unavailable dependency. Expected business rejections should be distinguished from technical failures.

### 3.4 Saturation

**Question:** How close are the services and their dependencies to their resource limits?

| Metric | Description | Unit |
|---|---|---|
| `process_cpu_usage_ratio` | Proportion of CPU capacity used by a process | Ratio, 0–1 |
| `process_memory_usage_bytes` | Memory used by a service process | Bytes |
| `http_server_active_requests` | Number of requests currently being processed | Requests |
| `rabbitmq_queue_messages_ready` | Number of messages waiting in a RabbitMQ queue | Messages |

**Example:** A growing RabbitMQ queue may indicate that consumers are processing events more slowly than they arrive. High CPU or memory usage may also affect response times.

The RabbitMQ metric applies when the broker and its monitoring integration are available.

## 4. Recommended Labels

Metrics should use a small, consistent set of labels, where applicable:

- `service`: service name, such as `api-gateway`, `reservation-service`, or `payment-service`.
- `method`: HTTP method, such as `GET` or `POST`.
- `route`: normalized route template, such as `/api/v1/reservations`.
- `status_code`: HTTP response status code.
- `outcome`: a bounded result category, such as `success`, `failure`, or `rejected`.

Do not use `trace_id`, reservation codes, customer identifiers, payment identifiers, or other unique values as metric labels. These create excessive metric cardinality. Trace identifiers belong in logs and distributed traces.

## 5. Initial Alerting Recommendations

The following are starting points for testing and tuning, not validated production thresholds.

| Signal | Initial condition | Purpose |
|---|---|---|
| Latency | Request p95 exceeds 1 second for 5 minutes | Detect slow requests |
| Traffic | Request rate changes significantly from its normal baseline | Detect unusual demand |
| Errors | Server error rate exceeds 5% for 5 minutes, with sufficient traffic | Detect service degradation |
| Saturation | CPU usage exceeds 85% for 10 minutes | Detect sustained resource pressure |
| Queue backlog | Queue depth grows continuously for 5 minutes | Detect delayed asynchronous processing |

Thresholds should be adjusted after observing real traffic and establishing a baseline. A traffic increase alone is not necessarily an incident.

## 6. Verification Plan

Before considering these metrics implemented, the team should:

1. Instrument HTTP request counts and request duration in a real service.
2. Record successful and failed operations separately.
3. Expose the metrics through the selected monitoring endpoint or exporter.
4. Generate test requests and verify that metric values change as expected.
5. Confirm that labels remain bounded and do not contain sensitive or unique identifiers.
6. Document actual test commands and results.

## 7. Current Limitations

The current CP3 Java files provide standalone trace-context and structured-logging utilities. They do not yet expose HTTP metrics, measure request duration in a running API, or integrate with a monitoring system.

Consequently, the metrics in this document are design proposals, not measured results. Integration and verification remain pending.

## 8. Acceptance Criteria

- The four Golden Signals are documented.
- Proposed metrics have clear meanings and units.
- Labels avoid high-cardinality and sensitive values.
- Initial alert conditions are documented as provisional.
- Implementation status and outstanding verification work are stated honestly.

**Status:** Documentation drafted; service instrumentation and metric verification pending.