
public final class ServiceLauncher {

    private ServiceLauncher() {

    }

    public static void main(String[] args) {
        
        System.out.println("Starting Weather management Services.");

        startService(
            "Weather Service",
            "Weather-Service-Thread",
            () -> {
                WeatherService service = new WeatherService();
                service.startService();
            });

        startService(
            "Alert Service",
            "Alert-Service-Thread",
            () -> {
                AlertService service = new AlertService();
                service.startService();
            });

        startService(
            "Location Service",
            "Location-Service-Thread",
            () -> {
                LocationService service = new LocationService();
                service.startService();
            });


            System.out.println();
            System.out.println("=======================================");
            System.out.println("All services stated successfully.");
            System.out.println("Weather Service : http://localhost:8081/weather");
            System.out.println("Alert Service : http://localhost:8082/alerts");
            System.out.println("Location Service: http://localhost:8083/locations");
            System.out.println();
            System.out.println("Open the frontend (index.html) to begin using the application.");
            
        }

        private static void startService (
            String serviceName,
            String threadName,
            ServiceStarter starter) {

                Thread thread = new Thread(() -> {

                    try {
                        System.out.println("Starting " + serviceName + "...");

                        starter.start();

                        System.out.println(serviceName + " started successfully.");
                    } catch (Exception e) {
                        System.err.println(serviceName + " failed to start.");

                        e.printStackTrace();
                    }
                });

                thread.setName(threadName);

                thread.start();
            }
        @FunctionalInterface
            private interface ServiceStarter {

            void start() throws Exception;
        }
}
