package com.alexalmanza.controller.wii;

import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.models.ControllerData;

public class WiiMote implements IController {

    public WiiMote() {
        try {
            // TODO: connect to wiimote
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public IObserver getObserver() {
        return null;
    }

    @Override
    public ControllerData getControllerData() {
        return null;
    }
}
