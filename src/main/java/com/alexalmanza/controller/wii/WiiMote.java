package com.alexalmanza.controller.wii;

import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.models.ControllerData;
import motej.Mote;
import motej.MoteFinderListener;

public class WiiMote implements IController {

    public WiiMote() {
        try {

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
