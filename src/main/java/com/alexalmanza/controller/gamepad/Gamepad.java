package com.alexalmanza.controller.gamepad;

import com.alexalmanza.controller.gamepad.observer.GamepadObserver;
import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IObserver;
import com.alexalmanza.models.ControllerData;
import com.alexalmanza.models.ControllerType;
import net.java.games.input.Controller;
import net.java.games.input.Event;

import java.util.concurrent.ConcurrentHashMap;

public class Gamepad implements IController {

    private Controller jinputGamepad;
    private GamepadObserver gamepadObserver;
    private ControllerData controllerData;

    public Gamepad(Controller jinputGamepad, Event event) {
        this.jinputGamepad = jinputGamepad;
        controllerData = new ControllerData(jinputGamepad.getName(), ControllerType.GAMEPAD);
        gamepadObserver = new GamepadObserver(this, jinputGamepad, event);
        gamepadObserver.doStart();
    }

    @Override
    public boolean isConnected() {
        return jinputGamepad.poll();
    }

    @Override
    public void disconnect() {

    }

    @Override
    public IObserver getObserver() {
        return gamepadObserver;
    }

    @Override
    public ControllerData getControllerData() {
        return controllerData;
    }
}
