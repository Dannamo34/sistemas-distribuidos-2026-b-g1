<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       13-week/hu-status/README.md (inside YOUR fork). English. -->

# Weekly Status - Week 13

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Danna Michelle Morales Losada
- GITHUB_USER: Dannamo34
- TEAM: Futbolix
- SPRINT_GOAL: Complete individual threat modeling, document the reservation-service to PostgreSQL trust boundary, create security backlog stories, and propose security checks for the project pipeline.
<!-- CONFIG-END -->

## 1. User stories worked this week

| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| HU-SEC-001 | Analyze threats at the reservation-service to PostgreSQL trust boundary | doing | https://github.com/code-corhuila/ftx-docs/pull/63 |
| HU-SEC-002 | Identify and prioritize security risks using STRIDE | doing | https://github.com/code-corhuila/ftx-docs/pull/63 |
| HU-SEC-003 | Create security stories and define testable acceptance criteria | doing | https://github.com/code-corhuila/ftx-docs/issues/64 |
| HU-SEC-004 | Define security controls for the CI pipeline and negative authorization testing | doing | https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/tree/main/13-week/workshop/04-security-ci |

## 2. My individual contribution

- Created the Week 13 workshop documentation in my personal fork.
- Documented the Futbolix attack surface and trust boundaries in the CP1 file.
- Claimed and analyzed the reservation-service to PostgreSQL trust boundary in the team documentation repository.
- Prepared a STRIDE analysis covering Spoofing, Tampering, Repudiation, Information Disclosure, Denial of Service, and Elevation of Privilege.
- Identified and prioritized security threats related to reservation data, database access, authorization, and service availability.
- Created three security issues in the team repository: #64, #65, and #66.
- Added the security issues to the Futbolix project backlog.
- Reviewed the teammate's STRIDE Pull Request #49 and published a review comment about forged identity headers, XSS exposure, and authorization validation.
- Proposed security checks and a negative authorization test for the workshop CI activity.

## 3. Blockers and risks

- The negative authorization test must be executed against a real protected Futbolix endpoint and its actual response must be recorded.
- The security workflow must be validated before considering CP4 and CP8 complete.
- The STRIDE Pull Request must receive and address peer review feedback before final completion.
- The physical library book references must include the exact pages consulted.
- The final implementation plan and reflection must be completed individually.

## 4. Plan for next week

- Complete the review process for my STRIDE Pull Request.
- Refine the three security backlog issues and verify their acceptance criteria.
- Execute and document the negative authorization test.
- Validate the security workflow.
- Complete the individual implementation plan and final workshop documentation.
- Continue the implementation of the prioritized security improvements in Futbolix.

## 5. Compliance self-check

- [ ] Conventional Commits - `type(scope): summary`
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...)
- [ ] Testable acceptance criteria
- [ ] Tests added/updated (unit / integration)
- [ ] DDD / hexagonal boundaries respected (domain has no I/O)
- [ ] No secrets; config via environment variables

## 6. Evidence links

- Personal fork: https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/tree/main/13-week/workshop
- Attack surface map (CP1): https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/blob/main/13-week/workshop/01-attack-surface.md
- STRIDE Pull Request (CP2): https://github.com/code-corhuila/ftx-docs/pull/63
- Security issue #64: https://github.com/code-corhuila/ftx-docs/issues/64
- Security issue #65: https://github.com/code-corhuila/ftx-docs/issues/65
- Security issue #66: https://github.com/code-corhuila/ftx-docs/issues/66
- Peer review Pull Request #49: https://github.com/code-corhuila/ftx-docs/pull/49
- My peer review comment: https://github.com/code-corhuila/ftx-docs/pull/49#issuecomment-6532338327
- Security CI proposal (CP4): https://github.com/Dannamo34/sistemas-distribuidos-2026-b-g1/tree/main/13-week/workshop/04-security-ci
- Physical library book: Schach, Stephen R. - Ingeniería de software clásica y orientada a objetos, 6th edition, 2006, call number 0004988. Library record: 19:10.
- Reflection document: https://docs.google.com/document/d/1DfHYwEHrQqXEnCsFDEhxy2HUjrXGIhGSsVdtZVA1Rns/edit?tab=t.0 