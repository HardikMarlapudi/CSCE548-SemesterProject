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

        // NEW MODEL FIELDS
        int locationId = alert.getLocationId();
        String alertType = alert.getAlertType();
        String severity = alert.getSeverity();
        String description = alert.getDescription();

        // Validate location ID
        if (locationId < 0) {
            throw new IllegalArgumentException("Invalid location ID");
        }

        // Validate alert type
        if (alertType == null || alertType.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert type cannot be empty");
        }

        // Validate severity
        if (severity == null || severity.trim().isEmpty()) {
            throw new IllegalArgumentException("Severity cannot be empty");
        }

        // Validate description
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        // Prevent extremely large payloads
        if (description.length() > 255) {
            throw new IllegalArgumentException("Description too long");
        }
    }
}
