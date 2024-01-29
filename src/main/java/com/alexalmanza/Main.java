package com.alexalmanza;

import net.java.games.input.*;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

public class Main {
    public static Component[] gamepadComponents = null;
    public static Controller gamepad = null;
    public static GamepadUtil gamepadUtil;

    public static void main(String[] args) {
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
        /* Get the available controllers */
        //GamepadObserver gamepadObserver = GamepadObserver.getInstance();

//        gamepadObserver.addListener((identifier, currentValue) -> System.out.println(identifier + " changing to value " + currentValue), Component.Identifier.Button._4);

        new MainFrame();

    }
}