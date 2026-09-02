<!-- HU-STATUS TEMPLATE - do NOT remove the <!-- ... --> markers or the table headers.
     Your weekly grade is read AUTOMATICALLY from this file:
       02-week/hu-status/README.md  (inside YOUR fork). English. -->
       
# Weekly Status - Week 05

- FULL_NAME: Danna Michelle Morales Losada
- GITHUB_USER: Dannamo34
- TEAM: Futbolix
- SPRINT_GOAL: Define and document the data model for Futbolix, including entities, tables, relationships, data dictionary, modeling conventions, normalization, and migration strategy.

## 1. User stories worked this week

| HU ID | Title | Status (todo/doing/done) | Evidence (PR or commit URL) |
|---|---|---|---|
| HU-DATA-001 | Define the Futbolix data model | doing | Pending |
| HU-DATA-002 | Define the data dictionary | doing | Pending |
| HU-DATA-003 | Define database modeling conventions | doing | Pending |
| HU-DATA-004 | Define normalization and migration strategy | doing | Pending |

## 2. My individual contribution

- Defined the main data entities for the Futbolix system: Fields, Schedules, Clients, and Reservations.
- Contributed to defining the relationships between the main entities.
- Participated in the design of the database structure for the management of three soccer fields.
- Defined important fields, data types, constraints, and business rules for the main data entities.
- Contributed to defining database naming conventions, identifiers, timestamps, and data management rules.
- Participated in the analysis of database normalization and the initial migration strategy.

## 3. Blockers and risks

- Defining data ownership for each microservice while respecting the principle that each service owns its own data.
- Determining which relationships should be managed by the database and which should be handled through APIs between services.
- Defining a simple and scalable database structure without adding unnecessary complexity to the MVP.
- Maintaining consistency between the data model, business domains, and future API contracts.

## 4. Plan for next week

- Complete the data models for the Futbolix services.
- Complete the data dictionary with the main business fields and possible values.
- Review the normalization level of the database design.
- Finalize the database modeling conventions.
- Define the migration strategy and rollback approach.
- Validate consistency between the data documentation, architecture, and business domains.

## 5. Compliance self-check

- [ ] Conventional Commits - `type(scope): summary`
- [ ] Per-environment HU branch + PR to that environment (hu-xxx-dev -> develop, ...)
- [x] Testable acceptance criteria
- [ ] Tests added/updated (unit / integration)
- [x] DDD / hexagonal boundaries respected (domain has no I/O)
- [x] No secrets; config via environment variables

## 6. Evidence links

- 06-data documentation: `05-week/06-data/`
- Data models: `05-week/06-data/models.md`
- Data dictionary: `05-week/06-data/data-dictionary.md`
- Modeling conventions: `05-week/06-data/modeling-conventions.md`
- Normalization assessment: `05-week/06-data/normalization-assessment.md`
- Migration strategy: `05-week/06-data/migration-strategy.md`
