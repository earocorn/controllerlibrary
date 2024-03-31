package com.alexalmanza.interfaces;

import com.alexalmanza.models.ControllerData;

public interface IController {

    void connect();
    void disconnect();
    void registerObserver(IObserver observer);
    ControllerData getControllerData();

}
