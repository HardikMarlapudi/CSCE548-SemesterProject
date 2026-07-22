import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class LocationDAO {

    // =======================
    // CREATE
    // =======================
    public void addLocation(Location location) {

        String sql = """
                INSERT INTO locations (city, state, country) VALUES (?, ?, ?)
                """;

        try ( 
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)

        ) {
            ps.setString(1, location.getCity());
            ps.setString(2, location.getState());
            ps.setString(3, location.getCountry());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding location.", e);
        }
    }

    // =======================
    // READ ALL
    // =======================
    public List<Location> getAllLocations() {

        List<Location> location = new ArrayList<>();

        String sql = """
                SELECT 
                    location_id,
                    city,
                    state,
                    country
                FROM locations
                ORDER BY location_id
                """;
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
     
            while(rs.next()) {
                location.add(

                    new Location(
                        rs.getInt("location_id"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("country")
                    )
                );
            }

        } catch (Exception e) {

                throw new RuntimeException(
                    "Error getting all locations.",
                    e
            );
        }
        return location;
    }

    // ========================
    // READ BY ID
    // =======================

    public Location getLocationById(int locationId) {

        String sql = """
                    SELECT
                        location_id,
                        city,
                        state,
                        country
                    FROM locations
                    WHERE location_id = ?
                """;
        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            
            ps.setInt(1, locationId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Location (

                        rs.getInt("location_id"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("country")
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                "Failed to retreive location.", e
            );
        }

        return null;
    }

// ========================
// READ BY CITY & STATE
// ========================

// ========================
// READ BY CITY & STATE
// ========================

public Location getLocationByCityState(String city, String state) {

    String sql = """
            SELECT
                location_id,
                city,
                state,
                country
            FROM locations
            WHERE city = ? AND state = ?
            LIMIT 1
            """;

    try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
    ) {

        ps.setString(1, city);
        ps.setString(2, state);

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
        throw new RuntimeException(
                "Failed to retrieve location by city and state.", e);
    }

    return null;
}

    // ========================
    // UPDATE
    // ========================

    public void updateLocation(Location location) {

        String sql = """
                    UPDATE locations
                    SET city = ?,
                        state = ?,
                        country = ?
                    WHERE location_id = ?
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, location.getCity());
            ps.setString(2, location.getState());
            ps.setString(3, location.getCountry());
            ps.setInt(4, location.getLocationId());

            ps.executeUpdate();

            if (ps.getUpdateCount() == 0) {

                throw new RuntimeException("Location not found.");
            }

        } catch (SQLException e) {

            throw new RuntimeException("Error updating location.", e);
        }
    }

    // =================
    // DELETE
    // =================
    public void deleteLocation(int locationId) {

        String sql = """
                DELETE FROM locations
                    WHERE location_id = ?    
                """;

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
    
    ) {
            
        ps.setInt(1, locationId);
        ps.executeUpdate();

        if (ps.getUpdateCount() == 0) {
            throw new RuntimeException("Location not found.");
        }

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete location.", e);
        }
    }
}
