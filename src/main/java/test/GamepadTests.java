package test;

import com.alexalmanza.GamepadUtil;;
import com.alexalmanza.controller.gamepad.GamepadAxis;
import com.alexalmanza.controller.gamepad.GamepadConnection;
import com.alexalmanza.models.Sensitivity;
import com.alexalmanza.interfaces.ControllerUpdateListener;
import com.alexalmanza.controller.gamepad.observer.GamepadObserver;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GamepadTests {
    @Test
    void gamepadConnection() {
        Event event = new Event();
        GamepadConnection gamepadConnection = new GamepadConnection(event);
        Assertions.assertFalse(gamepadConnection.getConnectedControllers().isEmpty());
    }

}
