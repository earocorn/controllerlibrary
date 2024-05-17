package com.alexalmanza.controller.wii;

import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IControllerConnection;
import motej.Mote;
import motej.MoteFinder;
import motej.MoteFinderListener;

import java.util.ArrayList;

public class WiiMoteConnection implements IControllerConnection {

    private ArrayList<Mote> motes;
    private ArrayList<IController> connectedControllers;
    private MoteFinder finder;
    private boolean isSearching = false;

    public WiiMoteConnection() {

        motes = new ArrayList<>();
        MoteFinderListener listener = mote -> {
            mote.rumble(2000L);
            System.out.println("WiiMote found!");
            motes.add(mote);
        };
            finder = MoteFinder.getMoteFinder();
            finder.addMoteFinderListener(listener);

        try {
            System.out.println("Starting Wii discovery");
            finder.startDiscovery();
            isSearching = true;
            while(isSearching) {
                if(!motes.isEmpty()) { isSearching = false; }
                Thread.sleep(1000);
                System.out.println("Searching...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            isSearching = false;
            finder.stopDiscovery();
        }

        if(motes.isEmpty()) {
            connectedControllers = null;
            throw new IllegalStateException("No connected WiiMotes.");
        }

        connectedControllers = new ArrayList<>();

        for(Mote mote : motes) {
            WiiMote connectedMote = new WiiMote(mote, motes.indexOf(mote));
            connectedControllers.add(connectedMote);
        }
    }

    public void cancelSearch() {
        isSearching = false;
        finder.stopDiscovery();
    }

    @Override
    public void disconnect() {
        isSearching = false;
        finder.stopDiscovery();
        for (Mote mote : motes) {
            mote.disconnect();
        }
    }

    @Override
    public ArrayList<IController> getConnectedControllers() {
        return connectedControllers;
    }
}
