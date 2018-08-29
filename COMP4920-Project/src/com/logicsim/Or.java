package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Or extends Gate {
	
	public Or(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		
		width = 50;
		height = 50;
		
		inputMin = 2;
	}

	@Override
	public int calculate() {
		if (inputs.size() < inputMin) return -1;
		
		// Get first input and bitwise AND with any further inputs
		int result = inputs.get(0).calculate();
		for (int i = 1; i < inputs.size(); i++) {
			result |= inputs.get(i).calculate();
		}
		return result;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
		
		g.setColor(Color.BLACK);
		g.drawString("OR", x + 17, y + height/2 + 3);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component clone() {
		Or c = new Or(x, y);
		
		c.width = width;
		c.height = height;
		
		c.inputMin = inputMin;
		
		c.inputs = new ArrayList<IO>();
		c.output = null;
		
		return c;
	}

}
