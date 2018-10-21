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
	
	private int x1, y1, x2, y2;
	
	/**
	 * Initialises a Connector Object
	 */
	public Connector() {
		super();
		
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
	 * Paint a line connecting two IO points
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
	
	/**
	 * Provides a way for left hand edge detection can be done
	 * @return The horizontal location of the input point (left most point)
	 */
	public int getLeftEdge() {
		return x;
	}
	
	/**
	 * Provides a way for right hand edge detection can be done
	 * @return The horizontal location of the output point + output point width (right most point)
	 */
	public int getRightEdge() {
		return x + width;
	}
	
	/**
	 * Allows a component to be added as an input
	 * @param c == the input component
	 */
	public void setInput(Component c) {
		input = c;
	}

	/**
	 * Allows a component to be added as an output
	 * @param c == the output component
	 */
	public void setOutput(Component c) {
		output = c;
	}

	/**
	 * Allows an input point to be added
	 * @param c == the input point
	 */
	public void setInPoint(ConnectPoint c) {
		inPoint = c;
	}
	
	/**
	 * Provides what the current input point is for this Connector
	 * @return the input point for the connector
	 */
	public ConnectPoint getInPoint() {
		return inPoint;
	}

	/**
	 * Allows an output point to be added
	 * @param c == the output point
	 */
	public void setOutPoint(ConnectPoint c) {
		outPoint = c;
	}


	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	@Override
	public Component clone() {
		Connector c = new Connector();

		c.x1 = x1;
		c.x2 = x2;
		c.y1 = y1;
		c.y2 = y2;
		
		c.inPoint = inPoint;
		c.outPoint = outPoint;
		
		c.input = input;
		c.output = output;

		return c;
	}

	public Component getOutput() {
		return output;
	}

}
