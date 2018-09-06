package com.logicsim;

import javax.swing.*;
import java.awt.*;

public class Tooltip {
    private int x, y, width, height;
    private boolean toggled;

    public Tooltip() {
        x = 0;
        y = 0;
        width = 200;
        height = 40;
        toggled = false;
    }

    public void paint(Graphics g) {
        g.setColor(new Color(255, 0, 255));
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawString("placeholder", x + 10, y + height/2);
    }

    public int getX() {
    	return x;
    }

    public int getY() {
    	return y;
    }

    public void setX(int x) {
    	this.x = x;
    }

    public void setY(int y) {
    	this.y = y;
    }

    public boolean isToggled() {
    	return toggled;
    }

    public void toggleTip() {
    	toggled = !toggled;
    }
}
