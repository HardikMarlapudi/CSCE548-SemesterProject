CREATE DATABASE IF NOT EXISTS weather_db;
USE weather_db;

-- =========================
-- LOCATIONS
-- =========================
CREATE TABLE locations (
    location_name VARCHAR(100) PRIMARY KEY,
    state VARCHAR(50),
    country VARCHAR(50)
);

-- =========================
-- WEATHER CONDITIONS
-- =========================
CREATE TABLE weather_conditions (
    condition_name VARCHAR(50) PRIMARY KEY
);

-- =========================
-- STATIONS
-- =========================
CREATE TABLE stations (
    station_id INT AUTO_INCREMENT PRIMARY KEY,
    state_name VARCHAR(100),
    location_name VARCHAR(100)
);

-- =========================
-- WEATHER RECORDS (MAIN TABLE)
-- =========================
CREATE TABLE weather_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(100),
    state_name VARCHAR(100),
    condition_name VARCHAR(50),
    temperature DECIMAL(5,2),
    humidity INT,
    record_date DATE
);

-- =========================
-- ALERTS
-- =========================
CREATE TABLE alerts (
    alert_id INT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(100),
    message VARCHAR(255)
);
