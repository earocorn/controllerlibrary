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

    public MainPanel() {
        timer = new Timer(100, this);

        setSize(Util.windowDimension);

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(int i = 0; i < Main.gamepadUtil.getGamepadComponents().length; i++) {
            Component comp = Main.gamepadUtil.getGamepadComponents()[i];
            EventQueue eventQueue = Main.gamepad.getEventQueue();
            // stream data from controller
            g.drawString(comp.getIdentifier() + " : " + Main.gamepadUtil.getComponentValue(comp.getIdentifier()), getWidth()/2, ((getHeight()/Main.gamepadComponents.length)*i) + 20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        GamepadObserver.getInstance().listen();
        // poll device for updates
        Main.gamepad.poll();
    }
}
