package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Input extends IO {
	
	private boolean state;
	
	public Input(int i, int j) {
		state = false;
		
		x = i;
		y = j;
		
		width = 30;
		height = 20;
	}
	
	public Input(int i, int j, boolean s) {
		state = s;
		
		x = i;
		y = j;
		
		width = 50;
		height = 50;
	}

	@Override
	public int calculate() {
		if (state == false) return 0;
		return 1;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x + 1, y + 1, width - 1, height -1);
		
		g.setColor(Color.GREEN);
		g.drawRect(x, y, width, height);
		g.drawLine(x + width + 1, y + height/2, x + width + 20, y + height/2);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		state = !state;
	}

	@Override
	public Component clone() {
		Input c = new Input(x, y);
		
		c.width = width;
		c.height = height;
		
		c.state = state;
		
		return c;
	}
}
