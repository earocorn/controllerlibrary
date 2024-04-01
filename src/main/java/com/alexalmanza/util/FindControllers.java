package com.alexalmanza.util;

import com.alexalmanza.controller.gamepad.GamepadConnection;
import com.alexalmanza.controller.wii.WiiMoteConnection;
import com.alexalmanza.interfaces.IController;
import com.alexalmanza.models.ControllerType;
import net.java.games.input.Event;

import java.util.*;

public class FindControllers {

    // TODO: Search for wii mote or usb controllers
    private ArrayList<IController> controllers;
    public FindControllers(Event event) {
        controllers = new ArrayList<>();

        try {
            GamepadConnection gamepadConnection = new GamepadConnection(event);
            if(gamepadConnection.getConnectedControllers() != null && !gamepadConnection.getConnectedControllers().isEmpty()) {
                controllers.addAll(gamepadConnection.getConnectedControllers());
            }

            WiiMoteConnection wiiMoteConnection = new WiiMoteConnection();
            if(wiiMoteConnection.getConnectedControllers() != null && !wiiMoteConnection.getConnectedControllers().isEmpty()) {
                controllers.addAll(wiiMoteConnection.getConnectedControllers());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<IController> getControllers() {
        return controllers;
    }

    public ArrayList<IController> getControllers(ControllerType controllerType) {
        ArrayList<IController> filteredControllers = null;
        try {
            filteredControllers = (ArrayList<IController>) controllers.stream().filter((controller) -> Objects.equals(controller.getControllerData().getControllerType(), controllerType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filteredControllers;
    }

    public IController getController(String controllerName) {
        return (IController) controllers.stream().filter((controller) -> Objects.equals(controller.getControllerData().getName(), controllerName));
    }

}
