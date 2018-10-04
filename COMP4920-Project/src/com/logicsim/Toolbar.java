package com.logicsim;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar{

    public Toolbar(SimWindow sw) {
        JToolBar toolbar = new JToolBar();
        toolbar.setRollover(true);

        JButton button = new JButton("Test");
        toolbar.add(button);
        toolbar.addSeparator();

        toolbar.add(new JButton("Test 2"));

        sw.add(toolbar, BorderLayout.NORTH);
    }

}
