<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers.
     Your weekly grade is read AUTOMATICALLY from this file:
       07-week/hu-status/README.md  (inside YOUR fork). English. -->

# Weekly Status - Week 08

<!-- CONFIG-START - must match your profile repo (username/username) CONFIG -->
- FULL_NAME: Danna Michelle Morales Losada
- GITHUB_USER: dannamo34
- TEAM: Futbolix
- SPRINT_GOAL: Continue improving and validating the Futbolix technical documentation, update the UML documentation, continue the API documentation work, advance the DevOps and CI/CD documentation, and apply feedback to keep the documentation consistent with the current project architecture and MVP decisions.
<!-- CONFIG-END -->

## 1. User stories worked this week

| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| HU-CAN-001 | Consult soccer fields | doing | API and UML documentation |
| HU-RES-001 | Create reservation | doing | API and UML documentation |
| HU-CLI-001 | Register client | doing | API and UML documentation |
| HU-HOR-001 | Consult available schedules | doing | API and UML documentation |

## 2. My individual contribution

- Continued working on the Futbolix technical documentation in the `ftx-docs` repository.
- Worked on the `08-uml` documentation and updated the UML diagrams to keep them aligned with the current Futbolix architecture and MVP decisions.
- Reviewed and adjusted the UML documentation so that the diagrams represent the defined services, domain responsibilities, relationships, and project boundaries consistently.
- Continued the work previously started in the `07-api` folder, reviewing and improving the API documentation and keeping the contracts aligned with the project's API versioning, authentication, and architectural decisions.
- Advanced the documentation in the `10-devops` folder, especially the CI/CD documentation and the explanation of how the project moves from development through the different environments.
- Reviewed the documentation changes to identify inconsistencies between the API, UML, and DevOps sections.
- Applied corrections and improvements based on the feedback received during the documentation review process.
- Continued using the repository workflow and Pull Requests to organize and review documentation changes.
- Participated in the general improvement of the Futbolix documentation repository so that the documentation remains consistent before moving further into implementation.

## 3. Blockers and risks

- The main risk is keeping all documentation sections synchronized as the project architecture continues to evolve.
- Changes in the API documentation can affect the UML diagrams, microservices documentation, and DevOps documentation, so these sections need to be reviewed together.
- Some documentation still requires feedback and additional corrections before it can be considered stable.
- The transition from documentation to implementation requires keeping the same domain boundaries, architecture, API contracts, and business rules defined in the documentation.
- The CI/CD documentation still needs to be reviewed and refined together with the actual implementation decisions of the project.

## 4. Plan for next week

- Continue reviewing and receiving feedback on the `ftx-docs` repository.
- Apply the corrections and recommendations received during the review process.
- Continue validating the consistency between `07-api`, `08-uml`, `09-microservices`, and `10-devops`.
- Continue improving the API documentation when new feedback or inconsistencies are identified.
- Continue refining the CI/CD and DevOps documentation according to the project's implementation progress.
- Review the UML diagrams again whenever there are changes to the architecture, services, or domain responsibilities.
- Keep the documentation aligned with the Futbolix MVP and the defined DDD, hexagonal architecture, SOLID, Clean Code, TDD, and CI/CD principles.
- Prepare the documentation and repository for the next stage of implementation and integration.

## 5. Compliance self-check

- [x] Conventional Commits - `type(scope): summary`
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...)
- [x] Testable acceptance criteria
- [ ] Tests added/updated (unit / integration)
- [x] DDD / hexagonal boundaries respected (domain has no I/O)
- [x] No secrets; config via environment variables

## 6. Evidence links

- Futbolix documentation repository:
  https://github.com/code-corhuila/ftx-docs/tree/main

- Futbolix API documentation:
  https://github.com/code-corhuila/ftx-docs/tree/main/07-api

- Futbolix UML documentation:
  https://github.com/code-corhuila/ftx-docs/tree/main/08-uml

- Futbolix DevOps documentation:
  https://github.com/code-corhuila/ftx-docs/tree/docs/update-10-ci-cd/10-devops

- Futbolix microservices documentation:
  https://github.com/code-corhuila/ftx-docs/tree/main/09-microservices
