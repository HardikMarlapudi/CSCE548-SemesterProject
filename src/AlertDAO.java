import java.sql.*;
import java.util.*;

public class AlertDAO {

    // ======================
    // READ ALL
    // ======================
    public List<Alert> getAllAlerts() {

        List<Alert> alerts = new ArrayList<>();

        String sql = "SELECT alert_id, location_id, alert_type, severity, description FROM alerts";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Alert alert = new Alert(
                    rs.getInt("location_id"),
                    rs.getString("alert_type"),
                    rs.getString("severity"),
                    rs.getString("description")
                );

                alert.setAlertId(rs.getInt("alert_id"));

                alerts.add(alert);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error retrieving alerts", e);
        }

        return alerts;
    }

    // ======================
    // CREATE
    // ======================
    public void addAlert(Alert alert) {

        String sql =
            "INSERT INTO alerts (location_id, alert_type, severity, description) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, alert.getLocationId());
            ps.setString(2, alert.getAlertType());
            ps.setString(3, alert.getSeverity());
            ps.setString(4, alert.getDescription());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding alert", e);
        }
    }

    // ======================
    // UPDATE
    // ======================
    public void updateAlert(Alert alert) {

        String sql =
            "UPDATE alerts SET location_id=?, alert_type=?, severity=?, description=? WHERE alert_id=?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, alert.getLocationId());
            ps.setString(2, alert.getAlertType());
            ps.setString(3, alert.getSeverity());
            ps.setString(4, alert.getDescription());
            ps.setInt(5, alert.getAlertId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating alert", e);
        }
    }

    // ======================
    // DELETE
    // ======================
    public void deleteAlert(int alertId) {

        String sql = "DELETE FROM alerts WHERE alert_id=?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, alertId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting alert", e);
        }
    }
}
