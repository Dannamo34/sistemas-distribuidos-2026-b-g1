<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       10-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 10

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Danna Michelle Morales Losada
- GITHUB_USER: Dannamo34
- TEAM: Futbolix
- SPRINT_GOAL: Consolidate the Futbolix architecture and technical documentation, advance frontend coordination, and configure and validate the PostgreSQL infrastructure.
<!-- CONFIG-END -->

## 1. User stories worked this week

| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| TBD | PostgreSQL infrastructure configuration using Docker Compose | done | https://github.com/code-corhuila/ftx-infra-postgres/pull/8 |
| TBD | PostgreSQL environment examples for development, QA, and production | done | https://github.com/code-corhuila/ftx-infra-postgres/pull/5 |
| TBD | PostgreSQL release notes and version tagging | done | https://github.com/code-corhuila/ftx-infra-postgres/pull/10 |
| TBD | API documentation and service contracts | doing | https://github.com/code-corhuila/ftx-docs/tree/main/07-api |
| TBD | UML diagrams and architecture documentation | doing | https://github.com/code-corhuila/ftx-docs/tree/main/08-uml |
| TBD | Microservices documentation | doing | https://github.com/code-corhuila/ftx-docs/tree/main/09-microservices |
| TBD | DevOps and CI/CD documentation | doing | https://github.com/code-corhuila/ftx-docs/tree/docs/update-10-ci-cd/10-devops |
| TBD | Frontend development and architecture coordination | doing | https://github.com/code-corhuila/ftx-front |

## 2. My individual contribution

- Configured the PostgreSQL 16 service using Docker Compose.
- Configured a persistent PostgreSQL volume and a health check.
- Prepared example environment files for development, QA, and production (`dev`, `qa`, and `main`).
- Used environment variables for configuration and avoided committing real credentials.
- Validated the Docker Compose configuration using the development environment example.
- Worked on promoting the PostgreSQL infrastructure changes through pull requests and traceable commits.
- Prepared the PostgreSQL release notes, merged the release changes, created the `v1.0.0` tag, and generated a local ZIP archive.
- Contributed to the API documentation and service contract organization in `ftx-docs/07-api`.
- Worked on UML diagrams and architecture documentation in `ftx-docs/08-uml`.
- Contributed to microservices documentation in `ftx-docs/09-microservices`.
- Continued the DevOps and CI/CD documentation in `ftx-docs/10-devops`.
- Followed frontend development and architecture coordination in `ftx-front` and the related Futbolix repositories.

## 3. Blockers and risks

- The official HU IDs must be confirmed before replacing the provisional `TBD` values.
- Frontend and documentation work must be linked to specific pull requests or commits when available.
- Integration between PostgreSQL, domain database migrations, backend services, and the frontend still requires verification.
- End-to-end deployment must not be considered complete until it has been tested.
- The team must continue following the required branch, pull request, review, and release procedures.

## 4. Plan for next week

- Continue frontend development according to the approved architecture.
- Update API, UML, microservices, and CI/CD documentation based on team and instructor feedback.
- Validate the PostgreSQL infrastructure in the target environment.
- Coordinate PostgreSQL with the domain database migrations and backend services.
- Confirm official HU IDs and add direct links to the corresponding issues, pull requests, and commits.
- Verify acceptance criteria and required tests before marking stories as done.

## 5. Compliance self-check

- [x] Conventional Commits - `type(scope): summary`
- [ ] Per-environment HU branch + PR to that environment (`hu-xxx-dev` -> `develop`, etc.)
- [ ] Testable acceptance criteria verified for every reported story
- [ ] Tests added/updated (unit / integration)
- [x] DDD / hexagonal boundaries considered in architecture and documentation
- [x] No real secrets committed; configuration uses environment variables

## 6. Evidence links

- PostgreSQL repository: https://github.com/code-corhuila/ftx-infra-postgres
- PostgreSQL environment configuration PR: https://github.com/code-corhuila/ftx-infra-postgres/pull/5
- PostgreSQL infrastructure promotion PR: https://github.com/code-corhuila/ftx-infra-postgres/pull/8
- PostgreSQL release PR: https://github.com/code-corhuila/ftx-infra-postgres/pull/10
- PostgreSQL release tag: https://github.com/code-corhuila/ftx-infra-postgres/releases/tag/v1.0.0
- API documentation: https://github.com/code-corhuila/ftx-docs/tree/main/07-api
- UML documentation: https://github.com/code-corhuila/ftx-docs/tree/main/08-uml
- Microservices documentation: https://github.com/code-corhuila/ftx-docs/tree/main/09-microservices
- DevOps and CI/CD documentation: https://github.com/code-corhuila/ftx-docs/tree/docs/update-10-ci-cd/10-devops
- Frontend repository: https://github.com/code-corhuila/ftx-front
- Infrastructure repository: https://github.com/code-corhuila/ftx-infra
