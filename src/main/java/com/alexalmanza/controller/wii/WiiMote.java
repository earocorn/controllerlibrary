package com.alexalmanza.controller.wii;

import com.alexalmanza.controller.wii.observer.WiiObserver;
import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.models.ControllerData;
import com.alexalmanza.models.ControllerType;
import motej.Mote;
import motej.MoteFinder;
import motej.MoteFinderListener;

public class WiiMote implements IController {

    private Mote mote;
    private WiiObserver wiiObserver;
    private ControllerData controllerData;
    private boolean connected = true;

    public WiiMote(Mote mote, int id) {
        this.mote = mote;

        mote.addMoteDisconnectedListener(moteDisconnectedEvent -> connected = false);

        controllerData = new ControllerData("WiiMote:" + id, ControllerType.WIIMOTE);
        wiiObserver = new WiiObserver(this, mote);
    }

    @Override
    public boolean isConnected() {
        return mote != null && connected;
    }

    @Override
    public void disconnect() {
        mote.disconnect();
    }

    @Override
    public IObserver getObserver() {
        return wiiObserver;
    }

    @Override
    public ControllerData getControllerData() {
        return controllerData;
    }
}
