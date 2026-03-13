import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * WeatherRecordDAO
 * Handles all database operations for Weather Records
 */

public class WeatherRecordDAO {

    /* =====================================
       READ ALL WEATHER RECORDS
       ===================================== */

    public List<WeatherRecord> getAllRecords() throws Exception {

        List<WeatherRecord> list = new ArrayList<>();

        String sql =
            "SELECT record_id, city_name, state_name, condition_name, " +
            "temperature, humidity, record_date " +
            "FROM weather_records ORDER BY record_id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                WeatherRecord record = new WeatherRecord(
                        rs.getString("city_name"),
                        rs.getString("state_name"),
                        rs.getString("condition_name"),
                        rs.getDouble("temperature"),
                        rs.getInt("humidity"),
                        rs.getDate("record_date")
                );

                record.setRecordId(rs.getInt("record_id"));

                list.add(record);
            }
        }

        return list;
    }

    /* =====================================
       READ RECORD BY ID
       ===================================== */

    public WeatherRecord getRecordById(int id) throws Exception {

        if (id <= 0)
            return null;

        String sql =
            "SELECT record_id, city_name, state_name, condition_name, " +
            "temperature, humidity, record_date " +
            "FROM weather_records WHERE record_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    WeatherRecord record = new WeatherRecord(
                            rs.getString("city_name"),
                            rs.getString("state_name"),
                            rs.getString("condition_name"),
                            rs.getDouble("temperature"),
                            rs.getInt("humidity"),
                            rs.getDate("record_date")
                    );

                    record.setRecordId(rs.getInt("record_id"));

                    return record;
                }
            }
        }

        return null;
    }

    /* =====================================
       CREATE NEW RECORD
       ===================================== */

    public void addRecord(WeatherRecord r) throws Exception {

        if (r == null)
            throw new IllegalArgumentException("Weather record cannot be null");

        String sql =
            "INSERT INTO weather_records " +
            "(city_name, state_name, condition_name, temperature, humidity, record_date) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getCityName());
            ps.setString(2, r.getStateName());
            ps.setString(3, r.getConditionName());
            ps.setDouble(4, r.getTemperature());
            ps.setInt(5, r.getHumidity());
            ps.setDate(6, r.getRecordDate());

            ps.executeUpdate();
        }
    }

    /* =====================================
       UPDATE EXISTING RECORD
       ===================================== */

    public void updateRecord(int id, WeatherRecord r) throws Exception {

        if (id <= 0 || r == null)
            throw new IllegalArgumentException("Invalid update request");

        String sql =
            "UPDATE weather_records SET " +
            "city_name=?, state_name=?, condition_name=?, " +
            "temperature=?, humidity=?, record_date=? " +
            "WHERE record_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getCityName());
            ps.setString(2, r.getStateName());
            ps.setString(3, r.getConditionName());
            ps.setDouble(4, r.getTemperature());
            ps.setInt(5, r.getHumidity());
            ps.setDate(6, r.getRecordDate());
            ps.setInt(7, id);

            ps.executeUpdate();
        }
    }

    /* =====================================
       DELETE RECORD
       ===================================== */

    public void deleteRecord(int id) throws Exception {

        if (id <= 0)
            throw new IllegalArgumentException("Invalid ID");

        String sql = "DELETE FROM weather_records WHERE record_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }

        reorderIds();
    }

    /* =====================================
       REORDER IDS AFTER DELETE
       ===================================== */

    public void reorderIds() throws Exception {

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("SET @count = 0");

            stmt.executeUpdate(
                "UPDATE weather_records SET record_id = (@count := @count + 1)"
            );

            stmt.execute(
                "ALTER TABLE weather_records AUTO_INCREMENT = 1"
            );
        }
    }

    /* =====================================
       READ RECORDS BY CITY
       ===================================== */

    public List<WeatherRecord> getRecordsByCity(String city) throws Exception {

        List<WeatherRecord> list = new ArrayList<>();

        if (city == null || city.trim().isEmpty())
            return list;

        String sql =
            "SELECT record_id, city_name, state_name, condition_name, " +
            "temperature, humidity, record_date " +
            "FROM weather_records WHERE city_name=? ORDER BY record_id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, city);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    WeatherRecord record = new WeatherRecord(
                            rs.getString("city_name"),
                            rs.getString("state_name"),
                            rs.getString("condition_name"),
                            rs.getDouble("temperature"),
                            rs.getInt("humidity"),
                            rs.getDate("record_date")
                    );

                    record.setRecordId(rs.getInt("record_id"));

                    list.add(record);
                }
            }
        }

        return list;
    }
}
