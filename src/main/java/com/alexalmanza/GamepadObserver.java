package com.alexalmanza;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

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
    private Event event;

    /**
     * The controller to which the observer is applied
     */
    private Controller controller;

    /**
     * Constructor
     *
     * @param event JInput Event instance to get current controller events
     * @param controller JInput Controller object for the observer to use
     */
    public GamepadObserver(Event event, Controller controller) {
        if(event == null || controller == null) {
            throw new NullPointerException();
        }
        this.event = event;
        this.controller = controller;
        gamepadListeners = new ConcurrentHashMap<>();
    }

    /**
     * Method to listen for controller updates and execute callback functions of event listeners. This method should be run continuously or whenever update notifications are desired
     */
    public void listen() {
        if(event != null && controller != null) {
            synchronized (this) {
            EventQueue queue = controller.getEventQueue();
            if(queue.getNextEvent(event)) {
                Component eventComponent = event.getComponent();
                for(Map.Entry<Identifier, GamepadListener> entry : gamepadListeners.entrySet()) {
                    if(eventComponent.getIdentifier() == entry.getKey()) {
                        entry.getValue().onChange();
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
