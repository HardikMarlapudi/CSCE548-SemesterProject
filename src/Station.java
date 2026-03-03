public class Station {

    private int stationId;
    private String stationName;
    private String locationName;

    // =====================
    // Default Constructor
    // =====================
    public Station() {}

    // =====================
    // Parameterized Constructor
    // =====================
    public Station(int stationId,
                   String stationName,
                   String locationName) {

        setStationId(stationId);
        setStationName(stationName);
        setLocationName(locationName);
    }

    // =====================
    // Getters
    // =====================
    public int getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public String getLocationName() {
        return locationName;
    }

    // =====================
    // Setters with Validation
    // =====================
    public void setStationId(int stationId) {
        if (stationId < 0)
            throw new IllegalArgumentException("Invalid station ID");

        this.stationId = stationId;
    }

    public void setStationName(String stationName) {
        if (stationName == null || stationName.trim().isEmpty())
            throw new IllegalArgumentException("Station name required");

        this.stationName = stationName.trim();
    }

    public void setLocationName(String locationName) {
        if (locationName == null || locationName.trim().isEmpty())
            throw new IllegalArgumentException("Location name required");

        this.locationName = locationName.trim();
    }

    // =====================
    // toString()
    // =====================
    @Override
    public String toString() {
        return stationId + " | " +
               stationName + " | " +
               locationName;
    }
}
