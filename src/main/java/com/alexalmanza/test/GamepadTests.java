package com.alexalmanza.test;

import com.alexalmanza.GamepadUtil;
import net.java.games.input.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GamepadTests {

    private final GamepadUtil gamepadUtil = new GamepadUtil();
    private Controller gamepad;

    @Test
    void gamepadExists() {
        Assertions.assertInstanceOf(Controller.class, gamepadUtil.getGamepad());
    }

}
