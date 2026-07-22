import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {

    // =====================
    // CREATE
    // =====================

    public void addStation(Station station) {

        String sql = """
                INSERT INTO stations (location_id, station_name)
                VALUES (?, ?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, station.getLocationId());
            ps.setString(2, station.getStationName());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add station.", e);
        }
    }

    // =====================
    // READ ALL
    // =====================

    public List<Station> getAllStations() {

        List<Station> stations = new ArrayList<>();

        String sql = """
                SELECT
                    station_id,
                    location_id,
                    station_name
                FROM stations
                ORDER BY station_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                stations.add(
                        new Station(
                                rs.getInt("station_id"),
                                rs.getInt("location_id"),
                                rs.getString("station_name")
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve stations.", e);
        }

        return stations;
    }

    // =====================
    // READ BY ID
    // =====================

    public Station getStationById(int stationId) {

        String sql = """
                SELECT
                    station_id,
                    location_id,
                    station_name
                FROM stations
                WHERE station_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, stationId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Station(
                            rs.getInt("station_id"),
                            rs.getInt("location_id"),
                            rs.getString("station_name")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve station.", e);
        }

        return null;
    }

    // =====================
    // READ BY LOCATION
    // =====================

    public Station getStationByLocationId(int locationId) {

        String sql = """
                SELECT
                    station_id,
                    location_id,
                    station_name
                FROM stations
                WHERE location_id = ?
                LIMIT 1
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, locationId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Station(
                            rs.getInt("station_id"),
                            rs.getInt("location_id"),
                            rs.getString("station_name")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve station.", e);
        }

        return null;
    }

    // =====================
    // UPDATE
    // =====================

    public void updateStation(Station station) {

        String sql = """
                UPDATE stations
                SET
                    location_id = ?,
                    station_name = ?
                WHERE station_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, station.getLocationId());
            ps.setString(2, station.getStationName());
            ps.setInt(3, station.getStationId());

            ps.executeUpdate();

            if (ps.getUpdateCount() == 0) {
                throw new RuntimeException("Station not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update station.", e);
        }
    }

    // =====================
    // DELETE
    // =====================

    public void deleteStation(int stationId) {

        String sql = """
                DELETE FROM stations
                WHERE station_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, stationId);

            ps.executeUpdate();

            if (ps.getUpdateCount() == 0) {
                throw new RuntimeException("Station not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete station.", e);
        }
    }
}
