import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {

    // =====================
    // READ ALL STATIONS
    // =====================
    public List<Station> getAllStations() throws Exception {

        List<Station> stations = new ArrayList<>();

        String sql =
                "SELECT station_id, station_name, location_name FROM stations";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Station station = new Station(
                        rs.getInt("station_id"),
                        rs.getString("station_name"),
                        rs.getString("location_name")
                );

                stations.add(station);
            }
        }

        return stations;
    }
}
