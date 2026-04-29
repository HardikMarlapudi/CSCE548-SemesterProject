import java.io.Serializable;

public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;

    private int alertId;
    private int locationId;
    private String alertType;
    private String severity;
    private String description;

    // REQUIRED for JSON/Jackson
    public Alert() {}

    public Alert(int locationId, String alertType, String severity, String description) {
        setLocationId(locationId);
        setAlertType(alertType);
        setSeverity(severity);
        setDescription(description);
    }

    public Alert(String locationName, String message) {
        this.locationId = 0; // default since old version didn’t use ID
        this.alertType = locationName;
        this.severity = "N/A";
        this.description = message;
    }

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

    public void setAlertId(int alertId) {
        if (alertId < 0) {
            throw new IllegalArgumentException("Invalid Alert ID");
        }
        this.alertId = alertId;
    }

    public void setLocationId(int locationId) {
        if (locationId < 0) {
            throw new IllegalArgumentException("Invalid Location ID");
        }
        this.locationId = locationId;
    }

    public void setAlertType(String alertType) {
        if (alertType == null || alertType.trim().isEmpty()) {
            throw new IllegalArgumentException("Alert type cannot be empty");
        }
        this.alertType = alertType.trim();
    }

    public void setSeverity(String severity) {
        if (severity == null || severity.trim().isEmpty()) {
            throw new IllegalArgumentException("Severity cannot be empty");
        }
        this.severity = severity.trim();
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description.trim();
    }

    @Override
    public String toString() {
        return alertId +
               " | Location ID: " + locationId +
               " | Type: " + alertType +
               " | Severity: " + severity +
               " | Description: " + description;
    }
}
