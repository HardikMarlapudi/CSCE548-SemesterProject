import java.io.Serializable;

public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;

    private int alertId;
    private String locationName;
    private String message;

    // REQUIRED for JSON/Jackson
    public Alert() {}

    public Alert(String locationName, String message) {
        setLocationName(locationName);
        setMessage(message);
    }

    public int getAlertId() {
        return alertId;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getMessage() {
        return message;
    }

    public void setAlertId(int alertId) {
        if (alertId < 0) {
            throw new IllegalArgumentException("Invalid Alert ID");
        }
        this.alertId = alertId;
    }

    public void setLocationName(String locationName) {
        if (locationName == null || locationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty");
        }
        this.locationName = locationName.trim();
    }

    public void setMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        this.message = message.trim();
    }

    @Override
    public String toString() {
        return alertId + " | Location: " +
               locationName +
               " | Message: " +
               message;
    }
}
