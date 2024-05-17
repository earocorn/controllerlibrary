package com.alexalmanza.controller.wii.observer;

import com.alexalmanza.controller.wii.WiiMote;
import com.alexalmanza.controller.wii.identifiers.WiiIdentifier;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.interfaces.ControllerUpdateListener;
import com.alexalmanza.models.ControllerComponent;
import motej.Extension;
import motej.Mote;
import motej.event.AccelerometerEvent;
import motej.event.AccelerometerListener;
import motej.event.CoreButtonEvent;
import motej.event.CoreButtonListener;
import motej.request.ReportModeRequest;
import motejx.extensions.nunchuk.Nunchuk;
import net.java.games.input.Component;

import javax.swing.text.html.Option;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class WiiObserver implements IObserver {

    private ConcurrentHashMap<WiiIdentifier, ControllerUpdateListener> wiiListeners;
    private WiiMote parent;
    private Mote mote;
    private Nunchuk nunchuk;
    private Thread worker;
    private String threadName = "WiiObserverThread:";
    private boolean running = false;

    private final CoreButtonListener coreButtonListener = event -> {
        populateButtonOutput(event);

        for(WiiIdentifier key : wiiListeners.keySet()) {
            wiiListeners.get(key).onChange(key.getName(), parent.getControllerData().getValue(key.getName()));
        }
    };

    private void populateButtonOutput(CoreButtonEvent e) {
        parent.getControllerData().getOutputs().get(0).setValue(e.isButtonAPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().get(1).setValue(e.isButtonBPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().get(2).setValue(e.isButtonMinusPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().get(3).setValue(e.isButtonPlusPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().get(4).setValue(e.isButtonHomePressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().get(5).setValue(e.isButtonOnePressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().get(6).setValue(e.isButtonTwoPressed() ? 1.0f : 0.0f);

        if(e.isDPadLeftPressed()) {
            parent.getControllerData().getOutputs().get(7).setValue(Component.POV.LEFT);
        } else
        if(e.isDPadUpPressed()) {
            parent.getControllerData().getOutputs().get(7).setValue(Component.POV.UP);
        } else
        if(e.isDPadRightPressed()) {
            parent.getControllerData().getOutputs().get(7).setValue(Component.POV.RIGHT);
        } else
        if(e.isDPadDownPressed()) {
            parent.getControllerData().getOutputs().get(7).setValue(Component.POV.DOWN);
        } else {
            parent.getControllerData().getOutputs().get(7).setValue(Component.POV.CENTER);
        }
        parent.getControllerData().getOutputs().get(8).setValue(e.isNoButtonPressed() ? 1.0f : 0.0f);
    }

    private final AccelerometerListener accelerometerListener = event -> {
        populateAccelerometerOutput(event);

        for(WiiIdentifier key : wiiListeners.keySet()) {
            wiiListeners.get(key).onChange(key.getName(), parent.getControllerData().getValue(key.getName()));
        }
    };

    private void populateAccelerometerOutput(AccelerometerEvent e) {
        parent.getControllerData().getOutputs().get(9).setValue((float) e.getX());
        parent.getControllerData().getOutputs().get(10).setValue((float) e.getY());
        parent.getControllerData().getOutputs().get(11).setValue((float) e.getZ());
    }

    private void populateNunchukOutput()

    public WiiObserver(WiiMote parent, Mote mote, boolean hasNunchuk) {
        if(hasNunchuk) {
            this.nunchuk = mote.getExtension();
        } else {
            this.nunchuk = null;
        }
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
