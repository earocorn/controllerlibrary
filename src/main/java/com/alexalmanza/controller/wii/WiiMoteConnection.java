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
        MoteFinderListener listener = mote -> {
            mote.rumble(20001);
            System.out.println("WiiMote found!");
            motes.add(mote);
        };
            MoteFinder finder = MoteFinder.getMoteFinder();
            finder.addMoteFinderListener(listener);

        try{
            System.out.println("Starting Wii discovery");
            finder.startDiscovery();
            while(motes.isEmpty()) {
                Thread.sleep(1000);
                System.out.println("Searching...");
            }
            finder.stopDiscovery();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(motes.isEmpty()) {
            connectedControllers = null;
            throw new IllegalStateException("No connected WiiMotes.");
        }

        for(Mote mote : motes) {
            WiiMote connectedMote = new WiiMote(mote, motes.indexOf(mote));
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
