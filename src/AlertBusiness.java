import java.util.List;

public class AlertBusiness {

    private AlertDAO alertDAO = new AlertDAO();

    // =====================
    // READ
    // =====================
    public List<Alert> getAllAlerts() {
        return alertDAO.getAllAlerts();
    }

    // =====================
    // CREATE
    // =====================
    public void addAlert(Alert alert) {

        validateAlert(alert);

        alertDAO.addAlert(alert);
    }

    // =====================
    // UPDATE
    // =====================
    public void updateAlert(Alert alert) {

        if (alert == null || alert.getAlertId() <= 0) {
            throw new IllegalArgumentException("Invalid Alert ID");
        }

        validateAlert(alert);

        alertDAO.updateAlert(alert);
    }

    // =====================
    // DELETE
    // =====================
    public void deleteAlert(int alertId) {

        if (alertId <= 0) {
            throw new IllegalArgumentException("Invalid Alert ID");
        }

        alertDAO.deleteAlert(alertId);
    }

    // =====================
    // SECURITY VALIDATION
    // =====================
    private void validateAlert(Alert alert) {

        if (alert == null) {
            throw new IllegalArgumentException("Alert cannot be null");
        }

        String location = alert.getLocationName();
        String message = alert.getMessage();

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty");
        }

        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        // Prevent extremely large payloads
        if (message.length() > 255) {
            throw new IllegalArgumentException("Message too long");
        }
    }
}
