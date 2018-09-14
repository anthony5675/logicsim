package com.logicsim;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Class to handle everything going on with a Connector (connecting two IO points)
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Connector extends IO {
	
	private Component input;
	private Component output;

	/**
	 * Figures out the current state of the input and send it to output (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	@Override
	public int calculate() {
		return input.calculate();
	}

	/**
	 * Runs updates to the values of variables if necessary
	 */
	@Override
	public void update() {
	}

	/**
	 * If there is an image, paint just that
	 * If there is not an image paint a line connecting two IO points
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {	
		// Draw Straight Line
	}

	/**
	 * Handles what clicking on a connector will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {}
	
	public int getLeftEdge() {
		return x;
	}
	
	public int getRightEdge() {
		return x + width;
	}

	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	@Override
	public Component clone() {
		return null;
	}

}
