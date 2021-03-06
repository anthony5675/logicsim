package com.logicsim;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button {

	private int x, y, width, height;
	private Color background, foreground;
	private String text;
	
	/**
	 * Initialises a Button object
	 */
	public Button (int i, int j, int w, int h, Color b, Color f, String t) {
		x = i;
		y = j;
		width = w;
		height = h;
		
		background = b;
		foreground = f;
		
		text = t;
	}

	/**
	 * Paint the button as a rectange with text on top
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
        g.setFont(new Font("Aerial", Font.PLAIN, 24));
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);

		g.setColor(background);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		int stringWidth = g.getFontMetrics().stringWidth(text);
		int stringHeight = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();

		g.setColor(foreground);
		g.drawString(text, x + (width/2) - (stringWidth/2), y + height/2 + stringHeight/4);
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

	/**
	 * Provides the y value of the button
	 * @return The y position of the top left corner of the button
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Allows changing of the button text on the fly
	 * @param s == the new text to show
	 */
	public void setText(String s) {
		text = s;
	}
	

}
