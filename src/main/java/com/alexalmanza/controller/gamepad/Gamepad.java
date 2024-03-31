package com.alexalmanza.controller.gamepad;

import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.models.ControllerData;

public class Gamepad implements IController {

    public Gamepad() {
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void registerObserver(IObserver observer) {

    }

    @Override
    public ControllerData getControllerData() {
        return null;
    }
}
