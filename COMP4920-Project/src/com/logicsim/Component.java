package com.logicsim;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public abstract class Component {

	protected int x, y, width, height;

	public abstract int calculate();

	public abstract void paint(Graphics g);

	public abstract void mousePressed(MouseEvent e);
	
	public abstract Component clone();

	public boolean wasClicked(int i, int j) {
		if (i >= x && i <= width) {
			if (j >= y && j <= height) {
				return true;
			}
		}
		return false;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int i) {
		x = i;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int j) {
		y = j;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
