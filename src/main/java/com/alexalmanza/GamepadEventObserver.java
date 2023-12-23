package com.alexalmanza;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to listen for controller input events registered as GamepadEvent objects
 */
public class GamepadEventObserver {

    /**
     * The list of event listeners registered to this observer
     */
    private Map<Identifier, GamepadEvent> gamepadEvents;

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
    public GamepadEventObserver(Event event, Controller controller) {
        this.event = event;
        this.controller = controller;
        gamepadEvents = new HashMap<>();
    }

    /**
     * Method to listen for controller updates and execute callback functions of event listeners. This method should be run continuously or whenever update notifications are desired
     */
    public void observe() {
        if(event != null && controller != null) {
            EventQueue queue = controller.getEventQueue();
            if(queue.getNextEvent(event)) {
                Component eventComponent = event.getComponent();
                for(Map.Entry<Identifier, GamepadEvent> entry : gamepadEvents.entrySet()) {
                    if(eventComponent.getIdentifier() == entry.getKey()) {
                        entry.getValue().onChange();
                    }
                }
            }
        }
    }

    /**
     * Adds an event listener to the observer's list of event listeners
     *
     * @param event GamepadEvent object whose callback method onChange() will be executed on notification of change in the controller component identified by the component identifier
     * @param identifier Controller component identifier to specify which component update notification needs to update which callback function
     */
    public void addEvent(GamepadEvent event, Identifier identifier) {
        gamepadEvents.put(identifier, event);
    }

    /**
     * Removes an event listener to the observer's list of event listeners
     *
     * @param event GamepadEvent object whose callback method onChange() will be executed on notification of change in the controller component identified by the component identifier
     * @param identifier Controller component identifier to specify which component update notification needs to update which callback function
     */
    public void removeEvent(GamepadEvent event, Identifier identifier) {
        gamepadEvents.remove(identifier, event);
    }


}
