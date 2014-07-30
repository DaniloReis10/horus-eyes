package br.com.fujitec.simulagent.models;

public enum Mobility {
    
    FIXED(0, "Fixed"),
    MOBILE(1, "Mobile"),
    UNDEFINED(2, "Undefined");
    
    private int index;
    private String description;
    
    private Mobility(final int index, final String description) {
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
