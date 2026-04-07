# Buscador de Actividades Deportivas

A JEE web application built with Servlets and JSPs for searching and managing sporting activities at Universidad Carlos III de Madrid. Developed as Part II of the *Diseño de Aplicaciones Telemáticas* practice.

## Project Structure

```
buscador-deportivo/
├── index.jsp                                   # Initial page (Register / Log In)
├── css/
│   └── style.css                               # Application stylesheet
├── jsp/
│   ├── common/
│   │   ├── banner.jsp                          # Header banner (included in all pages)
│   │   ├── footer.jsp                          # Footer copyright (included in all pages)
│   │   └── error.jsp                           # Generic error page
│   ├── client/
│   │   ├── register.jsp                        # Registration form (Exercise 2)
│   │   ├── confirmRegister.jsp                 # Registration confirmation (Exercise 2)
│   │   ├── registerSuccess.jsp                 # Registration success message (Exercise 2)
│   │   ├── login.jsp                           # Client login form (Exercise 3)
│   │   ├── search.jsp                          # Activity search form (Exercise 1)
│   │   └── results.jsp                         # Search results display (Exercise 1)
│   └── manager/
│       ├── login.jsp                           # Manager login form (Exercise 4)
│       ├── dashboard.jsp                       # Manager dashboard (Exercise 4)
│       ├── addActivity.jsp                     # Add activity form (Exercise 4)
│       ├── editActivity.jsp                    # Edit activity form (Exercise 4)
│       └── listActivities.jsp                  # List all activities (Exercise 4)
├── WEB-INF/
│   ├── web.xml                                 # Deployment descriptor
│   ├── lib/                                    # External libraries (commons-text, commons-lang3)
│   └── classes/
│       └── activities/
│           ├── model/                          # Data access layer (reused from gestor-deportivo)
│           │   ├── Activity.java               # Activity data class
│           │   ├── Client.java                 # Client data class
│           │   ├── Pavillion.java              # Pavillion data class
│           │   ├── DBInteraction.java          # All database operations (PreparedStatement)
│           │   ├── SecurityUtils.java          # SHA-256 password hashing with pepper
│           │   └── InputValidator.java         # HTML escaping for XSS prevention
│           └── control/                        # Servlet controller layer
│               ├── SearchServlet.java          # Search operations (Exercise 1)
│               ├── RegisterServlet.java        # Registration with cookies (Exercise 2)
│               ├── LoginServlet.java           # Client login with session (Exercise 3)
│               ├── LogoutServlet.java          # Logout (Exercise 3)
│               ├── AuthFilter.java             # Access restriction filter (Exercise 3)
│               ├── ManagerLoginServlet.java    # Manager authentication (Exercise 4)
│               ├── ManagerServlet.java         # Manager operations (Exercise 4)
│               └── ManagerAuthFilter.java      # Manager access filter (Exercise 4)
├── TABLE_CREATION.sql                          # SQL script for database setup
├── .gitignore                                  # Git ignore rules
└── README.md                                   # This file
```

## Architecture (MVC Pattern)

The application follows the **Model-View-Controller** pattern as required:

| Layer       | Components | Description |
|-------------|-----------|-------------|
| **Model**   | `activities.model.*` | Data access (DBInteraction), data classes (Activity, Client, Pavillion), security utilities |
| **View**    | `jsp/**/*.jsp`, `css/style.css` | JSP pages for user interface, CSS for styling |
| **Controller** | `activities.control.*` | Servlets that receive HTTP requests, invoke the model, and dispatch to JSP views |

## Exercises Implemented

### Exercise 1 — Basic Search Application
Seven search operations available to authenticated clients:
1. List all activities
2. List all pavillions
3. List activities with free places
4. List activities with free places costing less than a specified amount
5. List activities with free places at a specified pavillion
6. Show information about a particular activity by name
7. **Search activities by text in name or description** (new functionality)

### Exercise 2 — Registration Form
- Initial page offers "Register" and "Already Registered" options
- Registration form collects: login, password, name, surname, address, phone
- Confirmation page displays entered data for review
- If rejected, form is shown again pre-filled (data stored in **multiple cookies**)
- If confirmed, data is saved to the CLIENTS table and a success page is shown

