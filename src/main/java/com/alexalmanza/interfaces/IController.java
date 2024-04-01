package com.alexalmanza.interfaces;

import com.alexalmanza.models.ControllerData;

public interface IController {

    boolean isConnected();
    void disconnect();
    IObserver getObserver();
    ControllerData getControllerData();

}
