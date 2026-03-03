import java.sql.Date;

public class WeatherRecord {

    private int recordId;
    private String cityName;
    private String stationName;
    private String conditionName;
    private double temperature;
    private int humidity;
    private Date recordDate;

    // =====================
    // Default Constructor
    // =====================
    public WeatherRecord() {}

    // =====================
    // Parameterized Constructor
    // =====================
    public WeatherRecord(String cityName,
                         String stationName,
                         String conditionName,
                         double temperature,
                         int humidity,
                         Date recordDate) {

        setCityName(cityName);
        setStationName(stationName);
        setConditionName(conditionName);
        setTemperature(temperature);
        setHumidity(humidity);
        setRecordDate(recordDate);
    }

    // =====================
    // Getters
    // =====================
    public int getRecordId() {
        return recordId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStationName() {
        return stationName;
    }

    public String getConditionName() {
        return conditionName;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    // =====================
    // Setters with Validation
    // =====================
    public void setRecordId(int recordId) {
        if (recordId < 0)
            throw new IllegalArgumentException("Invalid record ID");

        this.recordId = recordId;
    }

    public void setCityName(String cityName) {
        if (cityName == null || cityName.trim().isEmpty())
            throw new IllegalArgumentException("City name required");

        this.cityName = cityName.trim();
    }

    public void setStationName(String stationName) {
        if (stationName == null || stationName.trim().isEmpty())
            throw new IllegalArgumentException("Station name required");

        this.stationName = stationName.trim();
    }

    public void setConditionName(String conditionName) {
        if (conditionName == null || conditionName.trim().isEmpty())
            throw new IllegalArgumentException("Condition required");

        this.conditionName = conditionName.trim();
    }

    public void setTemperature(double temperature) {
        if (temperature < -100 || temperature > 150)
            throw new IllegalArgumentException("Invalid temperature");

        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        if (humidity < 0 || humidity > 100)
            throw new IllegalArgumentException("Invalid humidity");

        this.humidity = humidity;
    }

    public void setRecordDate(Date recordDate) {
        if (recordDate == null)
            throw new IllegalArgumentException("Date required");

        this.recordDate = recordDate;
    }

    // =====================
    // toString()
    // =====================
    @Override
    public String toString() {
        return recordId +
               " | City: " + cityName +
               " | Station: " + stationName +
               " | Condition: " + conditionName +
               " | Temp: " + temperature +
               " | Humidity: " + humidity +
               " | Date: " + recordDate;
    }
}
