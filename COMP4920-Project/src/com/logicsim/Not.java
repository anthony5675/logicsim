package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle everything going on with an OR gate
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Not extends Gate {
	
	/**
     * Initializes an Not object
     * @param x == x coordinate to set where the OR gate will draw
     * @param y == y coordinate to set where the OR gate will draw
	 * @param s 
     */
	public Not(int x, int y, SimulatorEngine s) {
		super();
		this.x = x;
		this.y = y;
		
		se = s;
		
		width = 50;
		height = 50;
		
		inputMin = 1;
		inputMax = 1;

		inPoints.add(new ConnectPoint(x - (height/2), y + height/4, height/2, height/2, this));
		outPoint = new ConnectPoint(x + width, y + height/4, height/2, height/2, this);

		image = ImageLoader.loadImage("images/notgate.png");
	}

	/**
	 * Figures out the current state of the gate (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	@Override
	public int calculate() {
		if (inputs.size() < inputMin) return 0;
		
		// Invert input for output
		int result = inputs.get(0).calculate();
		if (result == 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Runs updates to the values of variables if necessary
	 */
	@Override
	public void update() {
		inPoints.get(0).setX(x - inPoints.get(0).getWidth());
		inPoints.get(0).setY(y + height/4);

		outPoint.setX(x + width);
		outPoint.setY(y + height/4);
		if (calculate() == 0) {
			outPoint.setState(false);
		} else {
			outPoint.setState(true);
		}
	}

	/**
	 * If there is an image, paint just that
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {
		if (image == null) {
			g.setColor(Color.WHITE);
			g.fillRect(x, y, width, height);
			
			g.setColor(Color.BLACK);
			g.drawString("NOT", x, y + height/2 + 3);

			for (ConnectPoint cp : inPoints) cp.paint(g);
			outPoint.paint(g);
		} else {
			g.drawImage(image, x, y, width, height,null);

			for (ConnectPoint cp : inPoints) cp.paint(g);
			outPoint.paint(g);
		}
	}

	/**
	 * Handles what clicking on an NOT gate will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Check if its on an input/output point and tell SE
		for (ConnectPoint cp : inPoints) if (cp.wasClicked(e.getX(), e.getY())) se.setIOPressed(cp);
		if (outPoint != null && outPoint.wasClicked(e.getX(), e.getY())) se.setIOPressed(outPoint);
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
		Not c = new Not(x, y, se);
		
		c.width = width;
		c.height = height;
		
		c.inputMin = inputMin;
		c.inputMax = inputMax;
		
		c.inputs = new ArrayList<Connector>();
		c.output = null;
		
		return c;
	}

}
