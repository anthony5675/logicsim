package com.logicsim;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle all dealings with a "Toolbox"
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Toolbox {
	
	private int x, y, width, height;
	// TODO: Not sure this is necessary and you Might be able to delete
	private SimulatorEngine se;
	private ArrayList<Component> comps;
	
	/**
     * Initializes an And object
     * @param x == The canvas this object resides in
     */
	public Toolbox(SimulatorEngine s) {
		se = s;
		
		x = 0;
		y = 0;
		
		width = 150;
		height = 800; // TODO: Set these correctly
		
		// Add dumby components to the toolbox
		comps = new ArrayList<Component>();
		comps.add(new And(width/2 - 50/2, 200, se));
		comps.add(new Or(width/2 - 50/2, 260, se));
		comps.add(new Source(width/2 - 30/2, 160, se));
		comps.add(new Output(width/2 - 30/2, 320, se));
	}
	
	/**
	 * If there is an image, paint just that
	 * If there is not an image paint a gray rectangle
	 * Then paint all dumby objects inside the toolbox
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
		g.setColor(new Color(150, 150, 150));
		g.fillRect(x, y, width, height);

		g.setColor(Color.GREEN);

		// Draw Toolbox title
		g.drawString("Toolbox", 50, 50);
		
		for (Component c : comps) c.paint(g);
	}

	/**
	 * Was the toolbox clicked inside of?
	 * @param i == the x part of the click
	 * @param j == the y part of the click
	 * @return If the click at (i, j) was inside the component
	 */
	public boolean wasClicked(int i, int j) {
		if (i >= x && i <= x + width) {
			if (j >= y && j <= y + height) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Handles what will happen if something is clicked inside of the toolbox
	 * @param e == A mouse event object describing what happened when clicked
	 */
	public void mousePressed(MouseEvent e) {
		
		for (Component c : comps) {
			// If one of the components was clicked
			// Add a new instance of this component to the canvas
			if (c.wasClicked(e.getX(), e.getY())) se.setToBeAdded((Component) c.clone());
		}
		
	}

	/**
     * Provides the X coordinate of the toolbox
     * @return Integer describing the X coordinate
     */
	public int getX() {
		return x;
	}

	/**
     * Provides the Y coordinate of the toolbox
     * @return Integer describing the Y coordinate
     */
	public int getY() {
		return y;
	}

	/**
     * Provides the width of the toolbox
     * @return Integer describing the width
     */
	public int getWidth() {
		return width;
	}

	/**
     * Provides the height of the component
     * @return Integer describing the height
     */
	public int getHeight() {
		return height;
	}

	public ArrayList<Component> getComponents() {
		return comps;
	}

}
