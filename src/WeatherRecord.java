import java.sql.Date;

/*
 * WeatherRecord
 * Model class representing a weather record in the system
 */

public class WeatherRecord {

    private int recordId;
    private String cityName;
    private String stateName;
    private String conditionName;
    private double temperature;
    private int humidity;
    private Date recordDate;

    /* =====================================
       DEFAULT CONSTRUCTOR
       ===================================== */

    public WeatherRecord() {}

    /* =====================================
       PARAMETERIZED CONSTRUCTOR
       ===================================== */

    public WeatherRecord(String cityName,
                         String stateName,
                         String conditionName,
                         double temperature,
                         int humidity,
                         Date recordDate) {

        setCityName(cityName);
        setStateName(stateName);
        setConditionName(conditionName);
        setTemperature(temperature);
        setHumidity(humidity);
        setRecordDate(recordDate);
    }

    /* =====================================
       GETTERS
       ===================================== */

    public int getRecordId() {
        return recordId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStateName() {
        return stateName;
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

    /* =====================================
       SETTERS WITH VALIDATION
       ===================================== */

    public void setRecordId(int recordId) {

        if (recordId < 0) {
            throw new IllegalArgumentException("Record ID cannot be negative");
        }

        this.recordId = recordId;
    }

    public void setCityName(String cityName) {

        if (cityName == null || cityName.trim().isEmpty()) {
            throw new IllegalArgumentException("City name is required");
        }

        this.cityName = cityName.trim();
    }

    public void setStateName(String stateName) {

        if (stateName == null || stateName.trim().isEmpty()) {
            throw new IllegalArgumentException("State name is required");
        }

        this.stateName = stateName.trim();
    }

    public void setConditionName(String conditionName) {

        if (conditionName == null || conditionName.trim().isEmpty()) {
            throw new IllegalArgumentException("Weather condition is required");
        }

        this.conditionName = conditionName.trim();
    }

    public void setTemperature(double temperature) {

        if (temperature < -100 || temperature > 150) {
            throw new IllegalArgumentException(
                "Temperature must be between -100°F and 150°F"
            );
        }

        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {

        if (humidity < 0 || humidity > 100) {
            throw new IllegalArgumentException(
                "Humidity must be between 0 and 100"
            );
        }

        this.humidity = humidity;
    }

    public void setRecordDate(Date recordDate) {

        if (recordDate == null) {
            throw new IllegalArgumentException("Record date is required");
        }

        this.recordDate = recordDate;
    }

    /* =====================================
       STRING REPRESENTATION
       ===================================== */

    @Override
    public String toString() {

        return "WeatherRecord{" +
                "recordId=" + recordId +
                ", city='" + cityName + '\'' +
                ", state='" + stateName + '\'' +
                ", condition='" + conditionName + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", recordDate=" + recordDate +
                '}';
    }
}
