package com.alexalmanza;

import net.java.games.input.*;
import net.java.games.input.Component.Identifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to listen for controller input events registered as GamepadListener objects
 */
public class GamepadObserver {

    /**
     * The list of event listeners registered to this observer
     */
    private Map<Identifier, GamepadListener> gamepadListeners;

    /**
     * Event object for underlying plugin to populate
     */
    private static Event event;

    /**
     * The controller to which the observer is applied
     */
    private static Controller gamepad;

    /**
     * Instance of observer for singleton
     */
    private static GamepadObserver gamepadObserver;

    //TODO convert to singleton and have its own thread to use listeners
    /**
     * Singleton constructor
     */
    private GamepadObserver() {
        if(event == null || gamepad == null) {
            event = new Event();
            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

            for (Controller controller : controllers) {
                if (controller.getType() == Controller.Type.GAMEPAD) {
                    gamepad = controller;
                }
            }
        }
        gamepadListeners = new ConcurrentHashMap<>();
    }

    /**
     * Get instance of singleton class
     *
     * @return Current instance of GamepadObserver
     */
    public static GamepadObserver getInstance() {
        if(gamepadObserver == null) {
            gamepadObserver = new GamepadObserver();
            //TODO ensure singleton works
        }
        return gamepadObserver;
    }

    /**
     * Get current Event for use of JInput library
     *
     * @return current event instance
     */
    public Event getEvent() {
        if(event == null) {
            event = new Event();
        }
        return event;
    }

    /**
     * Method to listen for controller updates and execute callback functions of event listeners. This method should be run continuously or whenever update notifications are desired
     */
    public void listen() {
        if(event != null && gamepad != null) {
            synchronized (this) {
            EventQueue queue = gamepad.getEventQueue();
            if(queue.getNextEvent(event)) {
                Component eventComponent = event.getComponent();
                for(Map.Entry<Identifier, GamepadListener> entry : gamepadListeners.entrySet()) {
                    if(eventComponent.getIdentifier() == entry.getKey()) {
                            entry.getValue().onChange(entry.getKey(), eventComponent.getPollData());
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds an event listener to the observer's list of event listeners
     *
     * @param listener GamepadListener object whose callback method onChange() will be executed on notification of change in the controller component identified by the component identifier
     * @param identifier Controller component identifier to specify which component update notification needs to update which callback function
     */
    public void addListener(GamepadListener listener, Identifier identifier) {
        gamepadListeners.put(identifier, listener);
    }

    /**
     * Removes an event listener to the observer's list of event listeners
     *
     * @param listener GamepadListener object whose callback method onChange() will be executed on notification of change in the controller component identified by the component identifier
     * @param identifier Controller component identifier to specify which component update notification needs to update which callback function
     */
    public void removeListener(GamepadListener listener, Identifier identifier) {
        gamepadListeners.remove(identifier, listener);
    }

}
