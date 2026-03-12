# CSCE 548 – Weather Microservices System

**Author:** Hardik Marlapudi
**Course:** CSCE 548 – Enterprise Software Architecture
**University:** University of South Carolina

---

# Project Overview

This project implements a **Weather Information System using a microservices architecture in Java**.
The application manages weather records, alerts, and locations through separate services and provides a **web-based UI dashboard** to interact with the system.

The system demonstrates:

* REST-style service communication
* Microservice separation
* CRUD operations
* JSON-based data exchange
* Frontend UI integration
* Database-backed persistence

---

# System Architecture

The system consists of **three backend services and a frontend UI**.

Frontend UI (GitHub Pages / Local Web UI)
↓
Weather Service (Port 8081)
↓
Alert Service (Port 8082)
↓
Location Service (Port 8083)
↓
MySQL Database

---

# Services

## Weather Service (Port 8081)

Handles all weather record operations.

### Features

* View weather records
* Add new weather records
* Update weather records
* Delete weather records
* Returns weather data in JSON format

### API Endpoints

GET /weather
POST /weather
PUT /weather/{id}
DELETE /weather/{id}

---

## Alert Service (Port 8082)

Handles weather alerts.

### Features

* Retrieve alert list
* Add new alerts

### API Endpoints

GET /alerts
POST /alerts

---

## Location Service (Port 8083)

Handles location information.

### Features

* Retrieve weather station locations

### API Endpoints

GET /locations

---

# Frontend UI

The project includes a **simple weather dashboard UI** built with:

* HTML
* CSS
* JavaScript

### Features

* View weather records
* Add new weather record
* Edit existing weather record
* Delete weather record
* View alerts
* View locations

Weather cards display:

* City
* State
* Condition
* Temperature
* Humidity
* Date

---

# Technologies Used

## Backend

* Java
* Java HTTP Server
* JDBC
* MySQL
* JSON

## Frontend

* HTML
* CSS
* JavaScript
* Fetch API

## Tools

* VS Code
* Git
* GitHub
* GitHub Pages
* Node.js

---

# Project Folder Structure

```
CSCE548-SemesterProject
│
├── src
│   ├── WeatherService.java
│   ├── AlertService.java
│   ├── LocationService.java
│   ├── DAO classes
│   └── Business logic classes
│
├── web
│   ├── index.html
│   ├── script.js
│   └── styles.css
│
├── sql
│   └── database schema and setup
│
├── screenshots
│   └── UI screenshots
│
├── package.json
└── README.md
```

---

# Database Setup

### Step 1 – Install MySQL

Make sure MySQL server is installed and running.

### Step 2 – Create Database

```
CREATE DATABASE weatherdb;
```

### Step 3 – Import Database Schema

```
mysql -u root -p weatherdb < sql/weatherdb.sql
```

### Step 4 – Configure Database Connection

Update credentials inside:

```
src/DBConnection.java
```

Example configuration:

```
String url = "jdbc:mysql://localhost:3306/weatherdb";
String username = "root";
String password = "your_password";
```

---

# How to Compile and Run the Application

## Step 1 – Clone the Repository

```
git clone https://github.com/HardikMarlapudi/CSCE548-SemesterProject.git
cd CSCE548-SemesterProject
```

---

## Step 2 – Compile the Java Services

Navigate to the source folder:

```
cd src
```

Compile all Java files.

### Mac / Linux

```
javac -cp ".:../lib/*" *.java
```

### Windows

```
javac -cp ".;../lib/*" *.java
```

---

## Step 3 – Start the Microservices

Run the service launcher:

### Mac / Linux

```
java -cp ".:../lib/*" ServiceLauncher
```

### Windows

```
java -cp ".;../lib/*" ServiceLauncher
```

Expected output:

```
Starting Weather Microservices...
All services initialized.

Weather Service running at http://localhost:8081/weather
Alert Service running at http://localhost:8082/alerts
Location Service running at http://localhost:8083/locations
```

---

## Step 4 – Start the Frontend UI

Navigate to the web folder:

```
cd ../web
```

Open:

```
index.html
```

in your browser.

Alternatively, run with Live Server:

```
npx live-server
```

---

# Accessing the System

### Frontend UI

```
web/index.html
```

### Weather API

```
http://localhost:8081/weather
```

### Alert API

```
http://localhost:8082/alerts
```

### Location API

```
http://localhost:8083/locations
```

---

# GitHub Pages Deployment

The frontend UI is deployed using **GitHub Pages**.

Deploy using:

```
npm install
npm run deploy
```

Deployment directory:

```
/web
```

Project URL:

```
https://HardikMarlapudi.github.io/CSCE548-SemesterProject/
```

Note:
Backend services must run locally for API requests to work.

---

# Example Startup Flow

Typical workflow:

```
git clone https://github.com/HardikMarlapudi/CSCE548-SemesterProject.git
cd CSCE548-SemesterProject/src
javac -cp ".:../lib/*" *.java
java -cp ".:../lib/*" ServiceLauncher
```

Then open:

```
web/index.html
```

---

# Screenshots

(Add screenshots of your UI here)

Examples:

* Weather dashboard
* Add weather record
* Update record
* Delete record
* Weather table

---

# Future Improvements

Possible improvements for the system include:

* Docker containerization
* Cloud deployment
* Authentication and user login
* Improved UI design
* Real-time weather API integration
* Kubernetes deployment

---

# Conclusion

This project demonstrates how **microservices can be implemented in Java to build scalable backend systems**, while also integrating a **web-based UI for user interaction**.

It highlights separation of concerns through service-based architecture and shows how independent services can work together to form a complete system.
