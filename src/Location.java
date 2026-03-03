public class Location {

    private int locationId;
    private String city;
    private String state;
    private String country;

    // ==========================
    // Constructor (Secure)
    // ==========================
    public Location(int locationId, String city, String state, String country) {

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be empty");
        }

        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State cannot be empty");
        }

        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be empty");
        }

        this.locationId = locationId;
        this.city = city.trim();
        this.state = state.trim();
        this.country = country.trim();
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
    // Setters (Secure Validation)
    // ==========================
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setCity(String city) {

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be empty");
        }

        this.city = city.trim();
    }

    public void setState(String state) {

        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State cannot be empty");
        }

        this.state = state.trim();
    }

    public void setCountry(String country) {

        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be empty");
        }

        this.country = country.trim();
    }

    // ==========================
    // toString Override
    // ==========================
    @Override
    public String toString() {
        return locationId + ": " + city + ", " + state + ", " + country;
    }
}
