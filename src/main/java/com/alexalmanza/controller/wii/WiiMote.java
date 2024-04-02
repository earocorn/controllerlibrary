package com.alexalmanza.controller.wii;

import com.alexalmanza.controller.wii.observer.WiiObserver;
import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.models.ControllerData;
import com.alexalmanza.models.ControllerType;
import motej.Mote;
import motej.MoteFinderListener;

public class WiiMote implements IController {

    private Mote mote;
    private WiiObserver wiiObserver;
    private ControllerData controllerData;

    public WiiMote(Mote mote) {
        this.mote = mote;
        controllerData = new ControllerData("WiiMote_" + mote.getBluetoothAddress(), ControllerType.WIIMOTE);
        wiiObserver = new WiiObserver(this, mote);
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
