## Configuration

1- edit the `environment.ts` file in the `src/environments` directory to change url of backend.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.


## Building docker image

```bash
docker build -t angular-app .
```

## Running docker image

```bash
docker run -p 80:80 angular-app
```

---

### Project Structure

1. **Source Code (`src/app`)**:
   - **`app.component.*`**: Root component for the application.
   - **`app.config.ts`**: Application configuration.
   - **`app.routes.ts`**: Routing configuration for the application.
   - **`components`**: Reusable UI components:
     - **`car`**: Components related to car management:
       - `car-create`: Form for creating a new car.
       - `car-list`: List of cars with filtering and pagination.
     - **`login`**: Login component for authentication.
     - **`register`**: Registration component for new users.
     - **`shared`**: Shared components like headers, footers, and error messages:
       - `error`: Error handling component.
       - `footer`: Footer component.
       - `header`: Header component.
     - **`showroom`**: Components related to showroom management:
       - `showroom-create`: Form for creating a new showroom.
       - `showroom-details`: Details view for a specific showroom.
       - `showroom-edit`: Form for editing showroom details.
       - `showroom-list`: List of showrooms with pagination and sorting.
   - **`helpers`**: Utility classes and guards:
     - `auth.guard.ts`: Route guard for authentication.
     - `auth.interceptor.ts`: HTTP interceptor for adding authentication tokens.
   - **`models`**: TypeScript interfaces/models for data structures:
     - `auth.model.ts`, `car.model.ts`, `showroom.model.ts`, `user.model.ts`.
   - **`services`**: Angular services for interacting with the backend API:
     - `auth.service.ts`: Authentication-related API calls.
     - `car.service.ts`: Car-related API calls.
     - `showroom.service.ts`: Showroom-related API calls.

2. **Environment Configuration (`src/environments`)**:
   - `environment.ts`: Environment-specific configuration.

