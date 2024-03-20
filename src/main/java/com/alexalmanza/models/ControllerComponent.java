package com.alexalmanza.models;

import net.java.games.input.Component;

public class ControllerComponent implements Component {
    @Override
    public Identifier getIdentifier() {
        return null;
    }

    @Override
    public boolean isRelative() {
        return false;
    }

    @Override
    public boolean isAnalog() {
        return false;
    }

    @Override
    public float getDeadZone() {
        return 0;
    }

    @Override
    public float getPollData() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }
}
