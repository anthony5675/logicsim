package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class And extends Gate {
	
	public And(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		
		width = 50;
		height = 50;
		
		inputMin = 2;

		image = ImageLoader.loadImage("images/andgate.png");
	}
	
	public int calculate() {
		if (inputs.size() < inputMin) return -1;
		
		// Get first input and bitwise AND with any further inputs
		int result = inputs.get(0).calculate();
		for (int i = 1; i < inputs.size(); i++) {
			result &= inputs.get(i).calculate();
		}
		return result;
	}
	
	public void paint(Graphics g) {
		if (image == null) {
			g.setColor(Color.WHITE);
			g.fillRect(x, y, width, height);

			g.setColor(Color.BLACK);
			g.drawString("AND", x + 10, y + height / 2);
		} else {
			g.drawImage(image, x, y, width, height,null);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	public Component clone() {
		And c = new And(x, y);
		
		c.width = width;
		c.height = height;
		
		c.inputMin = inputMin;
		
		c.inputs = new ArrayList<IO>();
		c.output = null;
		
		return c;
	}
}
