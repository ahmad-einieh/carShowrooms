## Configuration

1- edit `application.properties` file in `src/main/resources` to edit the database connection properties.

2- edit `application.properties` file in `src/main/resources` to edit the application properties (optional).

3- edit `application.properties` file in `src/main/resources` to edit the Admin user info (optional).

4- edit `application.properties` file in `src/main/resources` to edit the JWT secret (optional).


## Development server

To start a local development server, run:

```bash
mvn spring-boot:run
```

The server will start on port 8080.
To Access the Swagger UI (Documentation): http://localhost:8080/swagger-ui/index.html

## Building

To build the project run:

```bash
mvn clean package
```

JAR file will be created in the target directory.

To run the JAR file:

```bash
java -jar target/carshowrooms-0.0.1-SNAPSHOT.jar
```


## Building docker image

```bash
docker build -t springboot-app .
```

## Running docker image

```bash
docker run -p 8080:8080 springboot-app
```

### Project Structure

1. **Source Code (`src/main/java/com/project/carshowrooms`)**:
   - **`CarshowroomsApplication.java`**: The main entry point for the Spring Boot application.
   - **`config`**: Contains configuration classes like `OpenApiConfig.java` for Swagger/OpenAPI documentation.
   - **`controller`**: Contains REST controllers for handling HTTP requests:
     - `AuthController.java`: Handles authentication-related endpoints.
     - `CarController.java`: Manages car-related operations.
     - `ShowroomController.java`: Manages car showroom operations.
   - **`dto`**: Data Transfer Objects (DTOs) for request/response handling:
     - `CarDTO.java`, `ShowroomDTO.java`, etc., for structured data transfer.
   - **`exception`**: Custom exception handling:
     - `DuplicateResourceException.java`, `ResourceNotFoundException.java`, etc., for specific error scenarios.
     - `GlobalExceptionHandler.java`: Centralized exception handling.
   - **`helpers`**: Utility classes like `EmailValidator.java` for validation logic.
   - **`model`**: Entity classes representing database tables:
     - `Car.java`, `Showroom.java`, `User.java`.
   - **`repository`**: Spring Data JPA repositories for database operations:
     - `CarRepository.java`, `ShowroomRepository.java`, `UserRepository.java`.
   - **`security`**: Security-related configurations and filters:
     - `SecurityConfig.java`: Spring Security configuration.
     - `JwtAuthenticationFilter.java`: JWT-based authentication filter.
     - `JwtTokenProvider.java`: JWT token generation and validation.
     - `InitialAdminSetup.java`: Initial setup for admin user.
   - **`service`**: Business logic layer:
     - `AuthService.java`, `CarService.java`, `ShowroomService.java`: Service classes for handling business logic.
     - `UserDetailsServiceImpl.java`: Custom user details service for authentication.

2. **Resources (`src/main/resources`)**:
   - **`application.properties`**: Configuration properties for the application.
   - **`db/migration`**: Database migration scripts using Flyway:
     - `V1__init.sql`: Initial database schema.
     - `V2__Add_User_Table.sql`: Additional schema changes.