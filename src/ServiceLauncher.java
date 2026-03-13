/*
 * ServiceLauncher
 * Starts all Weather Microservices in separate threads
 */

public class ServiceLauncher {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("Starting Weather Microservices...");
        System.out.println("====================================");

        startWeatherService();
        startAlertService();
        startLocationService();

        System.out.println("All services initialized.");
    }

    /* ================================
       WEATHER SERVICE
       ================================ */

    private static void startWeatherService() {

        Thread weatherThread = new Thread(() -> {

            try {

                WeatherService weatherService = new WeatherService();
                weatherService.startService();

                System.out.println("Weather Service running at http://localhost:8081/weather");

            } catch (Exception e) {

                System.err.println("Weather Service failed to start.");
                e.printStackTrace();

            }

        });

        weatherThread.setName("Weather-Service-Thread");
        weatherThread.start();
    }

    /* ================================
       ALERT SERVICE
       ================================ */

    private static void startAlertService() {

        Thread alertThread = new Thread(() -> {

            try {

                AlertService alertService = new AlertService();
                alertService.startService();

                System.out.println("Alert Service running at http://localhost:8082/alerts");

            } catch (Exception e) {

                System.err.println("Alert Service failed to start.");
                e.printStackTrace();

            }

        });

        alertThread.setName("Alert-Service-Thread");
        alertThread.start();
    }

    /* ================================
       LOCATION SERVICE
       ================================ */

    private static void startLocationService() {

        Thread locationThread = new Thread(() -> {

            try {

                LocationService locationService = new LocationService();
                locationService.startService();

                System.out.println("Location Service running at http://localhost:8083/locations");

            } catch (Exception e) {

                System.err.println("Location Service failed to start.");
                e.printStackTrace();

            }

        });

        locationThread.setName("Location-Service-Thread");
        locationThread.start();
    }
}
