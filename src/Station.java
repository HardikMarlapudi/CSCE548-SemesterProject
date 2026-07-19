import java.io.Serializable;

public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    private int stationId;
    private int locationId;
    private String stationName;

    // =====================
    // Constructors
    // =====================

    public Station() {
    }

    public Station(int stationId, int locationId, String stationName) {
        setStationId(stationId);
        setLocationId(locationId);
        setStationName(stationName);
    }

    public Station(int locationId, String stationName) {
        this(0, locationId, stationName);
    }

    // =====================
    // Getters
    // =====================

    public int getStationId() {
        return stationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getStationName() {
        return stationName;
    }

    // =====================
    // Setters
    // =====================

    public void setStationId(int stationId) {

        if (stationId < 0) {
            throw new IllegalArgumentException("Station ID cannot be negative.");
        }

        this.stationId = stationId;
    }

    public void setLocationId(int locationId) {

        if (locationId < 0) {
            throw new IllegalArgumentException("Location ID cannot be negative.");
        }
    
        this.locationId = locationId;
    }

    public void setStationName(String stationName) {

        if (stationName == null || stationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Station name is required.");
        }

        stationName = stationName.trim();

        if (stationName.length() > 100) {
            throw new IllegalArgumentException(
                    "Station name cannot exceed 100 characters.");
        }

        this.stationName = stationName;
    }

    // =====================
    // toString()
    // =====================

    @Override
    public String toString() {

        return "Station{" +
                "stationId=" + stationId +
                ", locationId=" + locationId +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}
