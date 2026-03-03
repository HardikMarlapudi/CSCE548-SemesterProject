public class WeatherCondition {

    private int conditionId;
    private String description;

    // =====================
    // Default Constructor
    // =====================
    public WeatherCondition() {}

    // =====================
    // Parameterized Constructor
    // =====================
    public WeatherCondition(int conditionId, String description) {
        setConditionId(conditionId);
        setDescription(description);
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
    // Setters (Validated)
    // =====================
    public void setConditionId(int conditionId) {
        if (conditionId < 0)
            throw new IllegalArgumentException("Invalid condition ID");

        this.conditionId = conditionId;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty())
            throw new IllegalArgumentException("Description required");

        this.description = description.trim();
    }

    // =====================
    // toString
    // =====================
    @Override
    public String toString() {
        return conditionId + ": " + description;
    }
}
