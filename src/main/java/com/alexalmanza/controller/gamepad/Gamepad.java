package com.alexalmanza.controller.gamepad;

import com.alexalmanza.interfaces.IController;

public class Gamepad implements IController {

    public Gamepad() {
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }
}