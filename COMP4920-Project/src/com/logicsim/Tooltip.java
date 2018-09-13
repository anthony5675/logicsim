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

    /**
     * Initializes a Tooltip object
     */
    public Tooltip() {
        x = 0;
        y = 0;
        width = 200;
        height = 60;
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
        String table = "input1  Input2  Output";
        if (componentType.equals("And")) {
            text = "AND GATE TEST";
        } else if (componentType.equals("Or")) {
            text = "OR GATE TEST";
        } else if (componentType.equals("Source")) {
            text = "SOURCE TEST";
        }
        g.drawString(text, x + 10, y + 10);
        g.drawString(table, x + 10, y + 20);
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
