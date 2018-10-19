package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Class to handle everything going on with a Connector (connecting two IO points)
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Connector extends IO {
	
	private Component input;
	private ConnectPoint inPoint;
	private Component output;
	private ConnectPoint outPoint;
	
	private int x1, y1, x2, y2;
	
	public Connector() {
		super();
		
		x1 = x2 = y1 = y2 = 0;
	}
	
	public Connector(Component in, Component out) {
		input = in;
		output = out;

		x1 = x2 = y1 = y2 = 0;
	}

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
		if (inPoint == null || outPoint == null) return;
		x1 = inPoint.getX() + inPoint.getWidth();
		y1 = inPoint.getY() + inPoint.getHeight()/2;
		
		x2 = outPoint.getX();
		y2 = outPoint.getY() + outPoint.getHeight()/2;
	}

	/**
	 * If there is an image, paint just that
	 * If there is not an image paint a line connecting two IO points
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {	
		// Draw Straight Line
		if (calculate() == 0) {
			g.setColor(Color.RED);
		} else {	
			g.setColor(Color.GREEN);
		}
		g.drawLine(x1, y1, x2, y2);
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
	
	public void setInput(Component c) {
		input = c;
	}

	public void setOutput(Component c) {
		output = c;
	}

	public void setInPoint(ConnectPoint c) {
		inPoint = c;
	}

	public void setOutPoint(ConnectPoint c) {
		outPoint = c;
	}


	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	@Override
	public Component clone() {
		return null;
	}

	public Component getOutput() {
		return output;
	}

}
