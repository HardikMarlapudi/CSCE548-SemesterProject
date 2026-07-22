import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.List;

/*
 * WeatherService
 * Handles all REST API operations for weather records
 */

public class WeatherService {

    private final WeatherRecordDAO dao = new WeatherRecordDAO();

    /* ===================================== */
    /* START WEATHER SERVICE                 */
    /* ===================================== */

    public void startService() throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/weather", this::handleRequest);

        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());

        server.start();

        System.out.println("Weather Service running at http://localhost:8081/weather");
    }

    /* ===================================== */
    /* HANDLE HTTP REQUESTS                  */
    /* ===================================== */

    private void handleRequest(HttpExchange exchange) throws IOException {

        addCORS(exchange);

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();

        String response = "";
        int statusCode = 200;

        try {

            /* ============================= */
            /* HANDLE PREFLIGHT REQUEST     */
            /* ============================= */

            if (method.equalsIgnoreCase("OPTIONS")) {

                exchange.sendResponseHeaders(204, -1);
                return;

            }

            /* ============================= */
            /* GET REQUEST                  */
            /* ============================= */

            if (method.equalsIgnoreCase("GET")) {

                if (query != null && query.startsWith("locationId=")) {

                    int locationId = Integer.parseInt(
                            URLDecoder.decode(query.substring(11), "UTF-8"));
                
                    List<WeatherRecord> results =
                            dao.getRecordsByLocation(locationId);
                
                    response = JsonUtil.toJson(results);
                }

                else if (path.matches("/weather/\\d+")) {

                    int id =
                        Integer.parseInt(path.replace("/weather/", ""));

                    WeatherRecord record =
                        dao.getRecordById(id);

                    if (record == null) {

                        statusCode = 404;
                        response = "{\"error\":\"Record not found\"}";

                    } else {

                        response =
                            JsonUtil.toJson(List.of(record));
                    }
                }

                else {

                    response =
                        JsonUtil.toJson(dao.getAllRecords());
                }
            }

            /* ============================= */
            /* POST REQUEST                 */
            /* ============================= */

            else if (method.equalsIgnoreCase("POST")) {

                String body = readBody(exchange);

                if (body == null || body.isEmpty()) {

                    statusCode = 400;
                    response = "{\"error\":\"Empty request body\"}";

                } else {

                    WeatherRecord record =
                        JsonUtil.fromJson(body);

                    dao.addRecord(record);

                    statusCode = 201;

                    response =
                        JsonUtil.toJson(dao.getAllRecords());
                }
            }

            /* ============================= */
            /* PUT REQUEST                  */
            /* ============================= */

            else if (method.equalsIgnoreCase("PUT")
                    && path.matches("/weather/\\d+")) {

                int id =
                    Integer.parseInt(path.replace("/weather/", ""));

                String body = readBody(exchange);

                if (body == null || body.isEmpty()) {

                    statusCode = 400;
                    response = "{\"error\":\"Empty request body\"}";

                } else {

                    WeatherRecord record =
                        JsonUtil.fromJson(body);

                    record.setRecordId(id);
                    dao.updateRecord(record);

                    response =
                        JsonUtil.toJson(dao.getAllRecords());
                }
            }

            /* ============================= */
            /* DELETE REQUEST               */
            /* ============================= */

            else if (method.equalsIgnoreCase("DELETE")
                    && path.matches("/weather/\\d+")) {

                int id =
                    Integer.parseInt(path.replace("/weather/", ""));

                dao.deleteRecord(id);

                response =
                    JsonUtil.toJson(dao.getAllRecords());
            }

            else {

                statusCode = 400;
                response = "{\"error\":\"Invalid request\"}";
            }

        }

        catch (Exception e) {

            e.printStackTrace();
        
            statusCode = 500;
        
            response = "{\"error\":\"" + e.getMessage() + "\"}";
        }

        /* ============================= */
        /* SEND RESPONSE                */
        /* ============================= */

        exchange.getResponseHeaders()
                .add("Content-Type", "application/json");

        byte[] bytes = response.getBytes(java.nio.charset.StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(statusCode, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    /* ===================================== */
    /* ADD CORS HEADERS                      */
    /* ===================================== */

    private void addCORS(HttpExchange exchange) {

        Headers headers = exchange.getResponseHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods",
                "GET,POST,PUT,DELETE,OPTIONS");
        headers.add("Access-Control-Allow-Headers",
                "Content-Type");
    }

    /* ===================================== */
    /* READ REQUEST BODY                     */
    /* ===================================== */

    private String readBody(HttpExchange exchange) throws IOException {

        StringBuilder body = new StringBuilder();

        try (BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(exchange.getRequestBody()))) {

            String line;

            while ((line = br.readLine()) != null) {

                body.append(line);
            }
        }

        return body.toString();
    }

    /* ===================================== */
    /* SIMPLE JSON UTILITY                   */
    /* ===================================== */

    private static class JsonUtil {

        public static WeatherRecord fromJson(String json) {

            // Read values from JSON
            int recordId =
                    parseInt(extractValue(json, "recordId"));
        
            String city =
                    extractValue(json, "cityName");
        
            String state =
                    extractValue(json, "stateName");
        
            String conditionName =
                    extractValue(json, "conditionName");
        
            double temperature =
                    parseDouble(extractValue(json, "temperature"));
        
            int humidity =
                    parseInt(extractValue(json, "humidity"));
        
            java.sql.Date recordDate =
                    java.sql.Date.valueOf(
                            extractValue(json, "recordDate"));
        
            // Lookup IDs
            LocationDAO locationDAO = new LocationDAO();
            StationDAO stationDAO = new StationDAO();
            WeatherConditionDAO conditionDAO = new WeatherConditionDAO();
        
            Location location =
        locationDAO.getLocationByCityState(city, state);

/* Automatically create location if it doesn't exist */
if (location == null) {

    Location newLocation =
            new Location(
                    0,
                    city,
                    state,
                    "USA"
            );

    locationDAO.addLocation(newLocation);

    System.out.println("Inserted location: " + city + ", " + state);

    System.out.println(locationDAO.getAllLocations());

    location =
            locationDAO.getLocationByCityState(city, state);
}

/* Find station */
Station station =
        stationDAO.getStationByLocationId(
                location.getLocationId());

/* Automatically create station */
if (station == null) {

    Station newStation =
            new Station(
                    0,
                    location.getLocationId(),
                    city + " Weather Station"
            );

    stationDAO.addStation(newStation);

    station =
            stationDAO.getStationByLocationId(
                    location.getLocationId());
}
        
            WeatherCondition condition =
                    conditionDAO.getConditionByDescription(
                            conditionName);
        
            if (condition == null) {
                throw new IllegalArgumentException(
                        "Weather condition not found: "
                                + conditionName);
            }
        
            return new WeatherRecord(
                    recordId,
                    location.getLocationId(),
                    station.getStationId(),
                    condition.getConditionId(),
                    temperature,
                    humidity,
                    recordDate
            );
        }

        private static double parseDouble(String val) {

            try {
                return Double.parseDouble(val);
            } catch (Exception e) {
                return 0.0;
            }
        }

        private static int parseInt(String val) {

            try {
                return Integer.parseInt(val);
            } catch (Exception e) {
                return 0;
            }
        }

        private static String extractValue(String json, String key) {

            try {

                String pattern = "\"" + key + "\":\"";

                int start = json.indexOf(pattern);

                if (start != -1) {

                    start += pattern.length();
                    int end = json.indexOf("\"", start);

                    return json.substring(start, end);
                }

                pattern = "\"" + key + "\":";

                start = json.indexOf(pattern);

                if (start == -1)
                    return "";

                start += pattern.length();

                int end = json.indexOf(",", start);

                if (end == -1)
                    end = json.indexOf("}", start);

                return json.substring(start, end).trim();

            } catch (Exception e) {

                return "";
            }
        }

        public static String toJson(List<WeatherRecord> list) {

            StringBuilder sb = new StringBuilder();
        
            sb.append("[\n");
        
            for (int i = 0; i < list.size(); i++) {
        
                WeatherRecord r = list.get(i);
        
                sb.append("  {\n")
                  .append("    \"recordId\": ").append(r.getRecordId()).append(",\n")
                  .append("    \"locationId\": ").append(r.getLocationId()).append(",\n")
                  .append("    \"stationId\": ").append(r.getStationId()).append(",\n")
                  .append("    \"conditionId\": ").append(r.getConditionId()).append(",\n")
        
                  .append("    \"cityName\": \"")
                  .append(r.getCityName() == null ? "" : r.getCityName())
                  .append("\",\n")
        
                  .append("    \"stateName\": \"")
                  .append(r.getStateName() == null ? "" : r.getStateName())
                  .append("\",\n")
        
                  .append("    \"stationName\": \"")
                  .append(r.getStationName() == null ? "" : r.getStationName())
                  .append("\",\n")
        
                  .append("    \"conditionName\": \"")
                  .append(r.getConditionName() == null ? "" : r.getConditionName())
                  .append("\",\n")
        
                  .append("    \"temperature\": ")
                  .append(r.getTemperature()).append(",\n")
        
                  .append("    \"humidity\": ")
                  .append(r.getHumidity()).append(",\n")
        
                  .append("    \"recordDate\": \"")
                  .append(r.getRecordDate())
                  .append("\"\n")
        
                  .append("  }");
        
                if (i < list.size() - 1) {
                    sb.append(",");
                }
        
                sb.append("\n");
            }
        
            sb.append("]");
        
            return sb.toString();
        }
    }   
}
