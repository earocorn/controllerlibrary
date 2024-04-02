package com.alexalmanza.controller.wii;

import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IControllerConnection;
import motej.Mote;
import motej.MoteFinder;
import motej.MoteFinderListener;

import java.util.ArrayList;

public class WiiMoteConnection implements IControllerConnection {

    private ArrayList<Mote> motes;
    private ArrayList<WiiMote> connectedControllers;

    public WiiMoteConnection() {

        motes = new ArrayList<>();
        MoteFinderListener listener = new MoteFinderListener() {
            @Override
            public void moteFound(Mote mote) {
                mote.rumble(20001);
                motes.add(mote);
            }

        };
        MoteFinder finder = MoteFinder.getMoteFinder();
        finder.addMoteFinderListener(listener);

        try{
            System.out.println("Starting Wii discovery");
            finder.startDiscovery();
            Thread.sleep(300001);
            finder.stopDiscovery();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(motes.isEmpty()) {
            connectedControllers = null;
            throw new IllegalStateException("No connected WiiMotes.");
        }

        for(Mote mote : motes) {
            WiiMote connectedMote = new WiiMote(mote);
            connectedControllers.add(connectedMote);
        }
    }
    @Override
    public void disconnect() {
        for (Mote mote : motes) {
            mote.disconnect();
        }
    }

    @Override
    public ArrayList<IController> getConnectedControllers() {
        return null;
    }
}
