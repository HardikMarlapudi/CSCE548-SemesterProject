import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class LocationService {

    private LocationBusiness business = new LocationBusiness();

    public void startService() throws Exception {

        HttpServer server =
                HttpServer.create(new InetSocketAddress(8083), 0);

        server.createContext("/locations", exchange -> {

            try {

                // =====================
                // Allow ONLY GET requests
                // =====================
                if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    exchange.sendResponseHeaders(405, -1);
                    return;
                }

                // =====================
                // Security Headers
                // =====================
                Headers headers = exchange.getResponseHeaders();

                headers.add("Content-Type", "application/json");
                headers.add("Access-Control-Allow-Origin", "*");
                headers.add("X-Content-Type-Options", "nosniff");
                headers.add("X-Frame-Options", "DENY");
                headers.add("X-XSS-Protection", "1; mode=block");

                // =====================
                // Business Layer Call
                // =====================
                String response =
                        business.getAllLocations().toString();

                byte[] responseBytes =
                        response.getBytes(StandardCharsets.UTF_8);

                exchange.sendResponseHeaders(200, responseBytes.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(responseBytes);
                }

            } catch (Exception e) {

                String error = "Internal Server Error";

                exchange.sendResponseHeaders(500, error.length());

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(error.getBytes());
                }
            }
        });

        server.start();

        System.out.println(
            "Location Service running at http://localhost:8083/locations"
        );
    }
}
