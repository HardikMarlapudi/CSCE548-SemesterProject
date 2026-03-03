import java.sql.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        WeatherBusiness weatherBusiness = new WeatherBusiness();
        AlertBusiness alertBusiness = new AlertBusiness();

        while (true) {

            try {

                System.out.println("\nWeather Management System");
                System.out.println("1. View Weather Records");
                System.out.println("2. Add Weather Record");
                System.out.println("3. Delete Weather Record");
                System.out.println("4. View Alerts");
                System.out.println("5. Add Alert");
                System.out.println("6. Exit");
                System.out.print("Choice: ");

                String input = scanner.nextLine();

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a number.");
                    continue;
                }

                switch (choice) {

                    case 1:
                        weatherBusiness.getAllWeatherRecords()
                                .forEach(System.out::println);
                        break;

                    case 2:

                        System.out.print("City name: ");
                        String city = scanner.nextLine().trim();

                        System.out.print("Station name: ");
                        String station = scanner.nextLine().trim();

                        System.out.print("Condition: ");
                        String condition = scanner.nextLine().trim();

                        System.out.print("Temperature: ");
                        double temp;
                        try {
                            temp = Double.parseDouble(scanner.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid temperature.");
                            break;
                        }

                        System.out.print("Humidity: ");
                        int humidity;
                        try {
                            humidity = Integer.parseInt(scanner.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid humidity.");
                            break;
                        }

                        WeatherRecord record =
                                new WeatherRecord(
                                        city,
                                        station,
                                        condition,
                                        temp,
                                        humidity,
                                        new Date(System.currentTimeMillis())
                                );

                        weatherBusiness.addWeatherRecord(record);

                        System.out.println("Weather record added.");
                        break;

                    case 3:

                        System.out.print("Record ID to delete: ");

                        try {
                            int recordId =
                                    Integer.parseInt(scanner.nextLine());

                            weatherBusiness.deleteWeatherRecord(recordId);

                            System.out.println("Weather record deleted.");
                        } catch (Exception e) {
                            System.out.println("Invalid ID.");
                        }

                        break;

                    case 4:
                        alertBusiness.getAllAlerts()
                                .forEach(System.out::println);
                        break;

                    case 5:

                        System.out.print("Location name: ");
                        String location = scanner.nextLine().trim();

                        System.out.print("Alert message: ");
                        String message = scanner.nextLine().trim();

                        alertBusiness.addAlert(
                                new Alert(location, message));

                        System.out.println("Alert added.");
                        break;

                    case 6:
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            }
        }
    }
}
