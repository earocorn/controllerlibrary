package com.alexalmanza;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;

public class GamepadInit {

    private Event event = null;

    private Controller gamepad;

    private static GamepadInit instance = null;

    private GamepadInit() {
        event = new Event();
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.GAMEPAD) {
                gamepad = controller;
            }
        }
    }

    public static GamepadInit getInstance() {
        if(instance == null) {
            instance = new GamepadInit();
        }
        return instance;
    }

    /**
     * Get current Event for use of JInput library
     *
     * @return current event instance
     */
    public Event getEvent() {
        if(event == null) {
            throw new NullPointerException("Event has not been initialized.");
        }
        return event;
    }

    public Controller getGamepad() {
        if(gamepad == null) {
            throw new NullPointerException("Gamepad has not been initialized.");
        }
        return gamepad;
    }

}
