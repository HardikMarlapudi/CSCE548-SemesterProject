import java.util.List;

public class LocationBusiness {

    private final LocationDAO locationDAO = new LocationDAO();


    public List<Location> getAllLocations() {

        try {
            return locationDAO.getAllLocations();
        } catch (Exception e) {
            throw new RuntimeException("Unable to retreive locations.", e);
        }
    }

    // ===========================
    // CREATE
    // ===========================

    public void addLocation(Location location) {

        validateLocation(location);

        try {
            locationDAO.addLocation(location);
        } catch (Exception e) {
            throw new RuntimeException("Error adding location.", e);
        }
    }

    // ===========================
    // UPDATE
    // ===========================

    public void updateLocation(Location location) {

        if(location == null || location.getLocationId() <= 0) {
            throw new RuntimeException("Invalid location ID.");
        }

        validateLocation(location);

        try {
            locationDAO.updateLocation(location);
        } catch (Exception e) {
            throw new RuntimeException("Invalid locaiton ID.");
        }
    }


    // ===========================
    // DELETE
    // ===========================

    public void deleteLocation(int locationId) {

        if(locationId <= 0) {
            throw new RuntimeException("Invalid location ID.");
        }

        try {
            locationDAO.deleteLocation(locationId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting location.", e);
        }
    }

    // ===========================
    // BUSINESS VALIDATION
    // ===========================

    public void validateLocation(Location location) {

        if(location == null) {
            throw new RuntimeException("Location cannot be null.");
        }
        
        String city = location.getCity();
        String state = location.getState();
        String country = location.getCountry();

        // City
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty.");
        }

        if(city.length() > 100) {
            throw new IllegalArgumentException("City cannot exceed 100 characters.");
        }

        // State
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State cannot be empty.");
        }

        if (state.length() > 100) {
            throw new IllegalArgumentException("State name is too long.");
        }

        // Country 
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty.");
        }

        if (country.length() > 100) {
            throw new IllegalArgumentException("Country name is too long.");
        }
    }
}
