# QuickFlags

**QuickFlags** is a feature flag management engine built with **Java 21** and **Spring Boot 3**.

It is designed to handle **percentage-based rollouts** (Canary Releases) using a stateless, deterministic hashing. This ensures that a specific user always sees the same feature state without requiring persistent user session storage.

## About me
Hi, I'm Vansh a Software Engineering Student from the University of New South wales. I am currently in my honours year and close to finishing my degree. This project is way for me to apply my knowledge I learned as a student into real world applications. I hope to keep adding and progressing this project (alongside other projects).
Any advice or criticism will be highly appreciated and will help me in my journey as a developer. 

## Key Features

* **Stateless Determinism:** Uses `Hash(UserId + FeatureName) % 100` to guarantee consistent experiences across distributed systems.
* **Percentage Rollouts:** granular control over feature releases (e.g., "Roll out to 20% of users").
* **In-Memory Architecture:** Runs with an embedded H2 database for instant setup and testing.
* **API:** Simple JSON-based interface for creating and checking flags.


## Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.4.1
* **Database:** H2 (In-Memory) / Spring Data JPA
* **Build Tool:** Maven

## Getting Started

### Prerequisites
* JDK 21 or higher installed.

### Installation & Run
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/crucialvansh/quickflags.git
    cd quickflags
    ```

2.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```

The server will start on `http://localhost:8080`.

---

## API Reference

### 1. Create a New Flag
Registers a new feature flag in the system.

* **Endpoint:** `POST /api/flags`
* **Body:**
    ```json
    {
        "name": "dark-mode",
        "description": "Beta testing the new dark theme",
        "enabled": true,
        "rolloutPercentage": 50
    }
    ```

**Example (cURL):**
```bash
    curl -X POST http://localhost:8080/api/flags \
    -H "Content-Type: application/json" \
    -d '{"name": "dark-mode", "description": "Beta testing the new dark theme", "enabled": true, "rolloutPercentage": 50}'
```

### 2. Check Feature Status

Determines if a feature is enabled for a specific user.

* **Endpoint**: GET /api/flags/{name}/check?userId={userId}

* **Parameters**:

  * _name_: The unique ID of the flag (e.g., dark-mode).
  * _userId_: The unique ID of the user (used for hashing).

**Example (cURL):**

```bash
  curl "http://localhost:8080/api/flags/dark-mode/check?userId=user123"
```

Response:

```JSON
  true
```

### 3. Update Features

Updates features metadata or creates new features if not found

* **Endpoint**: PUT /api/flags/{name}
* **Body**:
    ```JSON
      {
        "name": "dark-mode-v1",
        "description" : "Beta testing version 1 of dark mode",
        "enabled": true,
        "rolloutPercentage": 30
      } 
  ```
* **Example (cURL)**:
```bash
    curl -X POST http://localhost:8080/api/flags/dark-mode/ \
    -H "Content-Type: application/json" \
    -d '{"name": "dark-mode-v1", "description": "Beta testing version 1 of dark mode", "enabled": true, "rolloutPercentage": 30}'
```

### 4. Get all features
Gets all created features

* **Endpoint**: GET /api/flags/
* **Example (cURL):**

```bash
  curl "http://localhost:8080/api/flags/"
```


## Explanation

QuickFlags avoids random number generation. Instead, it uses hashing to determine user eligibility.

_Input_: Takes the userId and the featureName.

_Hash_: Combines them (user123dark-mode) and calculates a hash code.

_Bucket_: Converts the hash to a bucket number (0-99).

**Decision**:
```
If Bucket < RolloutPercentage → TRUE
Else → FALSE
```

This means user123 will always fall into the same bucket for dark-mode, ensuring they don't see the feature flicker on and off.

## Roadmap (MVP)

1. [x]  Core Logic Engine

2. [x] H2 Database Integration

3. [ ] Testing 

4. [ ] Admin Dashboard (List/Edit Flags)

5. [ ] Redis Caching Layer
 
6. [ ] Docker Support

## License

This project is licensed under the MIT License.