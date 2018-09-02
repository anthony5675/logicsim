package com.logicsim;
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Component {

	public Image image;

	protected int x, y, width, height;
	
	protected int connectX, connectY, connectWidth, connectHeight;

	public abstract int calculate();

	public abstract void update();

	public abstract void paint(Graphics g);

	public abstract void mousePressed(MouseEvent e);
	
	public abstract Component clone();

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
