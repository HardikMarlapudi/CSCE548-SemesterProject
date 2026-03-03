import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherConditionDAO {

    // =====================
    // CREATE
    // =====================
    public void addCondition(WeatherCondition condition) throws Exception {

        String sql =
            "INSERT INTO weather_conditions (description) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, condition.getDescription());
            ps.executeUpdate();
        }
    }

    // =====================
    // READ ALL
    // =====================
    public List<WeatherCondition> getAllConditions() throws Exception {

        List<WeatherCondition> conditions = new ArrayList<>();

        String sql =
            "SELECT condition_id, description FROM weather_conditions";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                conditions.add(
                    new WeatherCondition(
                        rs.getInt("condition_id"),
                        rs.getString("description")
                    )
                );
            }
        }

        return conditions;
    }

    // =====================
    // READ BY ID
    // =====================
    public WeatherCondition getConditionById(int conditionId)
            throws Exception {

        if (conditionId <= 0)
            throw new IllegalArgumentException("Invalid condition ID");

        String sql =
            "SELECT condition_id, description FROM weather_conditions WHERE condition_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, conditionId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new WeatherCondition(
                        rs.getInt("condition_id"),
                        rs.getString("description")
                    );
                }
            }
        }

        return null;
    }

    // =====================
    // UPDATE
    // =====================
    public void updateCondition(WeatherCondition condition)
            throws Exception {

        String sql =
            "UPDATE weather_conditions SET description = ? WHERE condition_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, condition.getDescription());
            ps.setInt(2, condition.getConditionId());

            ps.executeUpdate();
        }
    }

    // =====================
    // DELETE
    // =====================
    public void deleteCondition(int conditionId)
            throws Exception {

        if (conditionId <= 0)
            throw new IllegalArgumentException("Invalid condition ID");

        String sql =
            "DELETE FROM weather_conditions WHERE condition_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, conditionId);
            ps.executeUpdate();
        }
    }
}
