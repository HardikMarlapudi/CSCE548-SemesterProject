import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AlertService {

    private AlertDAO dao = new AlertDAO();

    public void startService() throws Exception {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8082), 0);

        server.createContext("/alerts", this::handleRequest);

        server.start();

        System.out.println("Alert Service running at http://localhost:8082/alerts");
    }

    private void handleRequest(HttpExchange exchange) throws IOException {

        addCORS(exchange);

        String method = exchange.getRequestMethod();
        String response = "";

        try {

            // ======================
            // OPTIONS (CORS PREFLIGHT)
            // ======================
            if (method.equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // ======================
            // GET ALL ALERTS
            // ======================
            if (method.equalsIgnoreCase("GET")) {

                List<Alert> alerts = dao.getAllAlerts();
                response = JsonUtil.toJson(alerts);
            }

            // ======================
            // ADD ALERT
            // ======================
            else if (method.equalsIgnoreCase("POST")) {

                String body = readBody(exchange);
                Alert alert = JsonUtil.fromJson(body);

                dao.addAlert(alert);

                response = "{\"message\":\"Alert added\"}";
            }

            // ======================
            // UPDATE ALERT
            // ======================
            else if (method.equalsIgnoreCase("PUT")) {

                String body = readBody(exchange);
                Alert alert = JsonUtil.fromJson(body);

                dao.updateAlert(alert);

                response = "{\"message\":\"Alert updated\"}";
            }

            // ======================
            // DELETE ALERT
            // ======================
            else if (method.equalsIgnoreCase("DELETE")) {

                String query = exchange.getRequestURI().getQuery();

                int id = Integer.parseInt(query.split("=")[1]);

                dao.deleteAlert(id);

                response = "{\"message\":\"Alert deleted\"}";
            }

            else {
                response = "{\"error\":\"Unsupported method\"}";
            }

        } catch (Exception e) {
            response = "{\"error\":\"" + e.getMessage() + "\"}";
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(200, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    // ======================
    // SECURE CORS CONFIG
    // ======================
    private void addCORS(HttpExchange exchange) {

        Headers headers = exchange.getResponseHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers",
                "Content-Type");
    }

    // ======================
    // READ REQUEST BODY
    // ======================
    private String readBody(HttpExchange exchange)
            throws IOException {

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(
                                exchange.getRequestBody(),
                                StandardCharsets.UTF_8));

        StringBuilder body = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null)
            body.append(line);

        return body.toString();
    }

    private static class JsonUtil {

        public static String toJson(List<Alert> alerts) {
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < alerts.size(); i++) {
                Alert alert = alerts.get(i);
                json.append("{\"locationName\":\"").append(alert.getLocationName())
                    .append("\",\"message\":\"").append(alert.getMessage()).append("\"}")
                    .append(i < alerts.size() - 1 ? "," : "");
            }
            return json.append("]").toString();
        }

        public static Alert fromJson(String json) {
            String locationName = extractValue(json, "locationName");
            String message = extractValue(json, "message");
            return new Alert(locationName, message);
        }

        private static String extractValue(String json, String key) {
            String pattern = "\"" + key + "\":\"";
            int start = json.indexOf(pattern) + pattern.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }
    }
}
