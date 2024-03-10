package com.alexalmanza;

import net.java.games.input.Component;
import net.java.games.input.EventQueue;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

public class MainPanel extends JPanel implements ActionListener {

    Timer timer;
    int mover = 0;

    public MainPanel() {
        timer = new Timer(100, this);

        setSize(Util.windowDimension);

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("HELLO THIS IS A TEST GUI !!!" , 200, 200);

        g.setColor(Color.PINK);
        g.fillOval(mover+20, mover+20, 30, 30);
        mover+=10;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
