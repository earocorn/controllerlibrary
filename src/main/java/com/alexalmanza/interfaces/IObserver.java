package com.alexalmanza.interfaces;

import com.alexalmanza.models.ControllerUpdateListener;
import net.java.games.input.Component;

import java.util.concurrent.ConcurrentHashMap;

public interface IObserver extends Runnable {

    ConcurrentHashMap<Component.Identifier, ControllerUpdateListener> controllerListeners = null;
    void addListener(ControllerUpdateListener listener, Component.Identifier component);
    void removeListener(ControllerUpdateListener listener, Component.Identifier component);
    void doStart();
    void doStop();
    boolean isRunning();
}
