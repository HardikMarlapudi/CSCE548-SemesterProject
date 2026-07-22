import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LocationService {

    private final LocationBusiness business = new LocationBusiness();

    public void startService() throws IOException {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8083), 0);

        server.createContext("/locations", this::handleRequest);

        server.setExecutor(null);

        server.start();

        System.out.println(
                "Location Service running at http://localhost:8083/locations");
    }

    private void handleRequest(HttpExchange exchange) throws IOException {

        addCORS(exchange);

        String method = exchange.getRequestMethod();

        String response;
        int status = 200;

        try {

            if (method.equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            switch (method.toUpperCase()) {

                case "GET":

                List<Location> locations = business.getAllLocations();

                System.out.println("Locations returned by LocationService:");

                for (Location l : locations) {
                    System.out.println(
                    l.getLocationId() + " "
                    + l.getCity() + ", "
                    + l.getState()
                );
            }

            response = JsonUtil.toJson(locations);

                    response = JsonUtil.toJson(
                            business.getAllLocations());

                    break;

                case "POST":

                    Location newLocation =
                            JsonUtil.fromJson(readBody(exchange));

                    business.addLocation(newLocation);

                    status = 201;

                    response =
                            "{\"message\":\"Location created successfully.\"}";

                    break;

                case "PUT":

                    Location updatedLocation =
                            JsonUtil.fromJson(readBody(exchange));

                    business.updateLocation(updatedLocation);

                    response =
                            "{\"message\":\"Location updated successfully.\"}";

                    break;

                case "DELETE":

                    String query = exchange.getRequestURI().getQuery();

                    if (query == null || !query.startsWith("id=")) {
                        throw new IllegalArgumentException("Missing location id.");
                    }

                    int id = Integer.parseInt(query.substring(3));

                    business.deleteLocation(id);

                    response =
                            "{\"message\":\"Location deleted successfully.\"}";

                    break;

                default:

                    status = 405;

                    response =
                            "{\"error\":\"Method Not Allowed\"}";
            }

        }

        catch (IllegalArgumentException e) {

            status = 400;

            response =
                    "{\"error\":\"" +
                    e.getMessage() +
                    "\"}";
        }

        catch (Exception e) {

            e.printStackTrace();

            status = 500;

            response =
                    "{\"error\":\"Internal server error.\"}";
        }

        byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(status, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {

            os.write(bytes);

        }
    }

    private void addCORS(HttpExchange exchange) {

        Headers headers = exchange.getResponseHeaders();

        headers.add("Content-Type", "application/json");

        headers.add("Access-Control-Allow-Origin", "*");

        headers.add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");

        headers.add(
                "Access-Control-Allow-Headers",
                "Content-Type");
    }

    private String readBody(HttpExchange exchange)
            throws IOException {

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                exchange.getRequestBody(),
                                StandardCharsets.UTF_8));

        StringBuilder body = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {

            body.append(line);

        }

        return body.toString();
    }

    // ======================================
    // JSON Utility
    // ======================================

    private static class JsonUtil {

        public static String toJson(List<Location> locations) {

            StringBuilder json = new StringBuilder("[");

            for (int i = 0; i < locations.size(); i++) {

                Location l = locations.get(i);

                json.append("{")
                        .append("\"locationId\":")
                        .append(l.getLocationId())
                        .append(",")

                        .append("\"city\":\"")
                        .append(l.getCity())
                        .append("\",")

                        .append("\"state\":\"")
                        .append(l.getState())
                        .append("\",")

                        .append("\"country\":\"")
                        .append(l.getCountry())
                        .append("\"")
                        .append("}");

                if (i < locations.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");

            return json.toString();
        }

        public static Location fromJson(String json) {

            int id = 0;

            String idValue = extract(json, "locationId");

            if (!idValue.isEmpty()) {
                id = Integer.parseInt(idValue);
            }

            return new Location(
                    id,
                    extract(json, "city"),
                    extract(json, "state"),
                    extract(json, "country")
            );
        }

        private static String extract(String json, String key) {

            String search = "\"" + key + "\":";

            int start = json.indexOf(search);

            if (start == -1) {
                return "";
            }

            start += search.length();

            while (start < json.length()
                    && Character.isWhitespace(json.charAt(start))) {

                start++;
            }

            if (json.charAt(start) == '"') {

                start++;

                int end = json.indexOf('"', start);

                return json.substring(start, end);

            }

            int end = start;

            while (end < json.length()
                    && Character.isDigit(json.charAt(end))) {

                end++;
            }

            return json.substring(start, end);
        }

    }

}
