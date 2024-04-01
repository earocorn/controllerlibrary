package com.alexalmanza;

import com.alexalmanza.controller.gamepad.Gamepad;
import com.alexalmanza.models.ControllerData;
import net.java.games.input.Component;
import net.java.games.input.EventQueue;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;

public class MainPanel extends JPanel implements ActionListener {

    Timer timer;
    Gamepad gamepad;

    public MainPanel() {
        timer = new Timer(100, this);

        setSize(Util.windowDimension);

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("hello !!!" , 200, 200);

        ControllerData data = gamepad.getControllerData();

        int i = 0;
        for (Map.Entry entry : data.getOutputs().entrySet()) {
            g.drawString(entry.getKey().toString() + " : " + entry.getValue(),getWidth()/2, ((getHeight()/data.getOutputs().size())*i++) + 20);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
