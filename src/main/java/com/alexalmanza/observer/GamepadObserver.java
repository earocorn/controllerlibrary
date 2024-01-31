package com.alexalmanza.observer;

import com.alexalmanza.GamepadInit;
import net.java.games.input.*;
import net.java.games.input.Component.Identifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to listen for controller input events registered as GamepadListener objects
 */
public class GamepadObserver implements Runnable {

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

    /**
     * Lock for thread safety
     */
    private static final Object lock = new Object();

    /**
     * Worker thread for observing events
     */
    private Thread worker;

    private final String threadName = "GamepadObserverThread";

    private boolean running = false;

    //TODO convert to singleton and have its own thread to use listeners
    /**
     * Singleton constructor
     */
    private GamepadObserver() {
        if(event == null || gamepad == null) {
            event = GamepadInit.getInstance().getEvent();
            gamepad = GamepadInit.getInstance().getGamepad();
        }
        gamepadListeners = new ConcurrentHashMap<>();
    }

    /**
     * Get instance of singleton class
     *
     * @return Current instance of GamepadObserver
     */
    public static GamepadObserver getInstance() {
        GamepadObserver result = gamepadObserver;
        if(result == null) {
            synchronized (lock) {
                result = gamepadObserver;
                if(result == null) {
                    gamepadObserver = result = new GamepadObserver();
                }
            }
        }
        return result;
    }

    /**
     * Starts the worker thread to listen for events
     */
    public void doStart() {
        if(worker.isAlive()) {
            throw new IllegalStateException("Observer is already listening for events");
        }
        running = true;
        worker = new Thread(this, this.threadName);
        worker.start();
    }

    /**
     * Stop observer's worker thread
     */
    public void doStop() {
        running = false;
        System.out.println("Observer stopped manually");
    }

    /**
     * Check if observer's worker thread is running
     */
    public boolean isRunning() {
        running = worker.isAlive();
        return running;
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

    /**
     * Thread worker used for listening for controller events
     */
    @Override
    public void run() {

        if(!running) {
            return;
        }

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
}
