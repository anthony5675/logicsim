package com.logicsim;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Class for custom tab component which includes the title and close button
 * @author Jayden, Andre, Mitchell, Anthony
 */

public class WorkspaceTab extends JPanel {

    private SimulatorWindow sw;

    /**
     * Initialises the tab with its title property
     * @param sw
     */
    public WorkspaceTab(SimulatorWindow sw) {
        this.sw = sw;
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(new EmptyBorder(5, 2, 2, 2));
        setOpaque(false);
        JLabel label = new JLabel() {
            public String getText() {
                int i = sw.getTabs().indexOfTabComponent(WorkspaceTab.this);
                if (i != -1) {
                    return sw.getTabs().getTitleAt(i);
                }
                return null;
            }
        };
        label.setBorder(new EmptyBorder(0, 0, 0, 20));
        add(label);
        add(new CloseButton());
    }

    /**
     * Inner class that represents the close button.
     */
    class CloseButton extends JButton implements MouseListener {

        public CloseButton() {
            setText("x");
            setPreferredSize(new Dimension(15, 15));
            setToolTipText("close tab");
            setBorder(new EtchedBorder());
            setFocusable(false);
            addMouseListener(this);
        }

        /**
         * Handles mouse clicks, and closes the tab when pressed.
         * @param e == A mouse event object describing what happened when clicked.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            int index = sw.getTabs().indexOfTabComponent(WorkspaceTab.this);
            if(index != -1) {
                sw.removeWorkspace(index);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

}
