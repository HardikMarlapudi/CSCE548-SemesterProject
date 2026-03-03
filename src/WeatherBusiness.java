import java.util.List;

public class WeatherBusiness {

    private WeatherRecordDAO weatherDAO = new WeatherRecordDAO();

    // =====================
    // READ
    // =====================
    public List<WeatherRecord> getAllWeatherRecords() {

        try {
            return weatherDAO.getAllRecords();
        } catch (Exception e) {
            throw new RuntimeException("Unable to load weather records");
        }
    }

    // =====================
    // CREATE
    // =====================
    public void addWeatherRecord(WeatherRecord record) {

        validateRecord(record);

        try {
            weatherDAO.addRecord(record);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add weather record");
        }
    }

    // =====================
    // DELETE
    // =====================
    public void deleteWeatherRecord(int id) {

        if (id <= 0)
            throw new IllegalArgumentException("Invalid record ID");

        try {
            weatherDAO.deleteRecord(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete record");
        }
    }

    // =====================
    // VALIDATION (Project 3 Requirement)
    // =====================
    private void validateRecord(WeatherRecord record) {

        if (record == null)
            throw new IllegalArgumentException("Record cannot be null");

        if (record.getCityName() == null ||
            record.getCityName().trim().isEmpty())
            throw new IllegalArgumentException("City required");

        if (record.getStationName() == null ||
            record.getStationName().trim().isEmpty())
            throw new IllegalArgumentException("Station required");

        if (record.getConditionName() == null ||
            record.getConditionName().trim().isEmpty())
            throw new IllegalArgumentException("Condition required");

        if (record.getTemperature() < -100 ||
            record.getTemperature() > 150)
            throw new IllegalArgumentException("Invalid temperature");

        if (record.getHumidity() < 0 ||
            record.getHumidity() > 100)
            throw new IllegalArgumentException("Invalid humidity");
    }
}
