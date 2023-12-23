package com.alexalmanza;

/**
 * Listener interface used with GamepadEventObserver to perform callbacks
 */
public interface GamepadEvent {

    /**
     * Callback function to execute on update of controller component's state
     */
    void onChange();

}
