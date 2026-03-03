import java.util.ArrayList;

public class LocationBusiness {

    private LocationDAO locationDAO = new LocationDAO();

    // ==========================
    // READ
    // ==========================
    public ArrayList<Location> getAllLocations() {

        try {
            return locationDAO.getAllLocations();
        } catch (Exception e) {
            throw new RuntimeException("Unable to retrieve locations", e);
        }
    }

    // ==========================
    // CREATE
    // ==========================
    public void addLocation(Location location) {

        validateLocation(location);

        try {
            locationDAO.addLocation(location);
        } catch (Exception e) {
            throw new RuntimeException("Error adding location", e);
        }
    }

    // ==========================
    // UPDATE
    // ==========================
    public void updateLocation(Location location) {

        if (location.getLocationId() <= 0) {
            throw new IllegalArgumentException("Invalid Location ID");
        }

        validateLocation(location);

        try {
            locationDAO.updateLocation(location);
        } catch (Exception e) {
            throw new RuntimeException("Error updating location", e);
        }
    }

    // ==========================
    // DELETE
    // ==========================
    public void deleteLocation(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid Location ID");
        }

        try {
            locationDAO.deleteLocation(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting location", e);
        }
    }

    // ==========================
    // BUSINESS VALIDATION
    // ==========================
    private void validateLocation(Location location) {

        if (location == null)
            throw new IllegalArgumentException("Location cannot be null");

        if (location.getCity().isBlank())
            throw new IllegalArgumentException("City cannot be empty");

        if (location.getState().isBlank())
            throw new IllegalArgumentException("State cannot be empty");

        if (location.getCountry().isBlank())
            throw new IllegalArgumentException("Country cannot be empty");
    }
}
