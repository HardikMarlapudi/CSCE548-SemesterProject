import java.util.List;

public class AlertBusiness {

    private final AlertDAO alertDAO = new AlertDAO();

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
            throw new IllegalArgumentException("Invalid Alert ID.");
        }

        validateAlert(alert);

        alertDAO.updateAlert(alert);
    }

    // =====================
    // DELETE
    // =====================
    public void deleteAlert(int alertId) {

        if (alertId <= 0) {
            throw new IllegalArgumentException("Invalid Alert ID.");
        }

        alertDAO.deleteAlert(alertId);
    }

    // =====================
    // SECURITY VALIDATION
    // =====================
    private void validateAlert(Alert alert) {

        if (alert == null) {
            throw new IllegalArgumentException("Alert cannot be null.");
        }

        int locationId = alert.getLocationId();
        String alertType = alert.getAlertType();
        String severity = alert.getSeverity();
        String description = alert.getDescription();
        String alertDate = alert.getAlertDate();

        // Validate Location ID
        if (locationId <= 0) {
            throw new IllegalArgumentException("Invalid Location ID.");
        }

        // Validate Alert Type
        if (alertType == null || alertType.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert type cannot be empty.");
        }

        if (alertType.length() > 50) {
            throw new IllegalArgumentException("Alert type is too long.");
        }

        // Validate Severity
        if (severity == null || severity.trim().isEmpty()) {
            throw new IllegalArgumentException("Severity cannot be empty.");
        }

        if (severity.length() > 20) {
            throw new IllegalArgumentException("Severity is too long.");
        }

        // Validate Description
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        if (description.length() > 255) {
            throw new IllegalArgumentException("Description cannot exceed 255 characters.");
        }

        // Validate Alert Date
        if (alertDate == null || alertDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert date cannot be empty.");
        }

        if (alertDate.length() > 20) {
            throw new IllegalArgumentException("Invalid alert date.");
        }
    }
}
