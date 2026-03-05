import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AlertService {

    private AlertDAO dao = new AlertDAO();

    public void startService() throws Exception {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8082), 0);

        server.createContext("/alerts", this::handleRequest);

        server.start();

        System.out.println(
            "Alert Service running at http://localhost:8082/alerts");
    }

    private void handleRequest(HttpExchange exchange) throws IOException {

        addCORS(exchange);

        String method = exchange.getRequestMethod();
        String response = "";
        int statusCode = 200;

        try {

            // =====================
            // OPTIONS (Preflight)
            // =====================
            if (method.equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // =====================
            // GET
            // =====================
            if (method.equalsIgnoreCase("GET")) {

                List<Alert> alerts = dao.getAllAlerts();

                response = JsonUtil.toJson(alerts);
            }

            // =====================
            // POST
            // =====================
            else if (method.equalsIgnoreCase("POST")) {

                String body = readBody(exchange);

                Alert alert = JsonUtil.fromJson(body);

                dao.addAlert(alert);

                statusCode = 201;
                response = "{\"message\":\"Alert added\"}";
            }

            // =====================
            // PUT
            // =====================
            else if (method.equalsIgnoreCase("PUT")) {

                String body = readBody(exchange);

                Alert alert = JsonUtil.fromJson(body);

                dao.updateAlert(alert);

                response = "{\"message\":\"Alert updated\"}";
            }

            // =====================
            // DELETE
            // =====================
            else if (method.equalsIgnoreCase("DELETE")) {

                String query = exchange.getRequestURI().getQuery();

                int id = Integer.parseInt(query.split("=")[1]);

                dao.deleteAlert(id);

                response = "{\"message\":\"Alert deleted\"}";
            }

            else {
                statusCode = 400;
                response = "{\"error\":\"Unsupported method\"}";
            }

        }
        catch (Exception e) {

            e.printStackTrace();

            statusCode = 500;
            response = "{\"error\":\"Server error\"}";
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(statusCode, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private void addCORS(HttpExchange exchange) {

        Headers headers = exchange.getResponseHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
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

        public static String toJson(List<Alert> list) {

            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
        
            for (int i = 0; i < list.size(); i++) {
        
                Alert a = list.get(i);
        
                sb.append("  {\n")
                  .append("    \"locationName\": \"")
                  .append(a.getLocationName())
                  .append("\",\n")
                  .append("    \"message\": \"")
                  .append(a.getMessage())
                  .append("\"\n")
                  .append("  }");
        
                if (i < list.size() - 1)
                    sb.append(",");
        
                sb.append("\n");
            }
        
            sb.append("]");
        
            return sb.toString();
        }

        public static Alert fromJson(String json) {

            String locationName =
                    extractValue(json, "locationName");

            String message =
                    extractValue(json, "message");

            return new Alert(locationName, message);
        }

        private static String extractValue(String json, String key) {

            String pattern = "\"" + key + "\":\"";

            int start = json.indexOf(pattern);

            if (start == -1)
                return "";

            start += pattern.length();

            int end = json.indexOf("\"", start);

            return json.substring(start, end);
        }
    }
}
