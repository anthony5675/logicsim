package com.logicsim;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * Class to handle everything going on with an Output IO
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Output extends IO {

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
		
	}

	/**
	 * Handles what clicking on an output will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		
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
