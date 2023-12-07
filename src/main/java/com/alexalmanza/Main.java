package com.alexalmanza;

import net.java.games.input.*;

import java.io.PrintStream;

public class Main {
    public static Component[] gamepadComponents = null;
    public static final Event event = new Event();
    public static Controller gamepad = null;

    public static void main(String[] args) {
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