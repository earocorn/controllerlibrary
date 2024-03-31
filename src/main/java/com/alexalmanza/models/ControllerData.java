package com.alexalmanza.models;

import java.util.concurrent.ConcurrentHashMap;

public class ControllerData {

    public ControllerData(String name, ConcurrentHashMap<String, Float> outputs) {
        this.name = name;
        this.outputs = outputs;
    }

    private String name;

    private ConcurrentHashMap<String, Float> outputs;

    public String getName() {
        return name;
    }

    public ConcurrentHashMap<String, Float> getOutputs() {
        return outputs;
    }

}
