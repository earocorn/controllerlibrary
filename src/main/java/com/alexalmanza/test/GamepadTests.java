package com.alexalmanza.test;

import com.alexalmanza.GamepadInit;
import com.alexalmanza.GamepadUtil;;
import com.alexalmanza.model.GamepadAxis;
import com.alexalmanza.model.Sensitivity;
import com.alexalmanza.observer.GamepadListener;
import com.alexalmanza.observer.GamepadObserver;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GamepadTests {

    private static final GamepadUtil gamepadUtil = new GamepadUtil();
    private static GamepadObserver observer;
    @BeforeAll
    static void setUp() {
        try{
            gamepadUtil.setSensitivity(GamepadAxis.LEFT_JOYSTICK, Sensitivity.HIGH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        observer = GamepadObserver.getInstance();
        GamepadListener testListener = (identifier, currentValue) -> System.out.println(identifier + " : " + currentValue);
        observer.addListener(testListener, Component.Identifier.Button._0);
        observer.doStart();
    }

    @Test
    void utilNotNull() {
        Assertions.assertNotNull(gamepadUtil);
    }

    @Test
    void observerNotNull() {
        Assertions.assertNotNull(observer);
    }

    @Test
    void controllerExists() {
        Assertions.assertInstanceOf(Controller.class, gamepadUtil.getGamepad());
    }

    @Test
    void controllerIsGamepad() {
        Assertions.assertEquals(Controller.Type.GAMEPAD, gamepadUtil.getGamepad().getType());
    }

    @Test
    void componentsExist() {
        Assertions.assertInstanceOf(Component[].class, gamepadUtil.getGamepadComponents());
    }

    @Test
    void controllerHasMultipleComponents() {
        Assertions.assertTrue(gamepadUtil.getGamepadComponents().length > 1);
    }

    @Test
    void componentValueDefault() {
        Assertions.assertEquals(gamepadUtil.getGamepadComponents()[0].getPollData(), gamepadUtil.getGamepadComponents()[0].getDeadZone());
    }

    @Test
    void gamepadHasJoysticks() {
        Assertions.assertTrue(gamepadUtil.getAxisComponents().length > 1);
    }

    @Test
    void gamepadHasButtons() {
        Assertions.assertTrue(gamepadUtil.getButtonComponents().length > 1);
    }

    // Gamepad should not contain any KEY Components
    @Test
    void gamepadHasNoKeys() {
        Assertions.assertEquals(0, gamepadUtil.getKeyComponents().length);
    }

    @Test
    void hasDefaultButton() {
        Assertions.assertTrue(gamepadUtil.hasComponent(Component.Identifier.Button._0));
    }

    @Test
    void componentNamesExist() {
        Assertions.assertTrue(gamepadUtil.getComponentsNamesAsList().toArray().length > 1);
    }

    @Test
    void componentIdentifiersExist() {
        Assertions.assertTrue(gamepadUtil.getComponentsIdentifiersAsList().toArray().length > 1);
    }

    @Test
    void componentDataExists() {
        Assertions.assertTrue(gamepadUtil.getComponentsDataAsList().toArray().length > 1);
    }

    @Test
    void defaultButtonNotPressed() {
        Assertions.assertFalse(gamepadUtil.isButtonPressed(Component.Identifier.Button._0));
    }

    @Test
    void setAndGetSensitivity() {
        Assertions.assertEquals(Sensitivity.HIGH, gamepadUtil.getSensitivity(GamepadAxis.LEFT_JOYSTICK));
    }

    @Test
    void getDeadzoneSensitivityValue() {
        Assertions.assertEquals(0.0f, gamepadUtil.getValueWithSensitivity(Component.Identifier.Axis.X));
    }

    @Test
    void correctMaxValue() {
        Assertions.assertEquals(1.0f, gamepadUtil.getMaxValue(Component.Identifier.Axis.X));
    }

    @Test
    void correctMinValue() {
        Assertions.assertEquals(-1.0f, gamepadUtil.getMinValue(Component.Identifier.Axis.X));
    }

    // Need to test sensitivity, direction, trigger pressure manually

    // Observer test

    @Test
    void noEventThrowsException() {
        Assertions.assertThrows(IllegalStateException.class, () -> observer.doStart());
    }

    @Test
    void observerRunning() {
        Assertions.assertTrue(observer.isRunning());
    }

    @Test
    void stopOrRestartObserver() {
        observer.doStop();
        Assertions.assertFalse(observer.isRunning());
    }

    @Test
    void startOrRestartObserver() {
        if(observer.isRunning()) {
            observer.doStop();
        }
        observer.doStart();
        Assertions.assertTrue(observer.isRunning());
    }

}
