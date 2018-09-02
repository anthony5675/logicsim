package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Source extends IO {
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 20;
	
	private boolean state;
	
	public Source(int i, int j) {
		state = false;
		
		x = i;
		y = j;
		width = WIDTH;
		height = HEIGHT;
		
		connectX = x + width;
		connectY = y + height/4;
		connectHeight = height/2;
		connectWidth = connectHeight;
	}
	
	@Override
	public int calculate() {
		if (state == false) return 0;
		return 1;
	}

	@Override
	public void update() {
		connectX = x + width;
		connectY = y + height/4;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		g.fillRect(connectX + 1, connectY + 1, connectWidth - 1, connectHeight - 1);
		
		if (state) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.RED);
		}
		g.drawRect(x, y, width, height);
		g.drawRect(connectX, connectY, connectWidth, connectHeight);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		state = !state;
	}

	@Override
	public Component clone() {
		Source c = new Source(x, y);
		
		c.width = width;
		c.height = height;
		
		c.state = state;
		
		return c;
	}
}
