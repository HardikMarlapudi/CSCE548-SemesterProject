import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        WeatherBusiness weatherBusiness = new WeatherBusiness();
        AlertBusiness alertBusiness = new AlertBusiness();
        LocationBusiness locationBusiness = new LocationBusiness();

        while (true) {

            try {

                System.out.println("\n=================================");
                System.out.println(" Weather Management System");
                System.out.println("=================================");
                System.out.println("1. View Weather Records");
                System.out.println("2. View Alerts");
                System.out.println("3. View Locations");
                System.out.println("4. Add Location");
                System.out.println("5. Add Alert");
                System.out.println("6. Exit");
                System.out.print("Choice: ");

                int choice;

                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    continue;
                }

                switch (choice) {

                    case 1:

                        System.out.println("\nWeather Records");

                        weatherBusiness.getAllWeatherRecords()
                                .forEach(System.out::println);

                        break;

                    case 2:

                        System.out.println("\nWeather Alerts");

                        alertBusiness.getAllAlerts()
                                .forEach(System.out::println);

                        break;

                    case 3:

                        System.out.println("\nLocations");

                        locationBusiness.getAllLocations()
                                .forEach(System.out::println);

                        break;

                    case 4:

                        System.out.print("City: ");
                        String city = scanner.nextLine().trim();

                        System.out.print("State: ");
                        String state = scanner.nextLine().trim();

                        System.out.print("Country: ");
                        String country = scanner.nextLine().trim();

                        Location location = new Location(
                                0,
                                city,
                                state,
                                country
                        );

                        locationBusiness.addLocation(location);

                        System.out.println("Location added successfully.");

                        break;

                    case 5:

                        System.out.print("Location ID: ");
                        int locationId =
                                Integer.parseInt(scanner.nextLine());

                        System.out.print("Alert Type: ");
                        String alertType =
                                scanner.nextLine().trim();

                        System.out.print("Severity: ");
                        String severity =
                                scanner.nextLine().trim();

                        System.out.print("Description: ");
                        String description =
                                scanner.nextLine().trim();

                        System.out.print("Alert Date (YYYY-MM-DD): ");
                        String alertDate =
                                scanner.nextLine().trim();

                        Alert alert =
                                new Alert(
                                        locationId,
                                        alertType,
                                        severity,
                                        description,
                                        alertDate
                                );

                        alertBusiness.addAlert(alert);

                        System.out.println("Alert added successfully.");

                        break;

                    case 6:

                        System.out.println("Goodbye!");

                        scanner.close();

                        return;

                    default:

                        System.out.println("Invalid choice.");
                }

            }

            catch (Exception e) {

                System.out.println("Error: " + e.getMessage());

            }

        }

    }

}
