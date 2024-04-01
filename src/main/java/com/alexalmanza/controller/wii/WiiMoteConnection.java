package com.alexalmanza.controller.wii;

import com.alexalmanza.interfaces.IController;
import com.alexalmanza.interfaces.IControllerConnection;

import java.util.ArrayList;

public class WiiMoteConnection implements IControllerConnection {

    public WiiMoteConnection() {
        System.out.println("WiiMoteConnection initialized!");
    }
    @Override
    public void disconnect() {

    }

    @Override
    public ArrayList<IController> getConnectedControllers() {
        return null;
    }
}
