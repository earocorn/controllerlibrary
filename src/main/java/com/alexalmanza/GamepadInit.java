package com.alexalmanza;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 * Class to initialize necessary objects
 */
public class GamepadInit {

    /**
     * Globally-used default gamepad controller
     */
    private Controller defaultGamepad;

    /**
     * Instance of GamepadInit for singleton
     */
    private static GamepadInit instance = null;

    /**
     * List of all connected gamepad controllers
     */
    private Controller[] gamepads;

    /**
     * Singleton constructor
     */
    private GamepadInit() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        gamepads = controllers;

        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.GAMEPAD) {
                defaultGamepad = controller;
            }
        }
    }

    /**
     * Get current instance of singleton class
     *
     * @return Current instance of GamepadInit
     */
    public static GamepadInit getInstance() {
        if(instance == null) {
            instance = new GamepadInit();
        }
        return instance;
    }

    /**
     * Get current gamepad
     *
     * @return Current gamepad
     */
    public Controller getDefaultGamepad() {
        if(defaultGamepad == null) {
            throw new NullPointerException("Gamepad has not been initialized.");
        }
        return defaultGamepad;
    }

    public Controller[] getAllGamepads() {
        if(gamepads == null) {
            throw new NullPointerException("There are no connected gamepad controllers.");
        }
        return gamepads;
    }

}
