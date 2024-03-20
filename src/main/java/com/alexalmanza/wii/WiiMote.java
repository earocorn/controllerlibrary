package com.alexalmanza.wii;

import com.alexalmanza.model.IController;

public class WiiMote implements IController {

    public WiiMote() {
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
