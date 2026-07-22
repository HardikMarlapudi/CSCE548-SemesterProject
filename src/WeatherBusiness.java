import java.util.List;

public class WeatherBusiness {

    private final WeatherRecordDAO weatherDAO = new WeatherRecordDAO();

    // =====================
    // READ ALL
    // =====================
    public List<WeatherRecord> getAllWeatherRecords() {

        return weatherDAO.getAllRecords();
    }

    // =====================
    // READ BY ID
    // =====================
    public WeatherRecord getWeatherRecordById(int recordId) {

        if (recordId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid weather record ID."
            );
        }

        return weatherDAO.getRecordById(recordId);
    }

    // =====================
    // READ BY LOCATION
    // =====================
    public List<WeatherRecord> getWeatherRecordsByLocation(int locationId) {

        if (locationId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid location ID."
            );
        }

        return weatherDAO.getRecordsByLocation(locationId);
    }

    // =====================
    // CREATE
    // =====================
    public void addWeatherRecord(WeatherRecord record) {

        validateWeather(record);

        weatherDAO.addRecord(record);
    }

    // =====================
    // UPDATE
    // =====================
    public void updateWeatherRecord(WeatherRecord record) {

        if (record == null) {
            throw new IllegalArgumentException(
                    "Weather record cannot be null."
            );
        }

        if (record.getRecordId() <= 0) {
            throw new IllegalArgumentException(
                    "Invalid weather record ID."
            );
        }

        validateWeather(record);

        weatherDAO.updateRecord(record);
    }

    // =====================
    // DELETE
    // =====================
    public void deleteWeatherRecord(int recordId) {

        if (recordId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid weather record ID."
            );
        }

        weatherDAO.deleteRecord(recordId);
    }

    // =====================
    // VALIDATION
    // =====================
    private void validateWeather(WeatherRecord record) {

        if (record == null) {
            throw new IllegalArgumentException(
                    "Weather record cannot be null."
            );
        }

        if (record.getLocationId() <= 0) {
            throw new IllegalArgumentException(
                    "A valid location is required."
            );
        }

        if (record.getStationId() <= 0) {
            throw new IllegalArgumentException(
                    "A valid station is required."
            );
        }

        if (record.getConditionId() <= 0) {
            throw new IllegalArgumentException(
                    "A valid weather condition is required."
            );
        }

        if (record.getTemperature() < -100
                || record.getTemperature() > 150) {

            throw new IllegalArgumentException(
                    "Temperature must be between -100 and 150."
            );
        }

        if (record.getHumidity() < 0
                || record.getHumidity() > 100) {

            throw new IllegalArgumentException(
                    "Humidity must be between 0 and 100."
            );
        }

        if (record.getRecordDate() == null) {

            throw new IllegalArgumentException(
                    "Record date is required."
            );
        }
    }
}
