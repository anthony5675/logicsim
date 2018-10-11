package com.logicsim;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Class that handles interaction with the toolbar
 * @author Jayden, Andre, Mitchell, Anthony
 */

public class Toolbar extends JToolBar implements ActionListener {

    SimulatorCanvas s;

    /**
     * Initializes toolbar.
     * @param sim == The simulator canvas it works on.
     */
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

    /**
     * Used to decide what happens when toolbar button is clicked.
     * @param e == An event representing some type of action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("save")) {
            System.out.println("Saving workspace.");
        } else if (command.equals("clear")) {
            System.out.println("Clearing workspace.");
            s.getSimEngine().clearComponents();
        }
    }

}
