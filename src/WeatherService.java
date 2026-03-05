import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;

public class WeatherService {

    private String city;
    private double temperature;
    private double humidity;

    private WeatherRecordDAO dao = new WeatherRecordDAO();

    public void startService() throws Exception {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/weather", this::handleRequest);

        server.start();

        System.out.println(
            "Weather Service running at http://localhost:8081/weather");
    }

    private void handleRequest(HttpExchange exchange) throws IOException {

        addCORS(exchange);

        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String query = exchange.getRequestURI().getQuery();

        String response = "";
        int statusCode = 200;

        try {

            // =====================
            // HANDLE PREFLIGHT
            // =====================
            if (method.equals("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // =====================
            // GET
            // =====================
            if (method.equals("GET")) {

                if (query != null && query.startsWith("city=")) {

                    String city =
                        URLDecoder.decode(query.substring(5), "UTF-8");

                    response =
                        JsonUtil.toJson(dao.getRecordsByCity(city));

                }
                else if (path.matches("/weather/\\d+")) {

                    int id =
                        Integer.parseInt(path.replace("/weather/", ""));

                    WeatherRecord record =
                        dao.getRecordById(id);

                    if (record == null) {
                        statusCode = 404;
                        response = "Record not found";
                    } else {
                        response = JsonUtil.toJson(java.util.List.of(record));
                    }
                }
                else {
                    response = JsonUtil.toJson(dao.getAllRecords());
                }
            }

            // =====================
            // POST
            // =====================
            else if (method.equals("POST")) {

                String body = readBody(exchange);

                WeatherRecord r =
                        JsonUtil.fromJson(body);

                dao.addRecord(r);

                statusCode = 201;
                response = JsonUtil.toJson(dao.getAllRecords());
            }

            // =====================
            // PUT
            // =====================
            else if (method.equals("PUT")
                    && path.matches("/weather/\\d+")) {

                int id =
                    Integer.parseInt(path.replace("/weather/", ""));

                String body = readBody(exchange);

                WeatherRecord r =
                        JsonUtil.fromJson(body);

                dao.updateRecord(id, r);

                response = JsonUtil.toJson(dao.getAllRecords());
            }

            // =====================
            // DELETE
            // =====================
            else if (method.equals("DELETE")
                && path.matches("/weather/\\d+")) {
        
            int id =
                Integer.parseInt(path.replace("/weather/", ""));
        
            dao.deleteRecord(id);
        
            // reorder IDs after delete
            dao.reorderIds();
        
            response = JsonUtil.toJson(dao.getAllRecords());
            }

            else {
                statusCode = 400;
                response = "Invalid Request";
            }

        }
        catch (Exception e) {

            e.printStackTrace();

            statusCode = 500;
            response = "Server Error";
        }

        exchange.getResponseHeaders()
                .add("Content-Type", "application/json");

        byte[] bytes = response.getBytes();

        exchange.sendResponseHeaders(statusCode, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private void addCORS(HttpExchange exchange) {

        Headers h = exchange.getResponseHeaders();

        h.add("Access-Control-Allow-Origin", "*");
        h.add("Access-Control-Allow-Methods",
                "GET,POST,PUT,DELETE,OPTIONS");
        h.add("Access-Control-Allow-Headers", "Content-Type");
    }

    private String readBody(HttpExchange exchange) throws IOException {

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(exchange.getRequestBody()));

        StringBuilder body = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null)
            body.append(line);

        return body.toString();
    }

    private static class JsonUtil {

        public static WeatherRecord fromJson(String json) {

            String cityName = extractValue(json, "cityName");
            String stationName = extractValue(json, "stationName");
            String conditionName = extractValue(json, "conditionName");
        
            String tempStr = extractValue(json, "temperature");
            double temperature = 0.0;
        
            if (tempStr != null && !tempStr.equals("") && !tempStr.equals("null")) {
                temperature = Double.parseDouble(tempStr);
            }
        
            String humidityStr = extractValue(json, "humidity");
            int humidity = 0;
        
            if (humidityStr != null && !humidityStr.equals("") && !humidityStr.equals("null")) {
                humidity = Integer.parseInt(humidityStr);
            }
        
            String dateStr = extractValue(json, "recordDate");
            java.sql.Date recordDate = null;
        
            if (dateStr != null && !dateStr.equals("") && !dateStr.equals("null")) {
                recordDate = java.sql.Date.valueOf(dateStr);
            }
        
            return new WeatherRecord(
                cityName,
                stationName,
                conditionName,
                temperature,
                humidity,
                recordDate
            );
        }

        private static String extractValue(String json, String key) {
            String pattern = "\"" + key + "\":\"";
            int start = json.indexOf(pattern);
            if (start == -1) {
                pattern = "\"" + key + "\":";
                start = json.indexOf(pattern);
                if (start == -1) return "";
                start += pattern.length();
                int end = json.indexOf(",", start);
                if (end == -1) end = json.indexOf("}", start);
                return json.substring(start, end).trim();
            }
            start += pattern.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }

        public static String toJson(java.util.List<WeatherRecord> list) {

            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
        
            for (int i = 0; i < list.size(); i++) {
        
                WeatherRecord r = list.get(i);
        
                sb.append("  {\n")
                  .append("    \"recordId\": ").append(r.getRecordId()).append(",\n")
                  .append("    \"cityName\": \"").append(r.getCityName()).append("\",\n")
                  .append("    \"stationName\": \"").append(r.getStationName()).append("\",\n")
                  .append("    \"conditionName\": \"").append(r.getConditionName()).append("\",\n")
                  .append("    \"temperature\": ").append(r.getTemperature()).append(",\n")
                  .append("    \"humidity\": ").append(r.getHumidity()).append(",\n")
                  .append("    \"recordDate\": \"").append(r.getRecordDate()).append("\"\n")
                  .append("  }");
        
                if (i < list.size() - 1)
                    sb.append(",");
        
                sb.append("\n");
            }
        
            sb.append("]");
            return sb.toString();
        }
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * @param humidity the humidity to set
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    /**
     * @return WeatherRecordDAO return the dao
     */
    public WeatherRecordDAO getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(WeatherRecordDAO dao) {
        this.dao = dao;
    }

}
