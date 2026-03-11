import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LocationService {

    private LocationBusiness business =
            new LocationBusiness();

    public void startService() throws Exception {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8083), 0);

        server.createContext("/locations", this::handleRequest);

        server.start();

        System.out.println(
            "Location Service running at http://localhost:8083/locations");
    }

    private void handleRequest(HttpExchange exchange) throws IOException {

        Headers headers = exchange.getResponseHeaders();

        headers.add("Content-Type", "application/json");
        headers.add("Access-Control-Allow-Origin", "*");

        String response;
        int statusCode = 200;

        try {

            if (!exchange.getRequestMethod()
                    .equalsIgnoreCase("GET")) {

                statusCode = 405;
                response = "{\"error\":\"Method Not Allowed\"}";
            }

            else {

                response =
                        toJson(business.getAllLocations());
            }

        }
        catch (Exception e) {

            statusCode = 500;
            response = "{\"error\":\"Server Error\"}";
        }

        byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(statusCode, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

    private String toJson(List<Location> list) {

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        for (int i = 0; i < list.size(); i++) {

            Location l = list.get(i);

            sb.append("  {\n")
              .append("    \"locationId\": ")
              .append(l.getLocationId())
              .append(",\n")
              .append("    \"city\": \"")
              .append(l.getCity())
              .append("\",\n")
              .append("    \"state\": \"")
              .append(l.getState())
              .append("\",\n")
              .append("    \"country\": \"")
              .append(l.getCountry())
              .append("\"\n")
              .append("  }");

            if (i < list.size() - 1)
                sb.append(",");

            sb.append("\n");
        }

        sb.append("]");

        return sb.toString();
    }
}
