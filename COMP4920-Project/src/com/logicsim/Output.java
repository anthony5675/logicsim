package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle everything going on with an Output IO
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Output extends IO {
	
	private Connector input;

	/**
     * Initializes an Output object
	 * @param s 
     * @param x == x coordinate to set where the Source will draw
     * @param y == y coordinate to set where the Source will draw
     */
	public Output(int i, int j, SimulatorEngine s) {
		x = i;
		y = j;
		width = Source.WIDTH;
		height = Source.HEIGHT;
		
		se = s;
		
		inPoints = new ArrayList<ConnectPoint>();
		ConnectPoint ip = new ConnectPoint(x - (height/2), y + height/4, height/2, height/2, this);
		inPoints.add(ip);
		
		input = null;
	}
	
	/**
	 * Figures out the current state of the output (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	@Override
	public int calculate() {
		if (input != null) return input.calculate();
		return 0;
	}

	/**
	 * Runs updates to the values of variables if necessary
	 */
	@Override
	public void update() {
		inPoints.get(0).setX(x - (height/2));
		inPoints.get(0).setY(y + height/4);
		if (calculate() == 0) {
			inPoints.get(0).setState(false);
		} else {
			inPoints.get(0).setState(true);
		}
	}

	/**
	 * If there is an image, paint just that
	 * If there is not ?
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		if (calculate() != 0) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g.drawRect(x, y, width, height);

		inPoints.get(0).paint(g);
	}

	/**
	 * Handles what clicking on an output will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (inPoints.get(0) != null && inPoints.get(0).wasClicked(e.getX(), e.getY())) {
			se.setIOPressed(inPoints.get(0));
		}
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
		return x + width;
	}
	
	public void setInput(Connector c) {
		input = c;
	}

	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	@Override
	public Component clone() {
		Output c = new Output(x, y, se);
		
		c.width = width;
		c.height = height;

		return c;
	}

}
