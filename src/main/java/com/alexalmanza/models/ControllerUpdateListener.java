package com.alexalmanza.models;

/**
 * Listener interface used with GamepadObserver to perform callbacks
 */
public interface ControllerUpdateListener {

    /**
     * Callback function to execute on update of controller component's state
     *
     * @param identifier Identifier of component which triggered the callback
     * @param currentValue Current value of the component
     */
    void onChange(String identifier, float currentValue);

}
