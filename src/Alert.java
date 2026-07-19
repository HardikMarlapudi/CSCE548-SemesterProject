import java.io.Serializable;

public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;

    private int alertId;
    private int locationId;
    private String alertType;
    private String severity;
    private String description;
    private String alertDate;

    // Required by Jackson
    public Alert() {
    }

    public Alert(int locationId,
                 String alertType,
                 String severity,
                 String description,
                 String alertDate) {

        setLocationId(locationId);
        setAlertType(alertType);
        setSeverity(severity);
        setDescription(description);
        setAlertDate(alertDate);
    }

    // Legacy constructor (kept for compatibility)
    public Alert(String locationName, String message) {

        this.locationId = 0;
        this.alertType = locationName;
        this.severity = "N/A";
        this.description = message;
        this.alertDate = "";
    }

    // ==========================
    // Getters
    // ==========================

    public int getAlertId() {
        return alertId;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getAlertType() {
        return alertType;
    }

    public String getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    public String getAlertDate() {
        return alertDate;
    }

    // ==========================
    // Setters
    // ==========================

    public void setAlertId(int alertId) {

        if (alertId < 0) {
            throw new IllegalArgumentException("Invalid Alert ID.");
        }

        this.alertId = alertId;
    }

    public void setLocationId(int locationId) {

        if (locationId <= 0) {
            throw new IllegalArgumentException("Location ID must be greater than zero.");
        }

        this.locationId = locationId;
    }

    public void setAlertType(String alertType) {

        if (alertType == null || alertType.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert type cannot be empty.");
        }

        this.alertType = alertType.trim();
    }

    public void setSeverity(String severity) {

        if (severity == null || severity.trim().isEmpty()) {
            throw new IllegalArgumentException("Severity cannot be empty.");
        }

        this.severity = severity.trim();
    }

    public void setDescription(String description) {

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        this.description = description.trim();
    }

    public void setAlertDate(String alertDate) {

        if (alertDate == null || alertDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert date cannot be empty.");
        }

        this.alertDate = alertDate.trim();
    }

    // ==========================
    // Utility
    // ==========================

    @Override
    public String toString() {

        return "Alert{" +
                "alertId=" + alertId +
                ", locationId=" + locationId +
                ", alertType='" + alertType + '\'' +
                ", severity='" + severity + '\'' +
                ", description='" + description + '\'' +
                ", alertDate='" + alertDate + '\'' +
                '}';
    }
}
