INSERT INTO weather_records
(city_name, station_name, condition_name, temperature, humidity, record_date)
SELECT
    CASE WHEN seq % 2 = 0 THEN 'Columbia' ELSE 'New York' END,
    CASE WHEN seq % 2 = 0 THEN 'Campus Station' ELSE 'Times Square Station' END,
    CASE 
        WHEN seq % 3 = 0 THEN 'Sunny'
        WHEN seq % 3 = 1 THEN 'Cloudy'
        ELSE 'Rainy'
    END,
    60 + FLOOR(RAND()*30),
    40 + FLOOR(RAND()*50),
    DATE_ADD('2025-02-01', INTERVAL seq DAY)
FROM (
    SELECT @row := @row + 1 AS seq
    FROM (SELECT 0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1,
         (SELECT 0 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t2,
         (SELECT @row := 0) t3
    LIMIT 44
) numbers;
