package test;

import com.alexalmanza.GamepadUtil;;
import com.alexalmanza.controller.gamepad.Gamepad;
import com.alexalmanza.controller.gamepad.GamepadAxis;
import com.alexalmanza.controller.gamepad.GamepadConnection;
import com.alexalmanza.controller.wii.WiiMoteConnection;
import com.alexalmanza.interfaces.IController;
import com.alexalmanza.models.ControllerComponent;
import com.alexalmanza.models.Sensitivity;
import com.alexalmanza.interfaces.ControllerUpdateListener;
import com.alexalmanza.controller.gamepad.observer.GamepadObserver;
import com.alexalmanza.util.FindControllers;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GamepadTests {
    @Test
    void gamepadConnection() {
        Event event = new Event();
        GamepadConnection gamepadConnection = new GamepadConnection(event);
        Assertions.assertFalse(gamepadConnection.getConnectedControllers().isEmpty());
    }

    @Test
    void findControllers() {
        Gamepad gamepad;
        FindControllers findControllers = new FindControllers(new Event());

        List<IController> controllerArrayList = findControllers.getControllers();

        for (IController controller : controllerArrayList) {
            System.out.println(controller.getControllerData().getName());
        }

        gamepad = (Gamepad) controllerArrayList.get(0);

        gamepad.getObserver().addListener((identifier, currentValue) -> System.out.println(identifier + " changing to value " + currentValue), Component.Identifier.Button._4);

        for (IController controller : controllerArrayList) {
            for(ControllerComponent component : controller.getControllerData().getOutputs()) {
                System.out.println(component.getName() + " : " + component.getValue());
            }
        }
    }

    @Test
    void wiiConnection() {
        WiiMoteConnection wiiMoteConnection = new WiiMoteConnection(5000);
    }

}
