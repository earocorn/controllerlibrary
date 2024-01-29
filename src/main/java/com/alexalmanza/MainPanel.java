package com.alexalmanza;

import net.java.games.input.Component;
import net.java.games.input.EventQueue;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel implements ActionListener {

    Timer timer;

    GamepadUtil gamepadUtil;

    public MainPanel() {
        timer = new Timer(100, this);

        GamepadUtil gamepadUtil = new GamepadUtil();

        setSize(Util.windowDimension);

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("hello !!!" , 200, 200);

        for(int i = 0; i < gamepadUtil.getGamepadComponents().length; i++) {
            Component comp = gamepadUtil.getGamepadComponents()[i];
            // stream data from controller
            g.drawString(comp.getIdentifier() + " : " + gamepadUtil.getComponentValue(comp.getIdentifier()), getWidth()/2, ((getHeight()/gamepadUtil.getGamepadComponents().length)*i) + 20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
//        GamepadObserver.getInstance().listen();
        // poll device for updates
//        Main.gamepad.poll();
    }
}
