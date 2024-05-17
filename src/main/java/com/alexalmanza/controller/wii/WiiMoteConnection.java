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
    private long searchTime;

    public WiiMoteConnection(long searchTime) {
        this.searchTime = searchTime;

        motes = new ArrayList<>();
        MoteFinderListener listener = mote -> {
            mote.rumble(2000L);
            System.out.println("WiiMote found!");
            motes.add(mote);
        };
        finder = MoteFinder.getMoteFinder();
        finder.addMoteFinderListener(listener);

        System.out.println("Starting Wii discovery");
        finder.startDiscovery();

        try {
            Thread.sleep(searchTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Stopping Wii discovery. Found " + motes.size() + " wii motes.");
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

    public long getSearchTime() {
        return this.searchTime;
    }

    public void cancelSearch() {
        finder.stopDiscovery();
        System.out.println("Cancelling discovery");
    }

    @Override
    public void disconnect() {
        cancelSearch();
        for (Mote mote : motes) {
            mote.disconnect();
        }
    }

    @Override
    public ArrayList<IController> getConnectedControllers() {
        return connectedControllers;
    }
}
