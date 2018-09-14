package com.logicsim;

import javax.swing.*;
import java.awt.*;

/**
 * Class to display information about the components.
 * @author Jayden, Andre, Mitchell, Anthony
 */

public class Tooltip {
    private int x, y, width, height;
    private boolean toggled;
    private String componentType;
    private int[][] truthTable = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};

    /**
     * Initializes a Tooltip object
     */
    public Tooltip() {
        x = 0;
        y = 0;
        width = 200;
        height = 100;
        toggled = false;
        componentType = null;
    }

    /**
     * Paints tooltip displaying information based on component type.
     * @param g == Outward facing Graphics object to draw to
     */
    public void paint(Graphics g) {
        g.setColor(new Color(77, 77, 77));
        g.fillRect(x, y, width, height);
        g.setColor(new Color(255, 146, 208));

        String text = "placeholder";
        int rowPosY = this.y + 10;
        if (componentType.equals("And")) {
            text = "This is an AND gate.";
            for (int i = 0; i < 4; i++) {
                String tableRow = "";
                rowPosY += 10;
                int p = truthTable[i][0];
                int q = truthTable[i][1];
                int result = p & q;
                tableRow += p + "  " + q + "   " + result;
                g.drawString(tableRow, x + 10, rowPosY);
            }
        } else if (componentType.equals("Or")) {
            text = "This is an OR gate.";
            for (int i = 0; i < 4; i++) {
                String tableRow = "";
                rowPosY += 10;
                int p = truthTable[i][0];
                int q = truthTable[i][1];
                int result = p | q;
                tableRow += p + "  " + q + "   " + result;
                g.drawString(tableRow, x + 10, rowPosY);
            }
        } else if (componentType.equals("Source")) {
            text = "This is an input";
        }
        g.drawString(text, x + 10, y + 10);
    }

    /**
     * Provides the X coordinate of the tooltip
     * @return Integer describing X coordinate
     */
    public int getX() {
    	return x;
    }

    /**
     * Provides the Y coordinate of the tooltip
     * @return Integer describing Y coordinate
     */
    public int getY() {
    	return y;
    }

    /**
     * Allows the X coordinate of the tooltip to be updated
     * @param x == new x coordinate
     */
    public void setX(int x) {
    	this.x = x;
    }

    /**
     * Allows the Y coordinate of the tooltip to be updated
     * @param y == new y coordinate
     */
    public void setY(int y) {
    	this.y = y;
    }

    /**
     * Allows the component type to be updated based on which
     * component was clicked on.
     * @param componentType == current component type clicked
     */
    public void setInfo(String componentType) {
        this.componentType = componentType;
    }

    /**
     * Provides the visibility state of the tooltip
     * @return Boolean providing the toggled state
     */
    public boolean isToggled() {
    	return toggled;
    }

    /**
     * Toggles the visibility state of the tooltip on or off
     */
    public void toggleTip() {
    	toggled = !toggled;
    }
}
