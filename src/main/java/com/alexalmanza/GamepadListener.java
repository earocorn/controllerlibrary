package com.alexalmanza;

/**
 * Listener interface used with GamepadObserver to perform callbacks
 */
public interface GamepadListener {

    /**
     * Callback function to execute on update of controller component's state
     */
    void onChange();

}
