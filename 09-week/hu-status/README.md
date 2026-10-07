<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.

     Your weekly grade is read AUTOMATICALLY from this file:

       09-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 09

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Danna Michelle Morales Losada
- GITHUB_USER: dannamo34
- TEAM: Futbolix
- SPRINT_GOAL: Correct and complete the product and architecture documentation, keeping traceability with the project requirements and the agreed MVP scope.
<!-- CONFIG-END -->

## 1. User stories worked this week

| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| HU-RES-001 | View Field Availability - documentation traceability | done | Product backlog and architecture documentation |
| HU-RES-002 | Create Field Reservation - documentation traceability | done | Product backlog and architecture documentation |
| HU-PAY-001 | Complete Reservation Payment - documentation traceability | done | Product backlog and architecture documentation |
| HU-ADM-001 | Manage Courts, Schedules, Prices, and Status - documentation traceability | done | Product backlog and architecture documentation |

## 2. My individual contribution

- Corrected and completed the `03-product/product-backlog.md` documentation.
- Added the prioritized product backlog based on the project vision and the existing user stories.
- Organized the backlog according to MVP priorities and dependencies.
- Added traceability between the product backlog, product vision, and user stories.
- Corrected the documentation so that the backlog reflects the real Futbolix MVP scope, including the four fixed courts, public customer flow, administrator management, reservation process, and Wompi Sandbox payment.
- Worked on the `05-architecture` documentation and completed the missing architecture decisions and deployment documentation.
- Added `deployment.md` to document the deployment architecture.
- Added ADR-003 for the database-per-service decision.
- Added ADR-004 for the API Gateway decision.
- Added ADR-005 for reservation-payment consistency.
- Added ADR-006 for the Outbox Pattern.
- Added ADR-007 for reservation lookup.
- Kept the architecture aligned with the four business services: user-service, court-service, reservation-service, and payment-service, with the API Gateway treated as a gateway component and not as an additional business microservice.
- Used Conventional Commits for the architecture documentation changes.

## 3. Blockers and risks

- The product backlog file initially existed but was empty, so the PR did not contain a meaningful diff.
- The product backlog PR received review feedback requesting a real diff, a Conventional Commit title, and clearer traceability to the project requirements.
- The product backlog correction is being completed before the final commit and PR update.
- Care was required to keep the architecture decisions consistent with the approved Futbolix MVP scope and avoid documenting features that are outside the current MVP.

## 4. Plan for next week

- Finish and push the corrected `03-product/product-backlog.md`.
- Update the corresponding PR with the new documentation changes and traceability information.
- Continue reviewing the project documentation for consistency between product, requirements, architecture, and DevOps sections.
- Verify that the documented architecture continues to match the actual project scope and implementation status.
- Continue working on the next assigned Futbolix documentation tasks.

## 5. Compliance self-check

- [x] Conventional Commits - `type(scope): summary`
- [x] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...)
- [x] Testable acceptance criteria
- [ ] Tests added/updated (unit / integration) - This week's work was documentation-focused.
- [x] DDD / hexagonal boundaries respected (domain has no I/O)
- [x] No secrets; config via environment variables

## 6. Evidence links

- `05-architecture` deployment and ADR-003/ADR-004 commit: `4d83342` — `docs: add deployment architecture and ADRs`
- `05-architecture` ADR-005/ADR-006/ADR-007 commit: `e9e2651` — `docs: add reservation architecture decisions`
- `03-product/product-backlog.md` — corrected product backlog with prioritization and traceability to `03-product/vision.md` and `04-requirements/user-stories.md`
- PR #36 — `docs: update product backlog` — product backlog correction and review feedback addressed