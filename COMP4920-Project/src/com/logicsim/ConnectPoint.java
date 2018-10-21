package com.logicsim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ConnectPoint {
	
		private int x, y, width, height;
		private boolean state;
		private Component comp;
		private ArrayList<Connector> cons;
	
	public ConnectPoint(int i, int j, int w, int h, Component c) {
		x = i;
		y = j;
		width = w;
		height = h;
		state = false;
		comp = c;
		cons = new ArrayList<Connector>();
	}
	
	public ConnectPoint(int i, int j, int w, int h, boolean s, Component c) {
		x = i;
		y = j;
		width = w;
		height = h;
		state = s;
		comp = c;
		cons = new ArrayList<Connector>();
	}
	
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public boolean getState() {
		return state;
	}
	
	public void setState(boolean s) {
		state = s;
	}
	
	public Component getComp() {
		return comp;
	}
	
	public void setComp(Component c) {
		comp = c;
	}
	
	public ArrayList<Connector> getCons() {
		return cons;
	}
	
	public void addCon(Connector c) {
		cons.add(c);
	}
	
	public void removeCon(Connector c) {
		cons.remove(c);
	}

}
