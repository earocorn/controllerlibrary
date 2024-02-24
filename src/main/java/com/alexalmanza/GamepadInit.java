package com.alexalmanza;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

/**
 * Class to initialize necessary objects
 */
public class GamepadInit {

    /**
     * Globally-used gamepad controller
     */
    private Controller gamepad;

    /**
     * Instance of GamepadInit for singleton
     */
    private static GamepadInit instance = null;

    /**
     * Singleton constructor
     */
    private GamepadInit() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.GAMEPAD) {
                gamepad = controller;
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
    public Controller getGamepad() {
        if(gamepad == null) {
            throw new NullPointerException("Gamepad has not been initialized.");
        }
        return gamepad;
    }

}
