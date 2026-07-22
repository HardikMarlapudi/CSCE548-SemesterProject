CREATE DATABASE weather_db;
USE weather_db;

CREATE TABLE weather_station (
    station_id INT AUTO_INCREMENT PRIMARY KEY,
    station_name VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(50)
);

CREATE TABLE weather_location (
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(100),
    state_name VARCHAR(50),
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6)
);

CREATE TABLE weather_condition (
    condition_id INT AUTO_INCREMENT PRIMARY KEY,
    condition_name VARCHAR(100),
    temperature FLOAT,
    humidity INT
);

CREATE TABLE weather_alert (
    alert_id INT AUTO_INCREMENT PRIMARY KEY,
    alert_type VARCHAR(100),
    severity VARCHAR(50),
    description TEXT,
    location_id INT,
    FOREIGN KEY (location_id) REFERENCES weather_location(location_id)
);
