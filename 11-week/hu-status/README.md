<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       11-week/hu-status/README.md (inside YOUR fork). English. -->

# Weekly Status - Week 11

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Danna Michelle Morales Losada
- GITHUB_USER: Dannamo34
- TEAM: Futbolix
- SPRINT_GOAL: Document and plan observability for the reservation and payment journey, including SLOs, error budgets, burn-rate alerts, incident response, and postmortem analysis.
<!-- CONFIG-END -->

## 1. User stories worked this week

| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| HU-XXX-001 | Define service level objectives and error budgets | done | https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/commit/ef96d65 |
| HU-XXX-002 | Document Prometheus burn-rate alert rules and alert policy | doing | https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/tree/main/11-week/workshop |
| HU-XXX-003 | Write the reservation availability incident runbook | doing | https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/tree/main/11-week/workshop |
| HU-XXX-004 | Simulate an incident and document the postmortem | doing | https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/tree/main/11-week/workshop |
| HU-XXX-005 | Complete the Week 11 delivery status and implementation plan | doing | https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/tree/main/11-week/hu-status |

## 2. My individual contribution

- Defined three service level objectives covering reservation availability, reservation latency, and payment checkout initiation.
- Documented the corresponding service level indicators, PromQL examples, estimated traffic assumptions, and error-budget policies.
- Prepared Prometheus fast-burn and slow-burn alert rules and documented their intended behavior and implementation limitations.
- Wrote an incident response runbook covering incident confirmation, initial mitigations, escalation, and recovery verification.
- Prepared a simulated incident postmortem involving `api-gateway`, `reservation-service`, `payment-service`, and the `ReservationCreated` event flow.
- Documented corrective actions for dependency latency instrumentation, alerting, and distributed trace correlation.
- Distinguished documented designs and simulated results from functionality that still requires implementation and runtime validation.

## 3. Blockers and risks

- The proposed SLO metrics and Prometheus alert rules have not yet been validated against live Futbolix services.
- Distributed trace correlation and structured logging must be verified across the gateway, reservation service, and payment service.
- Traffic volumes and incident calculations are based on explicit exercise assumptions rather than measured production traffic.
- The alert rules require validation with `promtool` after the corresponding metrics and labels are implemented.
- The final status of each checkpoint depends on verifying its separate commit and push.

## 4. Plan for next week

- Integrate and test the required request, latency, failure, and dependency metrics in the relevant services.
- Validate the propagation of `trace_id` and structured logs across the critical reservation and payment journey.
- Validate the Prometheus alert rules and test the runbook with representative scenarios.
- Replace illustrative traffic assumptions with measured data when an observable test environment becomes available.
- Review the corrective actions and acceptance criteria with the Futbolix team.

## 5. Compliance self-check

- [x] Conventional Commits - `type(scope): summary`
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...)
- [x] Testable acceptance criteria documented for proposed corrective actions
- [ ] Tests added/updated (unit / integration)
- [x] DDD / hexagonal boundaries considered in the implementation plan
- [x] No secrets included; runtime configuration should use environment variables

## 6. Evidence links

- Fork repository: https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1
- Week 11 workshop: https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/tree/main/11-week/workshop
- SLOs and error budget commit: https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/commit/ef96d65
- Additional checkpoint commits: to be added after verifying the actual published commit URLs.
- Reflection document: add the actual Google Docs URL before submission.