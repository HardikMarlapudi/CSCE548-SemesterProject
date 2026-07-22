USE weather_db;

-- =========================
-- LOCATIONS
-- =========================
INSERT INTO locations VALUES
('Columbia', 'SC', 'USA'),
('New York', 'NY', 'USA');

-- =========================
-- WEATHER CONDITIONS
-- =========================
INSERT INTO weather_conditions VALUES
('Sunny'),
('Rainy'),
('Cloudy');

-- =========================
-- STATIONS
-- =========================
INSERT INTO stations (state_name, location_name) VALUES
('Downtown Station', 'Columbia'),
('Campus Station', 'Columbia'),
('Central Park Station', 'New York'),
('Times Square Station', 'New York');

-- =========================
-- WEATHER RECORDS (50 ROWS)
-- =========================
INSERT INTO weather_records
(city_name, state_name, condition_name, temperature, humidity, record_date)
VALUES
-- Columbia (25)
('Columbia','Downtown Station','Sunny',72.5,55,'2025-01-01'),
('Columbia','Downtown Station','Cloudy',70.1,60,'2025-01-02'),
('Columbia','Downtown Station','Rainy',66.4,78,'2025-01-03'),
('Columbia','Downtown Station','Sunny',74.2,52,'2025-01-04'),
('Columbia','Downtown Station','Cloudy',71.0,58,'2025-01-05'),
('Columbia','Campus Station','Rainy',67.3,80,'2025-01-06'),
('Columbia','Campus Station','Sunny',75.6,50,'2025-01-07'),
('Columbia','Campus Station','Cloudy',72.8,57,'2025-01-08'),
('Columbia','Campus Station','Rainy',68.9,82,'2025-01-09'),
('Columbia','Campus Station','Sunny',76.4,48,'2025-01-10'),
('Columbia','Downtown Station','Sunny',77.1,47,'2025-01-11'),
('Columbia','Downtown Station','Cloudy',73.5,55,'2025-01-12'),
('Columbia','Downtown Station','Rainy',69.2,83,'2025-01-13'),
('Columbia','Downtown Station','Sunny',78.0,45,'2025-01-14'),
('Columbia','Downtown Station','Cloudy',74.6,54,'2025-01-15'),
('Columbia','Campus Station','Rainy',70.0,85,'2025-01-16'),
('Columbia','Campus Station','Sunny',79.3,44,'2025-01-17'),
('Columbia','Campus Station','Cloudy',75.1,53,'2025-01-18'),
('Columbia','Campus Station','Rainy',71.4,86,'2025-01-19'),
('Columbia','Campus Station','Sunny',80.2,43,'2025-01-20'),
('Columbia','Downtown Station','Cloudy',76.0,52,'2025-01-21'),
('Columbia','Downtown Station','Rainy',72.1,88,'2025-01-22'),
('Columbia','Downtown Station','Sunny',81.5,42,'2025-01-23'),
('Columbia','Campus Station','Cloudy',77.3,51,'2025-01-24'),
('Columbia','Campus Station','Rainy',73.6,89,'2025-01-25'),

-- New York (25)
('New York','Central Park Station','Cloudy',35.2,65,'2025-01-01'),
('New York','Central Park Station','Sunny',38.1,60,'2025-01-02'),
('New York','Central Park Station','Rainy',36.4,75,'2025-01-03'),
('New York','Central Park Station','Cloudy',37.0,66,'2025-01-04'),
('New York','Central Park Station','Sunny',39.2,58,'2025-01-05'),
('New York','Times Square Station','Rainy',35.9,77,'2025-01-06'),
('New York','Times Square Station','Cloudy',36.7,69,'2025-01-07'),
('New York','Times Square Station','Sunny',40.3,57,'2025-01-08'),
('New York','Times Square Station','Rainy',37.6,78,'2025-01-09'),
('New York','Times Square Station','Cloudy',38.0,67,'2025-01-10'),
('New York','Central Park Station','Sunny',41.1,55,'2025-01-11'),
('New York','Central Park Station','Rainy',36.8,80,'2025-01-12'),
('New York','Central Park Station','Cloudy',39.4,66,'2025-01-13'),
('New York','Central Park Station','Sunny',42.0,54,'2025-01-14'),
('New York','Central Park Station','Rainy',37.2,82,'2025-01-15'),
('New York','Times Square Station','Cloudy',40.1,65,'2025-01-16'),
('New York','Times Square Station','Sunny',43.3,53,'2025-01-17'),
('New York','Times Square Station','Rainy',38.5,83,'2025-01-18'),
('New York','Times Square Station','Cloudy',41.0,64,'2025-01-19'),
('New York','Times Square Station','Sunny',44.2,52,'2025-01-20'),
('New York','Central Park Station','Rainy',39.1,84,'2025-01-21'),
('New York','Central Park Station','Cloudy',42.3,63,'2025-01-22'),
('New York','Central Park Station','Sunny',45.0,51,'2025-01-23'),
('New York','Times Square Station','Rainy',40.2,85,'2025-01-24'),
('New York','Times Square Station','Cloudy',43.1,62,'2025-01-25');

-- =========================
-- ALERTS
-- =========================
INSERT INTO alerts (location_name, message) VALUES
('Columbia', 'Heavy rain expected'),
('New York', 'Cold weather advisory');
