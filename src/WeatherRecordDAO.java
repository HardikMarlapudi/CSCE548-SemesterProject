import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherRecordDAO {

    // =====================
    // READ ALL
    // =====================
    public List<WeatherRecord> getAllRecords() throws Exception {

        List<WeatherRecord> list = new ArrayList<>();

        String sql =
            "SELECT record_id, city_name, station_name, condition_name, " +
            "temperature, humidity, record_date FROM weather_records";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                WeatherRecord record = new WeatherRecord(
                        rs.getString("city_name"),
                        rs.getString("station_name"),
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

    // =====================
    // READ BY ID
    // =====================
    public WeatherRecord getRecordById(int id) throws Exception {

        if (id <= 0)
            throw new IllegalArgumentException("Invalid ID");

        String sql =
            "SELECT * FROM weather_records WHERE record_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    WeatherRecord record = new WeatherRecord(
                            rs.getString("city_name"),
                            rs.getString("station_name"),
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

    // =====================
    // CREATE
    // =====================
    public void addRecord(WeatherRecord r) throws Exception {

        String sql =
            "INSERT INTO weather_records " +
            "(city_name, station_name, condition_name, temperature, humidity, record_date) " +
            "VALUES (?,?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getCityName());
            ps.setString(2, r.getStationName());
            ps.setString(3, r.getConditionName());
            ps.setDouble(4, r.getTemperature());
            ps.setInt(5, r.getHumidity());
            ps.setDate(6, r.getRecordDate());

            ps.executeUpdate();
        }
    }

    // =====================
    // UPDATE
    // =====================
    public void updateRecord(int id, WeatherRecord r) throws Exception {

        if (id <= 0)
            throw new IllegalArgumentException("Invalid ID");

        String sql =
            "UPDATE weather_records SET " +
            "city_name=?, station_name=?, condition_name=?, " +
            "temperature=?, humidity=?, record_date=? " +
            "WHERE record_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getCityName());
            ps.setString(2, r.getStationName());
            ps.setString(3, r.getConditionName());
            ps.setDouble(4, r.getTemperature());
            ps.setInt(5, r.getHumidity());
            ps.setDate(6, r.getRecordDate());
            ps.setInt(7, id);

            ps.executeUpdate();
        }
    }

    // =====================
    // DELETE
    // =====================
    public void deleteRecord(int id) throws Exception {

        if (id <= 0)
            throw new IllegalArgumentException("Invalid ID");

        String sql =
            "DELETE FROM weather_records WHERE record_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // =====================
    // READ BY CITY
    // =====================
    public List<WeatherRecord> getRecordsByCity(String city) throws Exception {

        List<WeatherRecord> list = new ArrayList<>();

        String sql =
            "SELECT * FROM weather_records WHERE city_name=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, city);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    WeatherRecord record = new WeatherRecord(
                            rs.getString("city_name"),
                            rs.getString("station_name"),
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
