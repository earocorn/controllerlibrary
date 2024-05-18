package com.alexalmanza.controller.wii;

import com.alexalmanza.controller.wii.identifiers.WiiIdentifier;
import com.alexalmanza.controller.wii.observer.WiiObserver;
import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.models.ControllerComponent;
import com.alexalmanza.models.ControllerData;
import com.alexalmanza.models.ControllerType;
import motej.Extension;
import motej.Mote;
import motej.request.ReportModeRequest;
import motejx.extensions.nunchuk.Nunchuk;
import net.java.games.input.Component;

import java.util.ArrayList;

public class WiiMote implements IController {

    private Mote mote;
    private WiiObserver wiiObserver;
    private ControllerData controllerData;
    private boolean connected = true;

    public WiiMote(Mote mote, int id) {
        this.mote = mote;

        mote.addMoteDisconnectedListener(moteDisconnectedEvent -> {
                    connected = false;
                    mote.disconnect();
                    wiiObserver.doStop();
        });

        mote.setPlayerLeds(new boolean[]{id == 0, id == 1, id == 2, id == 3});

        ArrayList<ControllerComponent> components = new ArrayList<>();
        components.add(new ControllerComponent(WiiIdentifier.A.getName(), 0.0f));
        components.add(new ControllerComponent(WiiIdentifier.B.getName(), 0.0f));
        components.add(new ControllerComponent(Component.Identifier.Button.LEFT_THUMB.getName(), 0.0f));
        components.add(new ControllerComponent(Component.Identifier.Button.RIGHT_THUMB.getName(), 0.0f));
        components.add(new ControllerComponent(Component.Identifier.Button.MODE.getName(), 0.0f));
        components.add(new ControllerComponent(WiiIdentifier._1.getName(), 0.0f));
        components.add(new ControllerComponent(WiiIdentifier._2.getName(), 0.0f));
        components.add(new ControllerComponent(WiiIdentifier.POV.getName(), 0.0f));
        components.add(new ControllerComponent(WiiIdentifier.X_ACCELERATION.getName(), 0.0f));
        components.add(new ControllerComponent(WiiIdentifier.Y_ACCELERATION.getName(), 0.0f));
        components.add(new ControllerComponent(WiiIdentifier.Z_ACCELERATION.getName(), 0.0f));

        controllerData = new ControllerData("WiiMote:" + id, ControllerType.WIIMOTE, components);
        wiiObserver = new WiiObserver(this, mote);
        wiiObserver.doStart();
    }

    @Override
    public boolean isConnected() {
        return mote != null && connected;
    }

    @Override
    public void disconnect() {
        mote.setReportMode(ReportModeRequest.DATA_REPORT_0x30);
        mote.disconnect();
    }

    @Override
    public IObserver getObserver() {
        return wiiObserver;
    }

    @Override
    public ControllerData getControllerData() {
        return controllerData;
    }
}
