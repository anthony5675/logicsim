package com.logicsim;

import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Class to create in initialize a window for the simulator to run in
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

        add(newButton("save"));
        add(newButton("load"));
        add(newButton("clear"));
    }

    /**
     * Creates a new button to be added to toolbar
     * @param name == name and action of the button
     * @return The button
     */
    public JButton newButton(String name) {
        JButton button = new JButton(name);
        button.setActionCommand(name);
        button.addActionListener(this);

        return button;
    }

    /**
     * Used to decide what happens when toolbar button is clicked.
     * @param e == An event representing some type of action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("save")) {
            SaveLoadUtils.save(s.getSimEngine());
        } else if (command.equals("load")) {
            //SaveLoadUtils.load(s.getSimEngine(), "save_2.json");
        } else if (command.equals("clear")) {
            s.getSimEngine().getComponents().clear();
        }
    }

}
