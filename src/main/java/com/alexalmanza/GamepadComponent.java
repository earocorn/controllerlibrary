package com.alexalmanza;

public enum GamepadComponent {

    Y_AXIS("Y Axis", "y");

    private final String displayName;
    private final String identifier;

    GamepadComponent(String displayName, String identifier) {
        this.displayName = displayName;
        this.identifier = identifier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIdentifier() {
        return identifier;
    }
}
