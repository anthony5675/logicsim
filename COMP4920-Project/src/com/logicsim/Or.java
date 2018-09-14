package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle everything going on with an OR gate
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Or extends Gate {
	
	/**
     * Initializes an Or object
     * @param x == x coordinate to set where the OR gate will draw
     * @param y == y coordinate to set where the OR gate will draw
     */
	public Or(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		
		width = 50;
		height = 50;
		
		inputMin = 2;
	}

	/**
	 * Figures out the current state of the gate (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	@Override
	public int calculate() {
		if (inputs.size() < inputMin) return -1;
		
		// Get first input and bitwise OR with any further inputs
		int result = inputs.get(0).calculate();
		for (int i = 1; i < inputs.size(); i++) {
			result |= inputs.get(i).calculate();
		}
		return result;
	}

	/**
	 * Runs updates to the values of variables if necessary
	 */
	@Override
	public void update() {
	}

	/**
	 * If there is an image, paint just that
	 * If there is not an image paint a white circle with a smaller white circle attached
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
		
		g.setColor(Color.BLACK);
		g.drawString("OR", x + 17, y + height/2 + 3);
	}

	/**
	 * Handles what clicking on an OR gate will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Check if its on an input/output point and tell SE

	}
	
	/**
	 * Provides a way for left hand edge detection can be done
	 * @return The horizontal location of the input point (left most point)
	 */
	public int getLeftEdge() {
		return inPoints.get(0).getX();
	}
	
	/**
	 * Provides a way for right hand edge detection can be done
	 * @return The horizontal location of the output point + output point width (right most point)
	 */
	public int getRightEdge() {
		return outPoint.getX() + outPoint.getWidth();
	}

	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	@Override
	public Component clone() {
		Or c = new Or(x, y);
		
		c.width = width;
		c.height = height;
		
		c.inputMin = inputMin;
		
		c.inputs = new ArrayList<Connector>();
		c.output = null;
		
		return c;
	}

}
