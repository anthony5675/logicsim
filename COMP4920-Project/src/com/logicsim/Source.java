package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle everything going on with an Input Source
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Source extends IO {
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 20;
	
	private boolean state;
	
	/**
     * Initializes an Source object
     * @param x == x coordinate to set where the Source will draw
     * @param y == y coordinate to set where the Source will draw
     */
	public Source(int i, int j) {
		state = false;
		
		x = i;
		y = j;
		width = WIDTH;
		height = HEIGHT;
		
		// These will soon be updated to a better format
		connectX = x + width;
		connectY = y + height/4;
		connectHeight = height/2;
		connectWidth = connectHeight;
	}
	
	/**
	 * Provides the current state of the input source (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	@Override
	public int calculate() {
		if (state == false) return 0;
		return 1;
	}

	/**
	 * Runs updates to the values of variables if necessary
	 */
	@Override
	public void update() {
		connectX = x + width;
		connectY = y + height/4;
	}

	/**
	 * If there is an image, paint just that
	 * If there is not an image paint a white rectangle with a smaller white square attached
	 * As well as a coloured border around it indicating its state
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		g.fillRect(connectX + 1, connectY + 1, connectWidth - 1, connectHeight - 1);
		
		if (state) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g.drawRect(x, y, width, height);
		g.drawRect(connectX, connectY, connectWidth, connectHeight);
	}

	/**
	 * Handles what clicking on an Input Source will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Invert state
		state = !state;
	}

	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	@Override
	public Component clone() {
		Source c = new Source(x, y);
		
		c.width = width;
		c.height = height;
		
		c.state = state;
		
		return c;
	}
}
