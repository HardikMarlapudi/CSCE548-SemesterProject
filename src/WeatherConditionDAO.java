import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class WeatherConditionDAO {

    // =====================
    // CREATE
    // =====================
    public void addCondition(WeatherCondition condition) {

        String sql = """
                INSERT INTO weather_conditions
                (description)
                VALUES (?)
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, condition.getDescription());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add weather condition.", e);
        }

    }

    // =====================
    // READ ALL
    // =====================

    public List<WeatherCondition> getAllConditions() {

        List<WeatherCondition> conditions = new ArrayList<>();

        String sql = """
                SELECT
                    condition_id,
                    description
                FROM weather_conditions
                ORDER BY condition_id
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                conditions.add(

                        new WeatherCondition(
                                rs.getInt("condition_id"),
                                rs.getString("description")
                        )

                );

            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve weather conditions.", e);
        }

        return conditions;
    }

    // =====================
    // READ BY ID
    // =====================
    public WeatherCondition getConditionById(int conditionId) {

        if (conditionId <= 0) {

            throw new IllegalArgumentException(
                    "Invalid condition ID."
            );

        }

        String sql = """
                SELECT
                    condition_id,
                    description
                FROM weather_conditions
                WHERE condition_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, conditionId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new WeatherCondition(
                            rs.getInt("condition_id"),
                            rs.getString("description")
                    );

                }

            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve weather condition.", e);
        }

        return null;
    }

// =====================
// READ BY DESCRIPTION
// =====================

public WeatherCondition getConditionByDescription(String description) {

    if (description == null || description.trim().isEmpty()) {
        throw new IllegalArgumentException(
                "Weather condition description is required.");
    }

    String sql = """
            SELECT
                condition_id,
                description
            FROM weather_conditions
            WHERE description = ?
            LIMIT 1
            """;

    try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
    ) {

        ps.setString(1, description.trim());

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                return new WeatherCondition(
                        rs.getInt("condition_id"),
                        rs.getString("description")
                );
            }
        }

    } catch (SQLException e) {
        throw new RuntimeException(
                "Failed to retrieve weather condition by description.", e);
    }

    return null;
}

    // =====================
    // UPDATE
    // =====================
    public void updateCondition(WeatherCondition condition) {

        String sql = """
                UPDATE weather_conditions
                SET description = ?
                WHERE condition_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, condition.getDescription());
            ps.setInt(2, condition.getConditionId());

            ps.executeUpdate();

            if (ps.getUpdateCount() == 0) {

                throw new RuntimeException("Weather condition not found.");

            }

        } catch (SQLException e) { 
            throw new RuntimeException("Failed to update weather condition.", e);
        }
    }

    // =====================
    // DELETE
    // =====================
    public void deleteCondition(int conditionId) {

        if (conditionId <= 0) {

            throw new IllegalArgumentException("Invalid condition ID.");

        }

        String sql = """
                DELETE FROM weather_conditions
                WHERE condition_id = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, conditionId);

            ps.executeUpdate();

            if (ps.getUpdateCount() == 0) {
                throw new RuntimeException("Weather condition not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete weather condition.", e);
        }
    }
}
