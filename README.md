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

Features:

* View weather records
* Add new weather records
* Update weather records
* Delete weather records
* Returns weather data in JSON format

API Endpoints:

GET /weather
POST /weather
PUT /weather/{id}
DELETE /weather/{id}

---

## Alert Service (Port 8082)

Handles weather alerts.

Features:

* Retrieve alert list
* Add new alerts

API Endpoints:

GET /alerts
POST /alerts

---

## Location Service (Port 8083)

Handles location information.

Features:

* Retrieve weather station locations

API Endpoints:

GET /locations

---

# Frontend UI

The project includes a **simple weather dashboard UI** built with:

* HTML
* CSS
* JavaScript

Features:

* View weather records
* Weather cards with icons
* Add new weather record
* Edit existing weather record
* Delete weather record
* View alerts
* View locations

Weather cards display:

* City
* Station
* Condition
* Temperature
* Humidity
* Date
* Weather icon

---

# Technologies Used

Backend

* Java
* Java HTTP Server
* JDBC
* MySQL
* JSON

Frontend

* HTML
* CSS
* JavaScript
* Fetch API

Tools

* VS Code
* Git
* GitHub
* GitHub Pages

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
└── README.md
```

---

# Database Setup

1. Install MySQL
2. Create database:

```sql
CREATE DATABASE weatherdb;
```

3. Import SQL schema from the `sql` folder.

4. Update database credentials in:

```
DBConnection.java
```

---

# How to Run the Application

## Step 1 – Start MySQL

Make sure MySQL is running and the database is created.

---

## Step 2 – Start Services

Run the following Java services:

Weather Service

```
WeatherService.java
```

Alert Service

```
AlertService.java
```

Location Service

```
LocationService.java
```

You should see:

```
Weather Service running at http://localhost:8081/weather
Alert Service running at http://localhost:8082/alerts
Location Service running at http://localhost:8083/locations
```

---

## Step 3 – Open the UI

Open:

```
web/index.html
```

or run using a live server.

---

# GitHub Pages Deployment

The frontend UI is deployed using **GitHub Pages**.

Deployment path:

```
/web
```

Project URL:

```
https://HardikMarlapudi.github.io/CSCE548-SemesterProject/
```

Note:
Backend services must still run locally for API requests to work.

---

# Screenshots

(Add screenshots of your UI here)

Example:

* Weather dashboard
* Weather cards
* Add/Edit/Delete record
* Alerts display

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

---
