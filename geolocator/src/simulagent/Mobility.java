package simulagent;

public enum AgentMobility {
    
    FIXED(0, "Fixed"),
    MOBILE(1, "Mobile");
    
    private int index;
    private String description;
    
    private AgentMobility(final int index, final String description) {
        this.index = index;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }
}
