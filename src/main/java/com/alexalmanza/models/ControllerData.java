package com.alexalmanza.models;

import java.util.concurrent.ConcurrentHashMap;

public class ControllerData {

    public ControllerData(String controllerName, ControllerType controllerType) {
        this.name = controllerName;
        this.outputs = new ConcurrentHashMap<>();
        this.controllerType = controllerType;
    }

    private String name;

    private ConcurrentHashMap<String, Float> outputs;
    private ControllerType controllerType;

    public String getName() {
        return name;
    }

    public ConcurrentHashMap<String, Float> getOutputs() {
        return outputs;
    }
    public ControllerType getControllerType() { return controllerType; }
    public float getValue(String identifier) { return outputs.get(identifier); }

}
