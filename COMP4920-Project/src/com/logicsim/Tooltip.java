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
        height = 140;
        toggled = false;
        componentType = null;
    }

    /**
     * Paints tooltip displaying information based on component type.
     * @param g == Outward facing Graphics object to draw to
     */
    public void paint(Graphics g) {
        g.setColor(new Color(77, 77, 77));
        g.setFont(new Font("Aerial", Font.PLAIN, 13));

        int rowPos = y + 70;
        String gap = "            ";
        switch(componentType) {
            case "And":
                setHeight(150);
                setWidth(230);
                g.fillRect(x, y, width, height);
                g.setColor(Color.CYAN);
                g.drawString("Input 1   Input 2   Output", x + 10, rowPos);
                for (int i = 0; i < 4; i++) {
                    rowPos += 15;
                    int p = truthTable[i][0];
                    int q = truthTable[i][1];
                    g.drawString(p + gap + q + gap + (p & q), x + 10, rowPos);
                }
                g.drawString("AND Gate:", x + 10, y + 15);
                g.drawString("If all inputs are 1, then the", x + 10, y + 30);
                g.drawString("output is 1, otherwise output is 0.", x + 10, y + 45);
                break;
            case "Or":
                setHeight(150);
                setWidth(230);
                g.fillRect(x, y, width, height);
                g.setColor(Color.CYAN);
                g.drawString("Input 1   Input 2   Output", x + 10, rowPos);
                for (int i = 0; i < 4; i++) {
                    rowPos += 15;
                    int p = truthTable[i][0];
                    int q = truthTable[i][1];
                    g.drawString(p + gap + q + gap + (p | q), x + 10, rowPos);
                }
                g.drawString("OR Gate:", x + 10, y + 15);
                g.drawString("If any input is 1, then the", x + 10, y + 30);
                g.drawString("output is 1, otherwise output is 0.", x + 10, y + 45);
                break;
            case "NAnd":
                setHeight(150);
                setWidth(230);
                g.fillRect(x, y, width, height);
                g.setColor(Color.CYAN);
                g.drawString("Input 1   Input 2   Output", x + 10, rowPos);
                for (int i = 0; i < 4; i++) {
                    rowPos += 15;
                    int p = truthTable[i][0];
                    int q = truthTable[i][1];
                    g.drawString(p + gap + q + gap + (((p & q) != 1) ? 1 : 0), x + 10, rowPos);
                }
                g.drawString("NAND Gate:", x + 10, y + 15);
                g.drawString("If all inputs are 1, then the", x + 10, y + 30);
                g.drawString("output is 0, otherwise output is 1.", x + 10, y + 45);
                break;
            case "NOr":
                setHeight(150);
                setWidth(230);
                g.fillRect(x, y, width, height);
                g.setColor(Color.CYAN);
                g.drawString("Input 1   Input 2   Output", x + 10, rowPos);
                for (int i = 0; i < 4; i++) {
                    rowPos += 15;
                    int p = truthTable[i][0];
                    int q = truthTable[i][1];
                    g.drawString(p + gap + q + gap + (((p | q) != 1) ? 1 : 0), x + 10, rowPos);
                }
                g.drawString("NOR Gate:", x + 10, y + 15);
                g.drawString("If both inputs are 0, then the", x + 10, y + 30);
                g.drawString("output is 1, otherwise output is 0.", x + 10, y + 45);
                break;
            case "Not":
                setHeight(130);
                setWidth(200);
                g.fillRect(x, y, width, height);
                g.setColor(Color.CYAN);
                g.drawString("Input      Output", x + 10, rowPos);
                for (int i = 0; i < 2; i++) {
                    rowPos += 15;
                    int p = truthTable[1][i];
                    g.drawString(p + gap + ((p != 1) ? 1 : 0), x + 10, rowPos);
                }
                g.drawString("NOT Gate:", x + 10, y + 15);
                g.drawString("Reverses the input.", x + 10, y + 30);
                break;
            case "XOr":
                setHeight(150);
                setWidth(230);
                g.fillRect(x, y, width, height);
                g.setColor(Color.CYAN);
                g.drawString("Input 1   Input 2   Output", x + 10, rowPos);
                for (int i = 0; i < 4; i++) {
                    rowPos += 15;
                    int p = truthTable[i][0];
                    int q = truthTable[i][1];
                    g.drawString(p + gap + q + gap + (p ^ q), x + 10, rowPos);
                }
                g.drawString("XOR Gate:", x + 10, y + 15);
                g.drawString("If any input is 1, then the", x + 10, y + 30);
                g.drawString("output is 1, otherwise output is 0.", x + 10, y + 45);
                break;
            case "Source":
                setHeight(50);
                g.fillRect(x, y, width, height);
                g.setColor(Color.CYAN);
                g.drawString("Input Connector:", x + 10, y + 15);
                g.drawString("Input source of a logic gate.", x + 10, y + 30);
                g.drawString("Can be either 1 or 0.", x + 10, y + 45);
                break;
            case "Output":
                setHeight(50);
                g.fillRect(x, y, width, height);
                g.setColor(Color.CYAN);
                g.drawString("Output Connector:", x + 10, y + 15);
                g.drawString("Output of the digital circuit.", x + 10, y + 30);
                g.drawString("Can be either 1 or 0.", x + 10, y + 45);
            default:
                break;
        }
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
     * Allows height of the tooltip to be updated
     * @param h == new height
     */
    public void setHeight(int h) {
        height = h;
    }

    /**
     * Allows the width of the tooltip to be updated
     * @param w == new width
     */
    public void setWidth(int w) {
        width = w;
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
