package com.logicsim;

import java.awt.Color;
import java.awt.Graphics;

public class ConnectPoint {
	
	private int x, y, width, height;
	private boolean state;
	
	public ConnectPoint(int i, int j, int w, int h) {
		x = i;
		y = j;
		width = w;
		height = h;
		state = false;
	}
	
	public ConnectPoint(int i, int j, int w, int h, boolean s) {
		x = i;
		y = j;
		width = w;
		height = h;
		state = s;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		if(!state) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}
		g.drawRect(x, y, width, height);
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

}
