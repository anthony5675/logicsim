package com.logicsim;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to hold all information about all components (Gates and IO)
 * @author Jayden, Andre, Mitchell, Anthony
 */
public abstract class Component {

	public Image image;

	protected int x, y, width, height;
	
	protected ArrayList<ConnectPoint> inPoints;
	
	protected ConnectPoint outPoint;

	public abstract int calculate();

	public abstract void update();

	public abstract void paint(Graphics g);

	public abstract void mousePressed(MouseEvent e);
	
	public abstract int getLeftEdge();
	
	public abstract int getRightEdge();
	
	public abstract Component clone();

	/**
	 * Was a component clicked on?
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
     * Provides the X coordinate of the component
     * @return Integer describing the X coordinate
     */
	public int getX() {
		return x;
	}
	
	/**
	 * Allows the X coordinate of a component to be updated
	 * @param i == new X coordinate
	 */
	public void setX(int i) {
		x = i;
	}

	/**
     * Provides the Y coordinate of the component
     * @return Integer describing the Y coordinate
     */
	public int getY() {
		return y;
	}
	
	/**
	 * Allows the Y coordinate of a component to be updated
	 * @param j == new Y coordinate
	 */
	public void setY(int j) {
		y = j;
	}

	/**
     * Provides the width of the component
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
	
}
