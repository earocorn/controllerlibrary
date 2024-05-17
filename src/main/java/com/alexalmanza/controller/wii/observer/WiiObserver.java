package com.alexalmanza.controller.wii.observer;

import com.alexalmanza.controller.wii.WiiMote;
import com.alexalmanza.controller.wii.identifiers.WiiIdentifier;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.interfaces.ControllerUpdateListener;
import com.alexalmanza.models.ControllerComponent;
import motej.Extension;
import motej.Mote;
import motej.event.*;
import motej.request.ReportModeRequest;
import motejx.extensions.nunchuk.*;
import net.java.games.input.Component;

import javax.swing.text.html.Option;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class WiiObserver implements IObserver, ExtensionListener {

    private ConcurrentHashMap<WiiIdentifier, ControllerUpdateListener> wiiListeners;
    private WiiMote parent;
    private Mote mote;
    private Thread worker;
    private String threadName = "WiiObserverThread:";
    private boolean running = false;
    NunchukCalibrationData nunchukCalibrationData = null;
    private ControllerComponent[] nunchukComponents = new ControllerComponent[]{
            new ControllerComponent(Component.Identifier.Button.C.getName(), 0.0f),
            new ControllerComponent(Component.Identifier.Button.Z.getName(), 0.0f),
            new ControllerComponent(Component.Identifier.Axis.X.getName(), 0.0f),
            new ControllerComponent(Component.Identifier.Axis.Y.getName(), 0.0f),
            new ControllerComponent(Component.Identifier.Axis.RX_ACCELERATION.getName(), 0.0f),
            new ControllerComponent(Component.Identifier.Axis.RY_ACCELERATION.getName(), 0.0f),
            new ControllerComponent(Component.Identifier.Axis.RZ_ACCELERATION.getName(), 0.0f)};

    private void applyCallbacks() {
        for(WiiIdentifier key : wiiListeners.keySet()) {
            wiiListeners.get(key).onChange(key.getName(), parent.getControllerData().getValue(key.getName()));
        }
    }
    private final CoreButtonListener coreButtonListener = event -> {
        populateButtonOutput(event);
        applyCallbacks();
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
    }

    private final AccelerometerListener<Mote> accelerometerListener = event -> {
        populateAccelerometerOutput(event);
        applyCallbacks();
    };

    private void populateAccelerometerOutput(AccelerometerEvent<Mote> e) {
        parent.getControllerData().getOutputs().get(8).setValue(e.getX() & 0xff);
        parent.getControllerData().getOutputs().get(9).setValue(e.getY() & 0xff);
        parent.getControllerData().getOutputs().get(10).setValue(e.getZ() & 0xff);
    }

    private final NunchukButtonListener nunchukButtonListener = event -> {
        populateNunchukButtonOutput(event);
        applyCallbacks();
    };

    private void populateNunchukButtonOutput(NunchukButtonEvent e) {
        parent.getControllerData().getOutputs().get(11).setValue(e.isButtonCPressed() ? 1.0f : 0.0f);
        parent.getControllerData().getOutputs().get(12).setValue(e.isButtonZPressed() ? 1.0f : 0.0f);
    }

    private final AnalogStickListener analogStickListener = event -> {
        populateNunchukJoystickOutput(event);
        applyCallbacks();
    };

    private void populateNunchukJoystickOutput(AnalogStickEvent e) {
        // Calculate calibration to match the [-1.0, 1.0] interval of normal controller axes
        // Calibration data example. MaxAnalog = 254, MinAnalog = 254, CenterPoint = 112, 114,
        // Actual x, y axes data: CenterPoint = [124, 123]
        // UP: [121, 217]
        // DOWN: [122, 28]
        // LEFT: [23, 120]
        // RIGHT: [220, 120]
        parent.getControllerData().getOutputs().get(13).setValue((float) ((nunchukCalibrationData.getMaximumAnalogPoint().getX() - e.getPoint().getX())/nunchukCalibrationData.getCenterAnalogPoint().getX());
        parent.getControllerData().getOutputs().get(14).setValue((float) e.getPoint().y);
    }

    private final AccelerometerListener<Nunchuk> nunchukAccelerometerListener = event -> {
        populateNunchukAccelerometerOutput(event);
        applyCallbacks();
    };

    private void populateNunchukAccelerometerOutput(AccelerometerEvent<Nunchuk> e) {
        parent.getControllerData().getOutputs().get(15).setValue(e.getX() & 0xff);
        parent.getControllerData().getOutputs().get(16).setValue(e.getY() & 0xff);
        parent.getControllerData().getOutputs().get(17).setValue(e.getZ() & 0xff);
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
                mote.addExtensionListener(this);
                mote.addCoreButtonListener(coreButtonListener);
                mote.addAccelerometerListener(accelerometerListener);
            }

            // Read Buttons and Accelerometer data from WiiMote
            mote.setReportMode(ReportModeRequest.DATA_REPORT_0x32, true);

            running = true;
            worker = new Thread(this, this.threadName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doStop() {
        running = false;
        mote.setReportMode(ReportModeRequest.DATA_REPORT_0x30);
        mote.disconnect();
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

    @Override
    public void extensionConnected(ExtensionEvent extensionEvent) {
        if(extensionEvent.getExtension() instanceof Nunchuk nunchuk) {
            for(ControllerComponent nunchukComponent : nunchukComponents) {
                parent.getControllerData().getOutputs().add(nunchukComponent);
            }
            mote.setReportMode(ReportModeRequest.DATA_REPORT_0x35, true);
            nunchuk.addNunchukButtonListener(nunchukButtonListener);
            nunchuk.addAnalogStickListener(analogStickListener);
            nunchuk.addAccelerometerListener(nunchukAccelerometerListener);

            Thread calibrationThread = new Thread(() -> {
                while(nunchuk.getCalibrationData() == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(nunchuk.getCalibrationData() !=null) {
                    System.out.println("Nunchuk calibration data: \n"
                            + nunchuk.getCalibrationData().getMaximumAnalogPoint() + ", "
                            + nunchuk.getCalibrationData().getMinimumAnalogPoint() + ", "
                            + nunchuk.getCalibrationData().getCenterAnalogPoint() + ", "
                            + nunchuk.getCalibrationData().getZeroForceX() + ", "
                            + nunchuk.getCalibrationData().getZeroForceY() + ", "
                            + nunchuk.getCalibrationData().getZeroForceZ() + ", "
                            + nunchuk.getCalibrationData().getGravityForceX() + ", "
                            + nunchuk.getCalibrationData().getGravityForceY() + ", "
                            + nunchuk.getCalibrationData().getGravityForceZ() + ", ");
                } else {
                    System.out.println("Null calibration data");
                }
            }, "calibrationThread");
            calibrationThread.start();
        }
    }

    @Override
    public void extensionDisconnected(ExtensionEvent extensionEvent) {
        mote.setReportMode(ReportModeRequest.DATA_REPORT_0x30, true);
        for(ControllerComponent nunchukComponent : nunchukComponents) {
            parent.getControllerData().getOutputs().remove(nunchukComponent);
        }
    }
}
