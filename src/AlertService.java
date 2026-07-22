import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AlertService {

    private final AlertBusiness business = new AlertBusiness();

    public void startService() throws IOException {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8082), 0);

        server.createContext("/alerts", this::handleRequest);

        server.setExecutor(null);

        server.start();

        System.out.println(
                "Alert Service running at http://localhost:8082/alerts");
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

                    response =
                            JsonUtil.toJson(
                                    business.getAllAlerts());

                    break;

                case "POST":

                    Alert newAlert =
                            JsonUtil.fromJson(readBody(exchange));

                    business.addAlert(newAlert);

                    status = 201;

                    response =
                            "{\"message\":\"Alert created successfully.\"}";

                    break;

                case "PUT":

                    Alert updatedAlert =
                            JsonUtil.fromJson(readBody(exchange));

                    business.updateAlert(updatedAlert);

                    response =
                            "{\"message\":\"Alert updated successfully.\"}";

                    break;

                case "DELETE":

                    String query =
                            exchange.getRequestURI().getQuery();

                    if (query == null || !query.startsWith("id=")) {

                        throw new IllegalArgumentException(
                                "Missing alert id.");

                    }

                    int id =
                            Integer.parseInt(query.substring(3));

                    business.deleteAlert(id);

                    response =
                            "{\"message\":\"Alert deleted successfully.\"}";

                    break;

                default:

                    status = 405;

                    response =
                            "{\"error\":\"HTTP method not supported.\"}";
            }

        }

        catch (IllegalArgumentException e) {

            status = 400;

            response =
                    "{\"error\":\"" +
                            e.getMessage() +
                            "\"}";
        } catch (Exception e) {

                e.printStackTrace();
            
                status = 500;
            
                response =
                    "{\"error\":\"" + e.getMessage() + "\"}";
        }

        byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(status, bytes.length);

        try (OutputStream os =
                     exchange.getResponseBody()) {

            os.write(bytes);
        }
    }

    private void addCORS(HttpExchange exchange) {

        Headers headers =
                exchange.getResponseHeaders();

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

        StringBuilder body =
                new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {

            body.append(line);

        }

        return body.toString();
    }

    // ==========================================
    // JSON Utility
    // ==========================================

    private static class JsonUtil {

        public static String toJson(List<Alert> alerts) {

            StringBuilder json =
                    new StringBuilder("[");

            for (int i = 0; i < alerts.size(); i++) {

                Alert a = alerts.get(i);

                json.append("{")
                        .append("\"alertId\":")
                        .append(a.getAlertId())
                        .append(",")

                        .append("\"locationId\":")
                        .append(a.getLocationId())
                        .append(",")

                        .append("\"alertType\":\"")
                        .append(a.getAlertType())
                        .append("\",")

                        .append("\"severity\":\"")
                        .append(a.getSeverity())
                        .append("\",")

                        .append("\"description\":\"")
                        .append(a.getDescription())
                        .append("\",")

                        .append("\"alertDate\":\"")
                        .append(a.getAlertDate())
                        .append("\"")
                        .append("}");

                if (i < alerts.size() - 1) {

                    json.append(",");

                }

            }

            json.append("]");

            return json.toString();
        }

        public static Alert fromJson(String json) {

            int locationId =
                    Integer.parseInt(
                            extract(json, "locationId"));

            String alertType =
                    extract(json, "alertType");

            String severity =
                    extract(json, "severity");

            String description =
                    extract(json, "description");

            String alertDate =
                    extract(json, "alertDate");

            return new Alert(
                    locationId,
                    alertType,
                    severity,
                    description,
                    alertDate);
        }

        private static String extract(
                String json,
                String key) {

            String search =
                    "\"" + key + "\":";

            int start =
                    json.indexOf(search);

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

                int end =
                        json.indexOf('"', start);

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
