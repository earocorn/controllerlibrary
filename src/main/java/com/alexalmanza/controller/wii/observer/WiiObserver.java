package com.alexalmanza.controller.wii.observer;

import com.alexalmanza.controller.wii.WiiMote;
import com.alexalmanza.controller.wii.identifiers.WiiIdentifier;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.interfaces.ControllerUpdateListener;
import motej.Mote;
import motej.event.AccelerometerEvent;
import motej.event.AccelerometerListener;
import motej.event.CoreButtonEvent;
import motej.event.CoreButtonListener;
import motej.request.ReportModeRequest;
import net.java.games.input.Component;

import java.security.InvalidParameterException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class WiiObserver implements IObserver {

    private ConcurrentHashMap<WiiIdentifier, ControllerUpdateListener> wiiListeners;
    private WiiMote parent;
    private Mote mote;
    private static final Object lock = new Object();
    private Thread worker;
    private String threadName = "WiiObserverThread:";
    private boolean running = false;

    private final CoreButtonListener coreButtonListener = event -> {
        populateButtonOutput(event);

        for(WiiIdentifier key : wiiListeners.keySet()) {
            wiiListeners.get(key).onChange(key.getName(), parent.getControllerData().getOutputs().get(key.getName()));
        }
    };

    private void populateButtonOutput(CoreButtonEvent e) {
        parent.getControllerData().getOutputs().put(WiiIdentifier.A.getName(), e.isButtonAPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().put(WiiIdentifier.B.getName(), e.isButtonBPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().put(WiiIdentifier.MINUS.getName(), e.isButtonMinusPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().put(WiiIdentifier.PLUS.getName(), e.isButtonPlusPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().put(WiiIdentifier.HOME.getName(), e.isButtonHomePressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().put(WiiIdentifier._1.getName(), e.isButtonOnePressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().put(WiiIdentifier._2.getName(), e.isButtonTwoPressed() ? 1.0f : 0.0f);
        float povDirection;
        switch (e.getButton()) {
            case CoreButtonEvent.D_PAD_DOWN -> povDirection = Component.POV.DOWN;
            case CoreButtonEvent.D_PAD_LEFT -> povDirection = Component.POV.LEFT;
            case CoreButtonEvent.D_PAD_RIGHT -> povDirection = Component.POV.RIGHT;
            case CoreButtonEvent.D_PAD_UP -> povDirection = Component.POV.UP;
            default -> povDirection = Component.POV.CENTER;
        }
        parent.getControllerData().getOutputs().put(WiiIdentifier.POV.getName(), povDirection);
        parent.getControllerData().getOutputs().put(WiiIdentifier.NONE.getName(), e.isNoButtonPressed() ? 1.0f : 0.0f);
    }

    private final AccelerometerListener accelerometerListener = event -> {
        populateAccelerometerOutput(event);

        for(WiiIdentifier key : wiiListeners.keySet()) {
            wiiListeners.get(key).onChange(key.getName(), parent.getControllerData().getOutputs().get(key.getName()));
        }
    };

    private void populateAccelerometerOutput(AccelerometerEvent e) {
        parent.getControllerData().getOutputs().put(WiiIdentifier.X_ACCELERATION.getName(), (float) e.getX());
        parent.getControllerData().getOutputs().put(WiiIdentifier.Y_ACCELERATION.getName(), (float) e.getY());
        parent.getControllerData().getOutputs().put(WiiIdentifier.Z_ACCELERATION.getName(), (float) e.getZ());
    }

    public WiiObserver(WiiMote parent, Mote mote) {
        this.parent = parent;
        this.mote = mote;

        threadName += parent.getControllerData().getName();

        wiiListeners = new ConcurrentHashMap<>();
    }

    @Override
    public void addListener(ControllerUpdateListener listener, Component.Identifier component) {
        if(component instanceof WiiIdentifier) {
            wiiListeners.put((WiiIdentifier) component, listener);
        } else {
            throw new InvalidParameterException("Identifier cannot be used on WiiMote. Must use WiiIdentifier.");
        }
    }

    @Override
    public void removeListener(ControllerUpdateListener listener, Component.Identifier component) {
        if(component instanceof WiiIdentifier) {
            wiiListeners.remove(component, listener);
        }
    }

    @Override
    public void doStart() {
        try{
            if(mote == null) {
                throw new IllegalStateException("WiiMote is not initialized.");
            }
            if(worker != null && worker.isAlive()) {
                throw new IllegalStateException("Observer is already listening for events");
            }

            synchronized (this) {
                mote.addCoreButtonListener(coreButtonListener);
                mote.addAccelerometerListener(accelerometerListener);
            }

            // Read Buttons and Accelerometer data from WiiMote
            mote.setReportMode(ReportModeRequest.DATA_REPORT_0x31, true);

            running = true;
            worker = new Thread(this, this.threadName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doStop() {
        running = false;
        System.out.println("Observer stopped");
    }

    @Override
    public boolean isRunning() {
        running = worker.isAlive();
        return running;
    }

    @Override
    public void run() {
        while(running) {
            if(mote == null) {
                doStop();
            }
        }
    }

}
