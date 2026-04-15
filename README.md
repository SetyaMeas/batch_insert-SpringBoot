<h1 align="center">Batch Insert - Spring Boot</h1>

In this project, we are dealing with Batch Insert in 3 differrent ways:

- Hibernate (Spring Data JPA)
- JDBC Batch Processing
- JDBC with Multi Threading

Tech Stack
---
- Java
- Spring Boot
- Maven
- Flyway
- PostgreSQL

Project initialize
---

1. Install dependencies

```bash
mvn clean install -DskipTests
```

2. Run Database migrations

```bash
mvn flyway:migrate
```

3. Run the application

```bash
mvn spring-boot:run
```

API endpoints
---

| Method | Endpoint        | Request Parameter | Description                           |
| ------ | --------------- | ----------------- | ------------------------------------- |
| POST   | /hibernate      | total             | Batch insert using JPA                |
| POST   | /jdbc           | total             | Batch insert using JDBC               |
| POST   | /jdbc-threading | total             | JDBC batch insert with multithreading |

HTTP Client
---

To test API, send the HTTP-request inside [client.http](./client.http) file.

Referrences: [medium](https://medium.com/@wahyaumau/boost-jpa-bulk-insert-performance-by-90-3a6232d9068d), [baeldung](https://www.baeldung.com/spring-jdbc-batch-inserts)