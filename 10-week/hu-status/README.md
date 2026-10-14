```markdown
<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       10-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 10

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Danna Michelle Morales Losada
- GITHUB_USER: Dannamo34
- TEAM: Futbolix
- SPRINT_GOAL: Advance the Futbolix frontend and architecture work while configuring, validating, and documenting the PostgreSQL infrastructure.
<!-- CONFIG-END -->

## 1. User stories worked this week

| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| HU ID pending confirmation | PostgreSQL infrastructure configuration with Docker Compose | done | https://github.com/code-corhuila/ftx-infra-postgres/pull/8 |
| HU ID pending confirmation | PostgreSQL environment configuration examples for development, QA, and production | done | https://github.com/code-corhuila/ftx-infra-postgres/pull/5 |
| HU ID pending confirmation | PostgreSQL release notes and version tagging | done | https://github.com/code-corhuila/ftx-infra-postgres/pull/10 |
| HU ID pending confirmation | Frontend development and architecture coordination | doing | https://github.com/code-corhuila/ftx-front |

## 2. My individual contribution

- Worked individually on the `ftx-infra-postgres` repository.
- Configured the PostgreSQL 16 service using Docker Compose.
- Configured a persistent volume for PostgreSQL data.
- Added environment example files for development, QA, and production.
- Configured environment variables to avoid committing real credentials.
- Validated the Docker Compose configuration using the development environment example.
- Promoted the PostgreSQL infrastructure changes through the QA and main pull requests.
- Prepared `RELEASE_NOTES.md` with scope, deployment instructions, traceability, and rollback guidance.
- Created and published the `v1.0.0` Git tag.
- Generated the `ftx-infra-postgres-v1.0.0.zip` delivery archive.

Evidence:
- QA promotion: https://github.com/code-corhuila/ftx-infra-postgres/pull/5
- Main promotion: https://github.com/code-corhuila/ftx-infra-postgres/pull/8
- Release PR: https://github.com/code-corhuila/ftx-infra-postgres/pull/10
- Release tag: https://github.com/code-corhuila/ftx-infra-postgres/releases/tag/v1.0.0

## 3. Blockers and risks

- The PostgreSQL infrastructure changes are integrated into the main branch, but the release process should be checked against the required incremental release procedure.
- The official user story IDs and their approved issue titles must be confirmed before final traceability is claimed.
- The complete system deployment and integration with the domain database migrations have not been confirmed in this report.
- Frontend and architecture tasks require their corresponding pull requests or commits to verify their final status.

## 4. Plan for next week

- Continue frontend implementation and coordinate integration with the project architecture.
- Review the architecture requirements and confirm that the infrastructure repositories follow the corrected database-instance and volume rules.
- Verify PostgreSQL integration with the domain database migration repositories.
- Test the PostgreSQL service from a clean environment using the documented configuration.
- Confirm the official HU IDs and link each completed story to its issue, pull request, and validation evidence.
- Review the release process with the team and follow the required release branch and approval workflow for future releases.

## 5. Compliance self-check

- [x] Conventional Commits - `type(scope): summary`
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...)
- [ ] Testable acceptance criteria
- [ ] Tests added/updated (unit / integration)
- [ ] DDD / hexagonal boundaries respected (domain has no I/O)
- [x] No secrets; config via environment variables

## 6. Evidence links

- PostgreSQL infrastructure repository: https://github.com/code-corhuila/ftx-infra-postgres
- QA pull request: https://github.com/code-corhuila/ftx-infra-postgres/pull/5
- Main pull request: https://github.com/code-corhuila/ftx-infra-postgres/pull/8
- Release pull request: https://github.com/code-corhuila/ftx-infra-postgres/pull/10
- Published release tag: https://github.com/code-corhuila/ftx-infra-postgres/releases/tag/v1.0.0
- Frontend repository: https://github.com/code-corhuila/ftx-front
```
