package com.alexalmanza.model;

import java.util.concurrent.ConcurrentHashMap;

public class ControllerData {

    private ConcurrentHashMap<String, Float> controllerData;

    public ConcurrentHashMap<String, Float> getControllerData() {
        return controllerData;
    }
}
