package com.alexalmanza;

import javax.swing.*;

public class MainFrame extends JFrame {

    MainPanel mainPanel;
    public MainFrame() {
        setPreferredSize(Util.windowDimension);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        mainPanel = new MainPanel();
        add(mainPanel);

        pack();
        setVisible(true);
    }

}
