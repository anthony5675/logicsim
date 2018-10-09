package com.logicsim;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Toolbar extends JToolBar implements ActionListener {

    SimulatorCanvas s;

    public Toolbar(SimulatorCanvas sim) {
        s = sim;

        setRollover(true);

        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.setToolTipText("save the current workspace");
        saveButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        clearButton.setToolTipText("clear the current workspace");
        clearButton.addActionListener(this);

        add(saveButton);
        add(clearButton);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String output = null;

        if (command.equals("save")) {
            System.out.println("Saving workspace.");
        } else if (command.equals("clear")) {
            System.out.println("Clearing workspace.");
            s.getSimEngine().clearComponents();
        }
    }

}
