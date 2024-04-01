package com.alexalmanza.interfaces;

import java.util.ArrayList;

public interface IControllerConnection {

    void disconnect();
    ArrayList<IController> getConnectedControllers();
}