### Exercise 3 — Restricted Access
- Authenticated access via login/password using **server-side session** (HttpSession, single JSESSIONID cookie)
- Client information stored in cookies for automatic re-login on return visits
- Incorrect credentials: error message displayed (max **3 attempts**, then redirected to initial page)
- **AuthFilter** blocks unauthenticated access to `/search`
- Logout clears both session and cookies, redirects to initial page
- User info and logout link displayed in a bar at the top of search pages

### Exercise 4 — Manager Functionality
- Managers access via a separate URL (`/manager/login`)
- Authentication with hardcoded password (no registration needed)
- Available operations: **Add Activity**, **Edit Activity**, **List All Activities**
- Protected by **ManagerAuthFilter**

### Exercise 5 — Security Improvements
- **5.1 (SQL Injection):** All SQL queries in `DBInteraction.java` use `PreparedStatement` instead of string concatenation
- **5.2 (XSS Prevention):** All user input is sanitized with `StringEscapeUtils.escapeHtml4()` from commons-text via the `InputValidator` utility class
- **5.3 (Password Hashing):** Passwords are hashed with SHA-256 + pepper `"DATJEE"` before storage and comparison (implemented in `SecurityUtils.java`). The PASSWD column is `VARCHAR(64)` to hold the hex hash.

## Reuse from [gestor-deportivo](https://github.com/dalouc/gestor-deportivo)

The following components were **reused and adapted** from the text-based gestor-deportivo application:

| Original | Adapted | Changes |
|----------|---------|---------|
| `Activity.java` | `activities.model.Activity` | Renamed getters to standard Java conventions (`getId()` vs `getid()`) |
| `Client.java` | `activities.model.Client` | Same as above |
| `Pavillion.java` | `activities.model.Pavillion` | Same as above |
| `DBInteraction.java` | `activities.model.DBInteraction` | Migrated from `Statement` to `PreparedStatement`; removed Query.java dependency; added password hashing; added `getActivityById()`, `updateActivity()`, `loginExists()`; removed text-mode methods (registration/subscription operations not needed in this web version) |
| `SecurityUtils.java` | `activities.model.SecurityUtils` | Changed pepper from `"base de datos"` to `"DATJEE"` as specified in Exercise 5.3 |

The `Query.java` auxiliary class was **removed** because `PreparedStatement` handles both query types (SELECT and INSERT/UPDATE/DELETE) directly, making the wrapper unnecessary.

The `InputOutput.java` class was **replaced** by JSP views, as the presentation layer is now web-based.

## Prerequisites

- **Java JDK** 8 or higher
- **Apache Tomcat** 8.x or higher
- **MySQL** 5.x or higher
- **MySQL Connector/J** JDBC driver (placed in Tomcat's `lib/` folder)
- **Apache Commons Text** and **Apache Commons Lang3** JARs (placed in Tomcat's `lib/` folder for Exercise 5.2)

## Setup

### 1. Create the Database
```bash
mysql -u root -p < TABLE_CREATION.sql
```

### 2. Configure Database Credentials
Edit `DBInteraction.java` and update the connection parameters:
```java
con = DriverManager.getConnection(url, "your_user", "your_password");
```

### 3. Add Required Libraries to Tomcat
Copy the following to `$CATALINA_HOME/lib/`:
- `mysql-connector-java-x.x.jar`
- `commons-text-x.x.jar`
- `commons-lang3-x.x.jar`

### 4. Deploy
Copy the `buscador_deportivo/` folder into Tomcat's `webapps/` directory and start the server.

### 5. Access
- **Client application:** `http://localhost:8080/buscador_deportivo/`
- **Manager application:** `http://localhost:8080/buscador_deportivo/manager/login`

## Concurrency Note

Each servlet request creates its own `DBInteraction` instance (and therefore its own `Connection`), and closes it in a `finally` block. This ensures thread safety without shared mutable state.

## Design Decisions

1. **Single results JSP:** Instead of separate JSPs for each result type (as in the original MVC solution), a single `results.jsp` handles both activity and pavillion lists, reducing file count and duplication.
2. **Filter-based authentication:** Servlet filters (`AuthFilter`, `ManagerAuthFilter`) enforce access control declaratively rather than requiring each servlet to check session state manually.
3. **InputValidator as a utility:** Centralizes HTML escaping so every servlet can sanitize input consistently with a single method call.
4. **Removed Query.java:** PreparedStatement makes the Query wrapper redundant and actually improves security by eliminating the temptation to use string-concatenated queries.

## Authors
[Noel Andolz Aguado](https://github.com/nooelanag) and [Daniel Lozano Uceda](https://github.com/dalouc)
