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

	/**
     * Initializes an Output object
     * @param x == x coordinate to set where the Source will draw
     * @param y == y coordinate to set where the Source will draw
     */
	public Output(int i, int j) {
		x = i;
		y = j;
		width = Source.WIDTH;
		height = Source.HEIGHT;
		
		inPoints = new ArrayList<ConnectPoint>();
	}
	
	/**
	 * Figures out the current state of the output (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	@Override
	public int calculate() {
		return 0;
	}

	/**
	 * Runs updates to the values of variables if necessary
	 */
	@Override
	public void update() {
	}

	/**
	 * If there is an image, paint just that
	 * If there is not ?
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRoundRect(x, y, width, height, 10, 10);
	}

	/**
	 * Handles what clicking on an output will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
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

	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	@Override
	public Component clone() {
		return null;
	}

}
