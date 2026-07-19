import java.io.Serializable;

public class WeatherCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    // =====================
    // Fields
    // =====================

    private int conditionId;
    private String description;

    // =====================
    // Default Constructor
    // =====================

    public WeatherCondition() {
    }

    // =====================
    // Constructor with ID
    // =====================

    public WeatherCondition(int conditionId, String description) {
        setConditionId(conditionId);
        setDescription(description);
    }

    // =====================
    // Constructor without ID
    // =====================

    public WeatherCondition(String description) {
        this(0, description);
    }

    // =====================
    // Getters
    // =====================

    public int getConditionId() {
        return conditionId;
    }

    public String getDescription() {
        return description;
    }

    // =====================
    // Setters
    // =====================

    public void setConditionId(int conditionId) {

        // Allow 0 for new records that haven't been inserted yet.
        if (conditionId < 0) {
            throw new IllegalArgumentException(
                    "Condition ID cannot be negative.");
        }

        this.conditionId = conditionId;
    }

    public void setDescription(String description) {

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Weather condition description is required.");
        }

        description = description.trim();

        if (description.length() > 100) {
            throw new IllegalArgumentException(
                    "Weather condition description cannot exceed 100 characters.");
        }

        this.description = description;
    }

    // =====================
    // toString()
    // =====================

    @Override
    public String toString() {

        return "WeatherCondition{" +
                "conditionId=" + conditionId +
                ", description='" + description + '\'' +
                '}';
    }
}
