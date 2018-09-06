package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle everything going on with an AND gate
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class And extends Gate {
	
	/**
     * Initializes an And object
     * @param x == x coordinate to set where the AND gate will draw
     * @param y == y coordinate to set where the AND gate will draw
     */
	public And(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		
		width = 50;
		height = 50;
		
		inputMin = 2;

		// These will soon be updated to a better format
		connectHeight = height/2;
		connectWidth = connectHeight;
		connectX = x - connectWidth;
		connectY = y + height/4;

		image = ImageLoader.loadImage("images/andgate.png");
	}
	
	/**
	 * Figures out the current state of the gate (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	public int calculate() {
		// Are there enough inputs to calculate
		if (inputs.size() < inputMin) return -1;
		
		// Get first input and bitwise AND with any further inputs
		int result = inputs.get(0).calculate();
		for (int i = 1; i < inputs.size(); i++) {
			result &= inputs.get(i).calculate();
		}
		return result;
	}

	/**
	 * Runs updates to the values of variables if necessary
	 */
	@Override
	public void update() {
		// Update connect X and Y to make the connection point
		// Move with the rest of the gate when dragging
		connectX = x - connectWidth;
		connectY = y + height/4;
	}
	
	/**
	 * If there is an image, paint just that
	 * If there is not an image paint a white square with a smaller white square attached
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
	    if (image == null) {
			g.setColor(Color.WHITE);
			g.fillRect(x, y, width, height);
			g.fillRect(connectX + 1, connectY + 1, connectWidth - 1, connectHeight - 1);
			
			g.setColor(Color.BLACK);
			g.drawString("AND", x + 10, y + height/2);
		} else {
			g.drawImage(image, x, y, width, height,null);
		}
	}

	/**
	 * Handles what clicking on an AND gate will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO: Check if its on an input/output point and tell SE
	}
	
	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	public Component clone() {
		And c = new And(x, y);
		
		c.width = width;
		c.height = height;
		
		c.inputMin = inputMin;
		
		c.inputs = new ArrayList<Connector>();
		c.output = null;
		
		return c;
	}
}
