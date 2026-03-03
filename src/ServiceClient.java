import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServiceClient {

    public static void main(String[] args) {

        System.out.println("Weather Service Response:");
        callService("http://localhost:8081/weather");

        System.out.println("\nAlert Service Response:");
        callService("http://localhost:8082/alerts");

        System.out.println("\nLocation Service Response:");
        callService("http://localhost:8083/locations");
    }

    private static void callService(String urlStr) {

        try {

            URL url = new URL(urlStr);

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            // =====================
            // Secure Configuration
            // =====================
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();

            // =====================
            // Validate Response
            // =====================
            if (status != 200) {
                System.out.println("Request failed. HTTP Status: " + status);
                return;
            }

            // =====================
            // Safe Resource Handling
            // =====================
            try (BufferedReader reader =
                     new BufferedReader(
                         new InputStreamReader(
                             conn.getInputStream(),
                             StandardCharsets.UTF_8))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Service unavailable: " + urlStr);
        }
    }
}
