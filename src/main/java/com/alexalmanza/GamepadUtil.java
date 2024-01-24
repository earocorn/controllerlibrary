package com.alexalmanza;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to set up an instance of JInput controller environment and initialize the currently connected gamepad
 */
public class GamepadUtil {

    /**
     * The main gamepad controller
     */
    private static Controller gamepad;

    /**
     * List of gamepad components associated with the main gamepad controller
     */
    private Component[] gamepadComponents;

    /**
     * Map of joystick sensitivities
     */
    private Map<Component.Identifier, Sensitivity> sensitivityMap;
    private final String ERR_NOT_CONNECTED = "A gamepad is not connected.";

    /**
     * Constructor that retrieves the currently connected gamepad controller and gets its components
     */
    public GamepadUtil() {

        // https://jinput.github.io/jinput/
        // Default environment loaded from native library. Currently, to use native libraries, set java.library.path to the path of all native libraries
        // For example, add argument -Djava.library.path="./jiraw" to launch.* with the jiraw directory containing all the native files
        gamepad = GamepadInit.getInstance().getGamepad();
        //TODO make sure getting gamepad works
        if (gamepad == null) {
            throw new NullPointerException(ERR_NOT_CONNECTED);
        }

        gamepadComponents = gamepad.getComponents();

        sensitivityMap = new ConcurrentHashMap<>();
    }

    /**
     * Check if gamepad is connected.
     *
     * @return True if gamepad is connected, false if gamepad is connected or the gamepad's components are null
     */
    public boolean isConnected() {
        return gamepad != null || gamepadComponents != null;
    }

