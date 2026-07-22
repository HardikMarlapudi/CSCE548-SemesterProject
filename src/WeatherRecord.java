import java.io.Serializable;
import java.sql.Date;

public class WeatherRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    // =====================
    // Database Fields
    // =====================

    private int recordId;
    private int locationId;
    private int stationId;
    private int conditionId;

    private double temperature;
    private int humidity;
    private Date recordDate;

    // =====================
    // Display Fields
    // =====================

    private String cityName;
    private String stateName;
    private String stationName;
    private String conditionName;

    // =====================
    // Default Constructor
    // =====================

    public WeatherRecord() {
    }

    // =====================
    // Constructor with ID
    // =====================

    public WeatherRecord(
            int recordId,
            int locationId,
            int stationId,
            int conditionId,
            double temperature,
            int humidity,
            Date recordDate) {

        setRecordId(recordId);
        setLocationId(locationId);
        setStationId(stationId);
        setConditionId(conditionId);
        setTemperature(temperature);
        setHumidity(humidity);
        setRecordDate(recordDate);
    }

    // =====================
    // Constructor without ID
    // =====================

    public WeatherRecord(
            int locationId,
            int stationId,
            int conditionId,
            double temperature,
            int humidity,
            Date recordDate) {

        this(
                0,
                locationId,
                stationId,
                conditionId,
                temperature,
                humidity,
                recordDate
        );
    }

    // =====================
    // Getters
    // =====================

    public int getRecordId() {
        return recordId;
    }

    public int getLocationId() {
        return locationId;
    }

    public int getStationId() {
        return stationId;
    }

    public int getConditionId() {
        return conditionId;
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

    public String getCityName() {
        return cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public String getStationName() {
        return stationName;
    }

    public String getConditionName() {
        return conditionName;
    }

    // =====================
    // Setters
    // =====================

    public void setRecordId(int recordId) {

        if (recordId < 0) {
            throw new IllegalArgumentException(
                    "Record ID cannot be negative.");
        }

        this.recordId = recordId;
    }

    public void setLocationId(int locationId) {

        if (locationId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid location ID.");
        }

        this.locationId = locationId;
    }

    public void setStationId(int stationId) {

        if (stationId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid station ID.");
        }

        this.stationId = stationId;
    }

    public void setConditionId(int conditionId) {

        if (conditionId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid condition ID.");
        }

        this.conditionId = conditionId;
    }

    public void setTemperature(double temperature) {

        if (temperature < -100 || temperature > 150) {
            throw new IllegalArgumentException(
                    "Temperature must be between -100 and 150.");
        }

        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {

        if (humidity < 0 || humidity > 100) {
            throw new IllegalArgumentException(
                    "Humidity must be between 0 and 100.");
        }

        this.humidity = humidity;
    }

    public void setRecordDate(Date recordDate) {

        if (recordDate == null) {
            throw new IllegalArgumentException(
                    "Record date cannot be null.");
        }

        this.recordDate = recordDate;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    // =====================
    // toString()
    // =====================

    @Override
    public String toString() {

        return "WeatherRecord{" +
                "recordId=" + recordId +
                ", locationId=" + locationId +
                ", stationId=" + stationId +
                ", conditionId=" + conditionId +
                ", cityName='" + cityName + '\'' +
                ", stateName='" + stateName + '\'' +
                ", stationName='" + stationName + '\'' +
                ", conditionName='" + conditionName + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", recordDate=" + recordDate +
                '}';
    }
}
