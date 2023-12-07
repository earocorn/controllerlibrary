package com.alexalmanza;

import net.java.games.input.*;

import java.io.File;
import java.io.PrintStream;

public class Main {
    public static Component[] gamepadComponents = null;
    public static final Event event = new Event();
    public static Controller gamepad = null;

    static{System.setProperty("java.library.path", new File("jiraw").getAbsolutePath());}

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
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        for (int i = 0; i < controllers.length; i++) {
            /* Remember to poll each one */
            controllers[i].poll();
            if(controllers[i].getType()== Controller.Type.GAMEPAD) {
                gamepad = controllers[i];
            }
            /* Get the controllers event queue */
            EventQueue queue = controllers[i].getEventQueue();

            /* For each object in the queue */
            while (queue.getNextEvent(event)) {
                /* Get event component */
                Component comp = event.getComponent();
                /* Process event (your awesome code) */
            }
        }

        if(gamepad == null) {
            System.out.println("There was an error retrieving the gamepad!");
            return;
        }
        gamepadComponents = gamepad.getComponents();

        for(Component comp : gamepadComponents) {
            System.out.println(comp.getName());
        }

        new MainFrame();


    }
}