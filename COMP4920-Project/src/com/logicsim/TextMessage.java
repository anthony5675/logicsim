package com.logicsim;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle everything going on with an OR gate
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class TextMessage extends Gate {
	String message;
	/**
     * Initializes an Or object
     * @param x == x coordinate to set where the OR gate will draw
     * @param y == y coordinate to set where the OR gate will draw
	 * @param s 
     */
	public TextMessage(int x, int y, SimulatorEngine s, String text) {
		super();
		this.x = x;
		this.y = y;
		this.message = text;
		se = s;
		
		width = 50;
		height = 50;
		
		inputMin = 2;

		inPoints.add(new ConnectPoint(x - (height/4), y + height/8, height/4, height/4, this));
		inPoints.add(new ConnectPoint(x - (height/4), y + (height*5/8), height/4, height/4, this));
		outPoint = new ConnectPoint(x + width, y + height/4, height/2, height/2, this);

	}

	/**
	 * Figures out the current state of the gate (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	@Override
	public int calculate() {
		if (inputs.size() < inputMin) return 0;
		
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
//		for (ConnectPoint cp : inPoints) {
//			cp.setX(x - cp.getWidth());
//			cp.setY(y + height/4);
//		}

		inPoints.get(0).setX(x - inPoints.get(0).getWidth());
		inPoints.get(0).setY(y + height/8);

		inPoints.get(1).setX(x - inPoints.get(0).getWidth());
		inPoints.get(1).setY(y + (height*5/8));

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
	 * If there is not an image paint a white circle with a smaller white circle attached
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {
		g.setFont(new Font("Aeriel", Font.PLAIN, 14)); 
		createBox(g, message, x, y);
		drawFormatString(g, message, x, y);
	}
	
	void createBox(Graphics g, String text, int x, int y) {
		int count = 0;
		int width = 0;
		for (String line : text.split("\n")) {
			if(width <  g.getFontMetrics().stringWidth(line)) {
				width = g.getFontMetrics().stringWidth(line);
			}
			if(line != "") {
				count ++;
			}
		}
		g.setColor(new Color(77, 77, 77));
		g.fillRoundRect(x-7, y-5, width+13, count*g.getFontMetrics().getHeight(), 5, 5);
		g.setColor(new Color(255, 146, 208));
		g.drawRoundRect(x-7, y-5, width+13, count*g.getFontMetrics().getHeight(), 5, 5);
	}
	
	void drawFormatString(Graphics g, String text, int x, int y) {
		int count = 0;
	    for (String line : text.split("\n")) {
	        if(count == 0) {
	        	int pixelWidth = g.getFontMetrics().stringWidth(line);
	        	//int pixelHeight = g.getFontMetrics().getWidths();
	        	g.setColor(Color.MAGENTA);
	        	g.fillRoundRect(x-5, y, pixelWidth + 10, 15, 5, 1000);
	        	g.setColor(Color.CYAN);
	        	g.drawString(line, x, y += g.getFontMetrics().getHeight()-7);
	        } else {
	        	g.setColor(Color.WHITE);
	        	g.drawString(line, x, y += g.getFontMetrics().getHeight()-7);	
	        }
	    	count++;	
	    }	
	}
	/**
	 * Handles what clicking on an OR gate will do
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
		Or c = new Or(x, y, se);
		
		c.width = width;
		c.height = height;
		
		c.inputMin = inputMin;
		
		c.inputs = new ArrayList<Connector>();
		c.output = null;
		
		return c;
	}

}
