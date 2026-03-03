public class ServiceLauncher {

    public static void main(String[] args) {

        System.out.println("Starting Weather Microservices...");

        startWeatherService();
        startAlertService();
        startLocationService();

        System.out.println("All services initialized.");
    }

    private static void startWeatherService() {
        new Thread(() -> {
            try {
                new WeatherService().startService();
            } catch (Exception e) {
                System.out.println("Weather Service failed to start.");
            }
        }).start();
    }

    private static void startAlertService() {
        new Thread(() -> {
            try {
                new AlertService().startService();
            } catch (Exception e) {
                System.out.println("Alert Service failed to start.");
            }
        }).start();
    }

    private static void startLocationService() {
        new Thread(() -> {
            try {
                new LocationService().startService();
            } catch (Exception e) {
                System.out.println("Location Service failed to start.");
            }
        }).start();
    }
}
