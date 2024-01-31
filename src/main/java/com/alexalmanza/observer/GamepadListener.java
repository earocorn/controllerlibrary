package com.alexalmanza.observer;

import net.java.games.input.Component;

/**
 * Listener interface used with GamepadObserver to perform callbacks
 */
public interface GamepadListener {

    /**
     * Callback function to execute on update of controller component's state
     *
     * @param identifier Identifier of component which triggered the callback
     * @param currentValue Current value of the component
     */
    void onChange(Component.Identifier identifier, float currentValue);

}
