package com.logicsim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Class to handle everything going on with a ConnectPoint
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class ConnectPoint {
	
	private int x, y, width, height;
	private boolean state;
	private Component comp;
	private ArrayList<Connector> cons;
	
	/**
	 * Initialises a ConnectPoint object with default state
	 */
	public ConnectPoint(int i, int j, int w, int h, Component c) {
		x = i;
		y = j;
		width = w;
		height = h;
		state = false;
		comp = c;
		cons = new ArrayList<Connector>();
	}
	
	/**
	 * Initialises a ConnectPoint object with custom state
	 */
	public ConnectPoint(int i, int j, int w, int h, boolean s, Component c) {
		x = i;
		y = j;
		width = w;
		height = h;
		state = s;
		comp = c;
		cons = new ArrayList<Connector>();
	}
	

	/**
	 * Draw a white square with a border displaying the current state of
	 * this point (green == true, red == false)
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		if (!(comp.getOutPoint() == this)) {
			if (cons.size() > 0) {
				if (cons.get(0).calculate() == 0) {
					state = false;
				} else {
					state = true;
				}
			}
		}
		
		if(!state) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}
		g.drawRect(x, y, width, height);
	}
	
	/**
	 * Was the point clicked on?
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
     * Provides the X coordinate of the Point
     * @return Integer describing the X coordinate
     */
	public int getX() {
		return x;
	}

	/**
	 * Allows the X coordinate of a ConnectPoint to be updated
	 * @param i == new X coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
     * Provides the Y coordinate of the ConnectPoint
     * @return Integer describing the Y coordinate
     */
	public int getY() {
		return y;
	}

	/**
	 * Allows the Y coordinate of a ConnectPoint to be updated
	 * @param j == new Y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
     * Provides the width of the ConnectPoint
     * @return Integer describing the width
     */
	public int getWidth() {
		return width;
	}

	/**
     * Provides the height of the ConnectPoint
     * @return Integer describing the height
     */
	public int getHeight() {
		return height;
	}
	
	/**
     * Provide the current state of the ConnectPoint
     * @return Boolean of what state the ConnnectPoint is in
     */
	public boolean getState() {
		return state;
	}
	
	/**
	 * Allow direct setting of if this is a 0 or a 1
	 * @param s = boolean describing a new state
	 */
	public void setState(boolean s) {
		state = s;
	}
	
	/**
	 * @return What component is this attached to?
	 */
	public Component getComp() {
		return comp;
	}
	
	/**
	 * Sets what component this is attached to
	 * @param c == New Component
	 */
	public void setComp(Component c) {
		comp = c;
	}
	
	/**
	 * Provides a list of all connections to/from this point
	 * @return a list of Connectors
	 */
	public ArrayList<Connector> getCons() {
		return cons;
	}
	
	/**
	 * Add a new connector to the list
	 * @param c == a new Connector
	 */
	public void addCon(Connector c) {
		cons.add(c);
	}
	
	/**
	 * Removes an existing Connector
	 * Especially for deleting connectors
	 * @param c == Connector to delete
	 */
	public void removeCon(Connector c) {
		cons.remove(c);
	}

}