package com.alexalmanza;

import com.alexalmanza.controller.gamepad.Gamepad;
import com.alexalmanza.interfaces.IController;
import com.alexalmanza.models.ControllerType;
import com.alexalmanza.util.FindControllers;
import net.java.games.input.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static FindControllers findControllers;
    public static Gamepad gamepad;

    public static void main(String[] args) {
        //System.setProperty("net.java.games.input.useDefaultPlugin", "false");
        Event event = new Event();

        findControllers = new FindControllers(event);

        File folder = new File(System.getProperty("java.library.path"));
        File[] listOfFiles = folder.listFiles();
        System.out.println(System.getProperty("java.library.path"));
        if(!folder.exists()) {
            System.out.println("Could not find jiraw folder!");
        } else {
            for (File file : listOfFiles) {
                System.out.println(file.getName());
            }
        }

        List<IController> controllerArrayList = findControllers.getControllers();

        for (IController controller : controllerArrayList) {
            System.out.println(controller.getControllerData().getName());
        }

        gamepad = (Gamepad) controllerArrayList.get(0);

        gamepad.getObserver().addListener((identifier, currentValue) -> System.out.println(identifier + " changing to value " + currentValue), Component.Identifier.Button._4);

        new MainFrame();

    }
}