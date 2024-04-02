package com.alexalmanza.controller.wii.observer;

import com.alexalmanza.controller.wii.WiiMote;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.interfaces.ControllerUpdateListener;
import motej.Mote;
import net.java.games.input.Component;

import java.util.concurrent.ConcurrentHashMap;

public class WiiObserver implements IObserver {

    private ConcurrentHashMap<Component.Identifier, ControllerUpdateListener> wiiListeners;
    private WiiMote parent;
    private Mote mote;
    private static final Object lock = new Object();
    private Thread worker;
    private final String threadName = "WiiObserverThread";

    public WiiObserver(WiiMote parent, Mote mote) {

    }

    @Override
    public void addListener(ControllerUpdateListener listener, Component.Identifier component) {
        wiiListeners.put(component, listener);
    }

    @Override
    public void removeListener(ControllerUpdateListener listener, Component.Identifier component) {
        wiiListeners.remove(component, listener);
    }

    @Override
    public void doStart() {
        try{
            if(mote == null) {
                throw new IllegalStateException("WiiMote is not initialized.");
            }
        }
    }

    @Override
    public void doStop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void run() {

    }
}
