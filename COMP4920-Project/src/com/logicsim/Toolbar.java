package com.logicsim;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar{

    public Toolbar(SimWindow sw) {
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);

        JButton saveButton = new JButton("Save");
        saveButton.setToolTipText("save the current workspace");

        JButton clearButton = new JButton("Clear");
        clearButton.setToolTipText("clear the current workspace");
        
        toolbar.add(saveButton);
        toolbar.add(clearButton);

        sw.add(toolbar, BorderLayout.NORTH);
    }

}
