import java.io.Serializable;

public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    private int locationId;
    private String city;
    private String state;
    private String country;

    // ==========================
    // Required by Jackson
    // ==========================
    public Location() {
    }

    // ==========================
    // Constructor
    // ==========================
    public Location(int locationId,
                    String city,
                    String state,
                    String country) {

        setLocationId(locationId);
        setCity(city);
        setState(state);
        setCountry(country);
    }

    // ==========================
    // Getters
    // ==========================
    public int getLocationId() {
        return locationId;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    // ==========================
    // Setters
    // ==========================
    public void setLocationId(int locationId) {

        if (locationId < 0) {
            throw new IllegalArgumentException("Invalid Location ID.");
        }

        this.locationId = locationId;
    }

    public void setCity(String city) {

        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty.");
        }

        if (city.length() > 100) {
            throw new IllegalArgumentException("City name is too long.");
        }

        this.city = city.trim();
    }

    public void setState(String state) {

        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State cannot be empty.");
        }

        if (state.length() > 100) {
            throw new IllegalArgumentException("State name is too long.");
        }

        this.state = state.trim();
    }

    public void setCountry(String country) {

        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty.");
        }

        if (country.length() > 100) {
            throw new IllegalArgumentException("Country name is too long.");
        }

        this.country = country.trim();
    }

    // ==========================
    // Utility
    // ==========================
    @Override
    public String toString() {

        return "Location{" +
                "locationId=" + locationId +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
