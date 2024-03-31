package com.alexalmanza.controller.wii.observer;

import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.interfaces.ControllerUpdateListener;
import net.java.games.input.Component;

import java.util.concurrent.ConcurrentHashMap;

public class WiiObserver implements IObserver {

    private ConcurrentHashMap<Component.Identifier, ControllerUpdateListener> wiiListeners;

    private WiiObserver() {

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
