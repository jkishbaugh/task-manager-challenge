# Task Manager - Full Stack Coding Challenge

This is a lightweight Task Manager service built as part of a full stack coding challenge. It provides a REST API using Spring Boot and a minimal frontend UI using React and TypeScript. The project is fully containerized using Docker and Docker Compose.

## Features

### Backend (Spring Boot)

- REST API with the following endpoints:
  - `POST /tasks` – Create a new task
  - `GET /tasks` – List all tasks
  - `PUT /tasks/{id}` – Update a task
  - `DELETE /tasks/{id}` – Delete a task
- Task object includes:
  - `UUID id`
  - `String title`
  - `String description` (optional)
  - `TaskStatus status` – Enum: `TODO`, `IN_PROGRESS`, `DONE`
- Input validation (e.g., title must not be blank)
- Proper HTTP status codes for success and error handling
- In-memory persistence using an H2 database
  _Justification: Fast setup for testing, no external dependencies, easy to reset and inspect._
- Unit tests for the service layer
- Integration test for the controller using `@SpringBootTest`

### Frontend (React + TypeScript)

- View a list of tasks
- Add a new task (title-only is acceptable)
- Mark a task as done
- Axios used for HTTP communication
- Minimal and functional UI

### Containerization

- `Dockerfile` for the backend service (multi-stage build using Maven)
- `Dockerfile` for the frontend service (production-ready React build served with Nginx)
- `docker-compose.yml` to orchestrate the frontend and backend containers

## Project Structure

```
.
├── backend/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── frontend/
│   ├── src/
│   ├── Dockerfile
│   ├── package.json
│   └── nginx.conf
└── docker-compose.yml
```

## Prerequisites

- Docker
- Docker Compose

## How to Build and Run

### Using Docker Compose

Build and run the entire project:

```bash
docker-compose build
docker-compose up
```

This will:

- Start the backend API at `http://localhost:8080`
- Start the frontend UI at `http://localhost:3000`

## API Endpoints

| Method | Endpoint        | Description              |
|--------|------------------|--------------------------|
| POST   | `/tasks`         | Create a new task        |
| GET    | `/tasks`         | List all tasks           |
| PUT    | `/tasks/{id}`    | Update an existing task  |
| DELETE | `/tasks/{id}`    | Delete a task            |

## Running Tests

### Backend

```bash
# From backend directory
./mvnw test
```

Tests include:

- Unit tests for the `TaskService`
- Integration tests for `TaskController` including validation logic

## Notes

- The backend uses an in-memory H2 database to keep things simple and self-contained.
- The frontend assumes that the backend is available at `http://backend:8080` inside the Docker network.
- CORS is enabled in the backend to allow interaction from the frontend running on port 5173 or 3000.

## License

This project was created for a job coding challenge and is not licensed for production use.