    /**
     * Poll the gamepad for updates, to populate data of each gamepad component
     */
    public void pollGamepad() {
        if (gamepad != null) {
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
     * Get the data for a component specified by its Identifier
     *
     * @param identifier The component identifier whose data is retrieved
     * @return Float value of the component's poll data
     */
    public float getComponentValue(Component.Identifier identifier) {
        if (gamepad == null) {
            throw new NullPointerException(ERR_NOT_CONNECTED);
        }
        return gamepad.getComponent(identifier).getPollData();
    }

    public float getComponentDeadZone(Component.Identifier identifier) {
        if (gamepad == null) {
            throw new NullPointerException(ERR_NOT_CONNECTED);
        }
        return gamepad.getComponent(identifier).getDeadZone();
    }

    /**
     * Retrieve a list of the components under the Axis Identifier
     *
     * @return Component array of the current gamepad's Axis components
     */
    public Component[] getAxisComponents() {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        Component[] components;
        components = (Component[]) Arrays.stream(gamepadComponents).filter(x -> x.getIdentifier() instanceof Component.Identifier.Axis).toArray();
        return components;
    }

    /**
     * Retrieve a list of the components under the Button Identifier
     *
     * @return Component array of the current gamepad's Button components
     */
    public Component[] getButtonComponents() {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        Component[] components;
        components = (Component[]) Arrays.stream(gamepadComponents).filter(x -> x.getIdentifier() instanceof Component.Identifier.Button).toArray();
        return components;
    }

    /**
     * Retrieve a list of the components under the Key Identifier
     *
     * @return Component array of the current gamepad's Key components
     */
    public Component[] getKeyComponents() {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        Component[] components;
        components = (Component[]) Arrays.stream(gamepadComponents).filter(x -> x.getIdentifier() instanceof Component.Identifier.Key).toArray();
        return components;
    }

    /**
     * Check if a component is currently active such as whether a button is being pressed or if a joystick is being moved
     *
     * @param identifier Identifier of requested component
     * @return True if component's value is not default
     */
    public boolean isComponentActive(Component.Identifier identifier) {
        boolean isActive = true;
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        if (gamepad.getComponent(identifier).getPollData() == gamepad.getComponent(identifier).getDeadZone()) {
            isActive = false;
        }
        return isActive;
    }

    /**
     * Check if gamepad has a certain component
     *
     * @param identifier Identifier of component
     * @return True if the gamepad's components includes the identified component
     */
    public boolean hasComponent(Component.Identifier identifier) {
        boolean isInList = false;
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        for (Component component : gamepadComponents) {
            if (component.getIdentifier() == identifier) {
                isInList = true;
            }
        }
        return isInList;
    }

    /**
     * Retrieve list of components' names
     *
     * @return ArrayList of component names
     */
    public ArrayList<String> getComponentsNamesAsList() {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        ArrayList<String> array = new ArrayList<>();
        for (Component component : gamepadComponents) {
            array.add(component.getName());
        }
        return array;
    }

    /**
     * Retrieve list of components' identifier values as Strings
     *
     * @return ArrayList of identifier Strings
     */
    public ArrayList<String> getComponentsIdentifiersAsList() {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        ArrayList<String> array = new ArrayList<>();
        for (Component component : gamepadComponents) {
            array.add(component.getIdentifier().getName());
        }
        return array;
    }

    /**
     * Retrieve list of components' float values
     *
     * @return ArrayList of raw component data
     */
    public ArrayList<Float> getComponentsDataAsList() {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        ArrayList<Float> array = new ArrayList<>();
        for (Component component : gamepadComponents) {
            array.add(component.getPollData());
        }
        return array;
    }

    /**
     * Check if a Button component is currently being pressed
     *
     * @param identifier Identify which button component
     * @return True if button is currently pressed
     */
    public boolean isButtonPressed(Component.Identifier identifier) {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        boolean pressed = false;
        if (identifier instanceof Component.Identifier.Button) {
            if (hasComponent(identifier)) {
                pressed = gamepad.getComponent(identifier).getPollData() == 1.0f;
            }
        }
        return pressed;
    }

    /**
     * Get left or right trigger pressure identified by the Identifier.Axis.Z component
     *
     * @param isLeft If left trigger pressure is requested
     * @return Value of left or right trigger pressure
     */
    public float getTriggerPressure(boolean isLeft) {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        float pressure = 0.0f;
        if (hasComponent(Component.Identifier.Axis.Z)) {
            Component component = gamepad.getComponent(Component.Identifier.Axis.Z);
            float deadZone = component.getDeadZone();
            float currentValue = component.getPollData();
            if (currentValue != deadZone) {
                if (isLeft) {
                    if (currentValue > deadZone) {
                        pressure = currentValue;
                    }
                } else {
                    if (currentValue < deadZone) {
                        pressure = currentValue;
                    }
                }
            } else {
                pressure = deadZone;
            }
        }
        return pressure;
    }

    /**
     * Return value of component that's passed through sensitivity map
     *
     * @param identifier
     * @return
     */
    public float getValueWithSensitivity(Component.Identifier identifier) {
        float value = 0.0f;
        float incremental = 0.5f;
        float componentValue = getComponentValue(identifier);
        float componentDeadZone = getComponentDeadZone(identifier);
        if (sensitivityMap == null || sensitivityMap.isEmpty()) {
            return value;
        }
        Sensitivity componentSensitivity = sensitivityMap.get(identifier);
        if(componentSensitivity == Sensitivity.NULL || componentSensitivity == null) {
            return value;
        }
        value = (componentSensitivity.ordinal() >= Sensitivity.MEDIUM.ordinal ()) ? (float) Math.pow(componentValue, componentSensitivity.getSensitivityModifier()) : componentValue * componentSensitivity.getSensitivityModifier();
        return (value > componentDeadZone) ? value : componentValue;
    }

    /**
     * Set the sensitivity value for the identified component
     *
     * @param identifier  Identifies component
     * @param sensitivity Sensitivity value
     */
    public void setSensitivity(Component.Identifier identifier, Sensitivity sensitivity) {
        if (sensitivityMap == null) {
            throw new NullPointerException("Sensitivity hashmap is null");
        }
        sensitivityMap.put(identifier, sensitivity);
    }

    /**
     * Get a component's custom sensitivity value
     *
     * @return Sensitivity of component given identifier
     */
    public Sensitivity getSensitivity(Component.Identifier identifier) {
        if (sensitivityMap == null || sensitivityMap.isEmpty()) {
            throw new NullPointerException("Sensitivity hashmap is null");
        }
        return sensitivityMap.get(identifier);
    }

    /**
     * Return directional component for X and Y Axis components
     *
     * @param xComponent X Axis Component. Axis.X or Axis.RX
     * @param yComponent Y Axis Component. Axis.Y or Axis.RY
     * @return GamepadDirection component for human readable direction component.
     */
    private GamepadDirection getAxisDirection(Component xComponent, Component yComponent) {
        GamepadDirection gamepadDirection = GamepadDirection.NULL;
        float xDeadzone = xComponent.getDeadZone();
        float yDeadzone = yComponent.getDeadZone();
        float xValue = xComponent.getPollData();
        float yValue = yComponent.getPollData();
        // 1.0 > X > 0.0
        if(xValue > xDeadzone) {
            // 1.0 > X > 0.0 AND 1.0 > Y > 0.0
            if(yValue > yDeadzone) {
                gamepadDirection = GamepadDirection.DOWN_RIGHT;
            }
            // 1.0 > X > 0.0 AND -1.0 < Y < 0.0
            else if(yValue < yDeadzone) {
                gamepadDirection = GamepadDirection.UP_RIGHT;
            }
        }
        // -1.0 < X < 0.0
        else {
            // -1.0 < X < 0.0 AND -1.0 > Y < 0.0
            if(yValue > yDeadzone) {
                gamepadDirection = GamepadDirection.DOWN_LEFT;
            }
            // -1.0 < X < 0.0 AND 1.0 > Y > 0.0
            else if(yValue < yDeadzone) {
                gamepadDirection = GamepadDirection.UP_LEFT;
            }
        }

        if(xValue == 1.0 && yValue == 0.0) {
            gamepadDirection = GamepadDirection.RIGHT;
        } else if (xValue == -1.0 && yValue == 0.0) {
            gamepadDirection = GamepadDirection.LEFT;
        } else if(xValue == 0.0) {
            if(yValue == 1.0) {
                gamepadDirection = GamepadDirection.DOWN;
            } else if (yValue == -1.0) {
                gamepadDirection = GamepadDirection.UP;
            }
        }
        return gamepadDirection;
    }

    /**
     * Retrieve human-readable direction component for any Axis component.
     *
     * @param axis Axis Component, LEFT_JOYSTICK, RIGHT_JOYSTICK, or D_PAD
     * @return Directional component given by enum GamepadDirection
     */
    public GamepadDirection getDirection(GamepadAxis axis) {
        if (!isConnected()) {
            throw new IllegalStateException(ERR_NOT_CONNECTED);
        }
        GamepadDirection gamepadDirection = GamepadDirection.NULL;
        switch (axis) {
            case LEFT_JOYSTICK:
                if (!hasComponent(Component.Identifier.Axis.X) || !hasComponent(Component.Identifier.Axis.Y)) {
                    throw new NullPointerException("Left joystick not found");
                }
                gamepadDirection = getAxisDirection(gamepad.getComponent(Component.Identifier.Axis.X), gamepad.getComponent(Component.Identifier.Axis.Y));
                break;
            case RIGHT_JOYSTICK:
                if (!hasComponent(Component.Identifier.Axis.RX) || !hasComponent(Component.Identifier.Axis.RY)) {
                    throw new NullPointerException("Right joystick not found");
                }
                gamepadDirection = getAxisDirection(gamepad.getComponent(Component.Identifier.Axis.RX), gamepad.getComponent(Component.Identifier.Axis.RY));
                break;
            case D_PAD:
                if (!hasComponent(Component.Identifier.Axis.POV)) {
                    throw new NullPointerException("Right joystick not found");
                }
                float dpadVal = getComponentValue(Component.Identifier.Axis.POV);
                if (dpadVal == 0.125f) {
                    gamepadDirection = GamepadDirection.UP_LEFT;
                } else if (dpadVal == 0.25f) {
                    gamepadDirection = GamepadDirection.UP;
                } else if (dpadVal == 0.375f) {
                    gamepadDirection = GamepadDirection.UP_RIGHT;
                } else if (dpadVal == 0.5f) {
                    gamepadDirection = GamepadDirection.RIGHT;
                } else if (dpadVal == 0.625f) {
                    gamepadDirection = GamepadDirection.DOWN_RIGHT;
                } else if (dpadVal == 0.75f) {
                    gamepadDirection = GamepadDirection.DOWN;
                } else if (dpadVal == 0.875f) {
                    gamepadDirection = GamepadDirection.DOWN_LEFT;
                } else if (dpadVal == 1.0f) {
                    gamepadDirection = GamepadDirection.LEFT;
                }
                break;
        }
        return gamepadDirection;
    }

}
