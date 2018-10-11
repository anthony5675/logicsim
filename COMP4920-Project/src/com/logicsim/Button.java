package com.logicsim;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Button {

	private int x, y, width, height;
	private Color background, foreground;
	
	public Button (int i, int j, int w, int h, Color b, Color f) {
		x = i;
		y = j;
		width = w;
		height = h;
		
		background = b;
		foreground = f;
	}

	public void paint(Graphics g) {
        g.setFont(new Font("Aerial", Font.PLAIN, 24));
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);

		g.setColor(background);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		g.setColor(foreground);
		String text = "Next";
		g.drawString(text, x + (width/2) - 25, y + 20);
	}

	/**
	 * Was a button clicked on?
	 * @param i == the x part of the click
	 * @param j == the y part of the click
	 * @return If the click at (i, j) was inside the button
	 */
	public boolean wasClicked(int i, int j) {
		if (i >= x && i <= x + width) {
			if (j >= y && j <= y + height) {
				return true;
			}
		}
		return false;
	}

}
