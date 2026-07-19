import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherRecordDAO {

    // =====================
    // CREATE
    // =====================
    public void addRecord(WeatherRecord record) {

        String sql = """
        INSERT INTO weather_records
        (location_id,
         station_id,
         condition_id,
         temperature,
         humidity,
         record_date)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, record.getLocationId());
            ps.setInt(2, record.getStationId());
            ps.setInt(3, record.getConditionId());
            ps.setDouble(4, record.getTemperature());
            ps.setInt(5, record.getHumidity());
            ps.setDate(6, record.getRecordDate());

            ps.executeUpdate();

        }

        catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to add weather record.",
                    e
            );

        }

    }

    // =====================
    // READ ALL
    // =====================
    public List<WeatherRecord> getAllRecords() {

        List<WeatherRecord> records = new ArrayList<>();

        String sql = """
        SELECT
            wr.record_id,
            wr.location_id,
            wr.station_id,
            wr.condition_id,
            l.city,
            l.state,
            s.station_name,
            wc.description AS condition_name,
            wr.temperature,
            wr.humidity,
            wr.record_date
        FROM weather_records wr
        JOIN locations l
            ON wr.location_id = l.location_id
        JOIN stations s
            ON wr.station_id = s.station_id
        JOIN weather_conditions wc
            ON wr.condition_id = wc.condition_id
        ORDER BY wr.record_id
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                WeatherRecord record = new WeatherRecord(
                    rs.getInt("record_id"),
                    rs.getInt("location_id"),
                    rs.getInt("station_id"),
                    rs.getInt("condition_id"),
                    rs.getDouble("temperature"),
                    rs.getInt("humidity"),
                    rs.getDate("record_date")
            );
            
            record.setCityName(rs.getString("city"));
            record.setStateName(rs.getString("state"));
            record.setStationName(rs.getString("station_name"));
            record.setConditionName(rs.getString("condition_name"));
            
            records.add(record);

            }

        }

        catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve weather records.",
                    e
            );

        }

        return records;
    }

    // =====================
    // READ BY ID
    // =====================
    public WeatherRecord getRecordById(int recordId) {

        if (recordId <= 0) {

            throw new IllegalArgumentException(
                    "Invalid record ID."
            );

        }

        String sql = """
        SELECT
            wr.record_id,
            wr.location_id,
            wr.station_id,
            wr.condition_id,
            l.city,
            l.state,
            s.station_name,
            wc.description AS condition_name,
            wr.temperature,
            wr.humidity,
            wr.record_date
        FROM weather_records wr
        JOIN locations l
            ON wr.location_id = l.location_id
        JOIN stations s
            ON wr.station_id = s.station_id
        JOIN weather_conditions wc
            ON wr.condition_id = wc.condition_id
        WHERE wr.record_id = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, recordId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    WeatherRecord record = new WeatherRecord(
                        rs.getInt("record_id"),
                        rs.getInt("location_id"),
                        rs.getInt("station_id"),
                        rs.getInt("condition_id"),
                        rs.getDouble("temperature"),
                        rs.getInt("humidity"),
                        rs.getDate("record_date")
                );
                
                record.setCityName(rs.getString("city"));
                record.setStateName(rs.getString("state"));
                record.setStationName(rs.getString("station_name"));
                record.setConditionName(rs.getString("condition_name"));
                
                return record;

                }

            }

        } catch (SQLException e) {

            throw new RuntimeException("Failed to retrieve weather record.", e);

        }

        return null;
    }

    // =====================
    // UPDATE
    // =====================
    public void updateRecord(WeatherRecord record) {

        String sql = """
        UPDATE weather_records
        SET location_id = ?,
            station_id = ?,
            condition_id = ?,
            temperature = ?,
            humidity = ?,
            record_date = ?
        WHERE record_id = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, record.getLocationId());
            ps.setInt(2, record.getStationId());
            ps.setInt(3, record.getConditionId());
            ps.setDouble(4, record.getTemperature());
            ps.setInt(5, record.getHumidity());
            ps.setDate(6, record.getRecordDate());
            ps.setInt(7, record.getRecordId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Weather record not found.");
            }

        } catch (SQLException e) {

            throw new RuntimeException("Failed to update weather record.", e);

        }

    }

    // =====================
    // DELETE
    // =====================
    public void deleteRecord(int recordId) {

        if (recordId <= 0) {

            throw new IllegalArgumentException(
                    "Invalid record ID."
            );

        }

        String sql = """
        DELETE FROM weather_records
        WHERE record_id = ?
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, recordId);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new RuntimeException("Weather record not found.");
            }

        } catch (SQLException e) {

            throw new RuntimeException("Failed to delete weather record.", e);

        }

    }

    // =====================
    // READ BY LOCATION
    // =====================
    public List<WeatherRecord> getRecordsByLocation(int locationId) {

        List<WeatherRecord> records = new ArrayList<>();

        String sql = """
        SELECT
            wr.record_id,
            wr.location_id,
            wr.station_id,
            wr.condition_id,
            l.city,
            l.state,
            s.station_name,
            wc.description AS condition_name,
            wr.temperature,
            wr.humidity,
            wr.record_date
        FROM weather_records wr
        JOIN locations l
            ON wr.location_id = l.location_id
        JOIN stations s
            ON wr.station_id = s.station_id
        JOIN weather_conditions wc
            ON wr.condition_id = wc.condition_id
        WHERE wr.location_id = ?
        ORDER BY wr.record_id
        """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, locationId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    WeatherRecord record = new WeatherRecord(
                        rs.getInt("record_id"),
                        rs.getInt("location_id"),
                        rs.getInt("station_id"),
                        rs.getInt("condition_id"),
                        rs.getDouble("temperature"),
                        rs.getInt("humidity"),
                        rs.getDate("record_date")
                );
                
                record.setCityName(rs.getString("city"));
                record.setStateName(rs.getString("state"));
                record.setStationName(rs.getString("station_name"));
                record.setConditionName(rs.getString("condition_name"));
                
                records.add(record);

                }

            }

        }

        catch (SQLException e) {

            throw new RuntimeException(
                    "Failed to retrieve weather records.",
                    e
            );

        }

        return records;
    }

}
