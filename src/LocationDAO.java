import java.sql.*;
import java.util.ArrayList;

public class LocationDAO {

    // ======================
    // CREATE
    // ======================
    public void addLocation(Location location) {

        String sql =
            "INSERT INTO locations (city, state, country) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, location.getCity());
            ps.setString(2, location.getState());
            ps.setString(3, location.getCountry());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add location", e);
        }
    }

    // ======================
    // READ ALL
    // ======================
    public ArrayList<Location> getAllLocations() {

        ArrayList<Location> locations = new ArrayList<>();

        String sql =
            "SELECT location_id, city, state, country FROM locations";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                locations.add(new Location(
                        rs.getInt("location_id"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("country")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve locations", e);
        }

        return locations;
    }

    // ======================
    // READ BY ID
    // ======================
    public Location getLocationById(int locationId) {

        String sql =
            "SELECT location_id, city, state, country FROM locations WHERE location_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, locationId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Location(
                            rs.getInt("location_id"),
                            rs.getString("city"),
                            rs.getString("state"),
                            rs.getString("country")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve location", e);
        }

        return null;
    }

    // ======================
    // UPDATE
    // ======================
    public void updateLocation(Location location) {

        String sql =
            "UPDATE locations SET city=?, state=?, country=? WHERE location_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, location.getCity());
            ps.setString(2, location.getState());
            ps.setString(3, location.getCountry());
            ps.setInt(4, location.getLocationId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update location", e);
        }
    }

    // ======================
    // DELETE
    // ======================
    public void deleteLocation(int locationId) {

        String sql =
            "DELETE FROM locations WHERE location_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, locationId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete location", e);
        }
    }
}
