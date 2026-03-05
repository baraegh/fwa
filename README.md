
```
  ██████╗██╗███╗   ██╗███████╗███╗   ███╗ █████╗
 ██╔════╝██║████╗  ██║██╔════╝████╗ ████║██╔══██╗
 ██║     ██║██╔██╗ ██║█████╗  ██╔████╔██║███████║
 ██║     ██║██║╚██╗██║██╔══╝  ██║╚██╔╝██║██╔══██║
 ╚██████╗██║██║ ╚████║███████╗██║ ╚═╝ ██║██║  ██║
  ╚═════╝╚═╝╚═╝  ╚═══╝╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝
```
FWA — First Web Application
A cinema booking system prototype built with pure Java Servlets

Part of the 42 Network Java curriculum — no frameworks, no shortcuts, just raw Servlet craftsmanship.

## 📖 Overview

**FWA** is the first prototype of a cinema booking system, built entirely on the **Java Servlet API** — no Spring, no Hibernate. It focuses on the fundamentals of web application development: authentication, session management, authorization filters, file handling, and database interaction over raw JDBC.

The application is packaged as a **WAR file** and deployed on **Apache Tomcat**.

---

## ⚡ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java (Latest LTS) |
| Web | Java Servlet API |
| Server | Apache Tomcat |
| Build | Maven |
| Database | MySQL / PostgreSQL |
| DB Access | JDBC / JdbcTemplate |
| Views | JSP + JSTL |
| Security | BCrypt password encryption |

---

## 📁 Project Structure
````
Cinema/
├── src/
│   └── main/
│       ├── java/
│       │   └── fr.42.cinema/
│       │       ├── config/          # App configuration & DB connection
│       │       ├── services/        # Business logic layer
│       │       ├── models/          # Domain models (User, AuthLog, Image)
│       │       ├── repositories/    # JDBC data access layer
│       │       ├── servlets/        # HTTP request handlers
│       │       ├── filters/         # Authorization filter
│       │       └── listeners/       # App lifecycle listeners
│       │
│       ├── resources/
│       │   └── sql/
│       │       ├── schema.sql       # Database schema
│       │       └── data.sql         # Seed / test data
│       │
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml          # Servlet + filter mappings
│           ├── html/                # Static HTML pages
│           └── jsp/                 # Dynamic JSP views
│
├── pom.xml
├── application.properties           # DB URL, credentials, storage path
└── README.md
````

---

## 🚀 Getting Started

### 1 — Clone the repository
```bash
git clone https://github.com/your-username/fwa.git
cd fwa
```

### 2 — Configure the database

Edit `application.properties`:
```properties
db.url=jdbc:postgresql://localhost:5432/cinema
db.user=postgres
db.password=postgres
storage.path=/tmp/uploads
```

### 3 — Initialize the database
```bash
psql -U postgres -d cinema -f src/main/resources/sql/schema.sql
psql -U postgres -d cinema -f src/main/resources/sql/data.sql
```

### 4 — Build the WAR
```bash
mvn clean package
```

The output will be at:
````
target/cinema.war
````

### 5 — Deploy to Tomcat
```bash
cp target/cinema.war $TOMCAT_HOME/webapps/
$TOMCAT_HOME/bin/startup.sh
```

App available at → **`http://localhost:8080/cinema`**

---

## 🎯 Features

### 🔐 User Registration — `/signUp`

New users register with:
- First name & last name
- Phone number
- Password *(hashed with BCrypt before storage)*

---

### 🔑 Authentication — `/signIn`

On login the app:
1. Verifies the user exists in the DB
2. Compares the submitted password against the BCrypt hash
3. Creates an **HttpSession** on success → redirects to `/profile`
4. Redirects back to `/signIn` on failure

---

### 🛡 Authorization Filter

A Servlet `Filter` guards restricted resources:

| Route | Status |
|---|---|
| `/profile` | 🔒 Requires active session |
| `/signIn` | 🌐 Public |
| `/signUp` | 🌐 Public |

Unauthenticated requests to protected routes receive **HTTP 403**.

---

### 👤 Profile Page — `/profile`

Rendered with **JSP + JSTL**, displays:
- User info (first name, last name, email)
- Full authentication history (date / time / IP)
- Uploaded avatar images

---

### 🖼 Avatar Upload — `POST /images`

Users can upload profile images:
- Files are stored on disk at the path defined in `application.properties`
- Each file is renamed with a **UUID** to guarantee uniqueness
- Accessible via: `http://host:port/cinema/images/{unique-file-name}`

---

### 📜 Authentication History

Every successful login records:
- Date & time
- Client IP address

Displayed as a list on the profile page.

---

## 🧠 Learning Objectives

This project covers the core building blocks of Java web development:

- ✅ Java Servlet lifecycle (`init`, `service`, `destroy`)
- ✅ MVC architecture without a framework
- ✅ Session management with `HttpSession`
- ✅ Request filtering with `javax.servlet.Filter`
- ✅ JSP templating with JSTL tag libraries
- ✅ Raw JDBC / JdbcTemplate for database access
- ✅ Multipart file upload handling
- ✅ BCrypt-based secure password storage

---

## 📦 Maven Reference
```bash
# Build the WAR
mvn clean package

# Clean compiled output
mvn clean
```

---

## 👨‍💻 Author

**Elbarae El-Ghannouchi**
*42 Network — Java Curriculum*

---

<div align="center">
<sub>Built without Spring. Without Hibernate. Just Java.</sub>
</div>

