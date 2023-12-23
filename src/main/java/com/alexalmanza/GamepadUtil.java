package com.alexalmanza;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;

/**
 * Class to set up an instance of JInput controller environment and initialize the currently connected gamepad
 */
public class GamepadUtil {

    /**
     * The main gamepad controller
     */
    private Controller gamepad;

    /**
     * List of gamepad components associated with the main gamepad controller
     */
    private Component[] gamepadComponents;

    /**
     * Constructor that retrieves the currently connected gamepad controller and gets its components
     */
    public GamepadUtil() {

        // Sample setup from https://jinput.github.io/jinput/
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for(Controller controller : controllers) {
            if(controller.getType() == Controller.Type.GAMEPAD) {
                gamepad = controller;
            }
        }

        assert gamepad != null;

        gamepadComponents = gamepad.getComponents();

    }

    /**
     * Poll the gamepad for updates, to populate data of each gamepad component
     */
    public void pollGamepad() {
        if(gamepad != null) {
            gamepad.poll();
        }
    }

    /**
     * Retrieve a list of the current gamepad's components
     *
     * @return gamepadComponents
     */
    public Component[] getGamepadComponents() {
        return gamepadComponents;
    }

    /**
     * Retrieve the current gamepad
     *
     * @return gamepad
     */
    public Controller getGamepad() {
        return gamepad;
    }

}
