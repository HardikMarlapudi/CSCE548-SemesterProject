import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServiceClient {

    private static final String WEATHER_URL = "http://localhost:8081/weather";

    private static final String ALERT_URL = "http://localhost:8082/alerts";

    private static final String LOCATION_URL = "http://localhost:8083/locations";

    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("Weather Management System Client");
        System.out.println("============================================");

        callService("Weather Service", WEATHER_URL);

        callService("Alert Service", ALERT_URL);

        callService("Location Service", LOCATION_URL);

        System.out.println("\n All service request completed.");
    }

    private static void callService(String serviceName, String urlString) {

        HttpURLConnection connection = null;

        try {
            long startTime = System.currentTimeMillis();
            
            URL url = new URL(urlString);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setConnectTimeout(5000);

            connection.setReadTimeout(5000);

            connection.setRequestProperty(
                "Accept",
                "application/json"
            );

            int status = connection.getResponseCode();

            System.out.println("\n------------------------------------------------");
            System.out.println(serviceName);
            System.out.println("----------------------------------------");
            System.out.println("URL: " + urlString);
            System.out.println("HTTP Status: " + status);

            if (status != HttpURLConnection.HTTP_OK) {

                System.out.println("Request failed.");

                return;
            }

            System.out.println("\nResponse:");

            try (
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                        connection.getInputStream(),
                        StandardCharsets.UTF_8))
                ) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                long elasped = System.currentTimeMillis() - startTime;

                System.out.println("\nResponse Time: " + elasped + " ms");
            
            } catch (Exception e) {

                System.out.println("\n----------------------------------------");
                System.out.println(serviceName);
                System.out.println("------------------------------------------");
                System.out.println("Service unavalibale");
                System.out.println("URL: " + urlString);
                System.out.println("Reason: " + e.getMessage());
            }

            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
