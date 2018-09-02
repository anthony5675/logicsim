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

<<<<<<< HEAD
		connectHeight = height/2;
		connectWidth = connectHeight;
		connectX = x - connectWidth;
		connectY = y + height/4;
=======
		image = ImageLoader.loadImage("images/andgate.png");
>>>>>>> 48418d78893638bf8de5f3b87f171a9f8023c1e9
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

	@Override
	public void update() {
		connectX = x - connectWidth;
		connectY = y + height/4;
	}
	
	public void paint(Graphics g) {
<<<<<<< HEAD
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		g.fillRect(connectX + 1, connectY + 1, connectWidth - 1, connectHeight - 1);
		
		g.setColor(Color.BLACK);
		g.drawString("AND", x + 10, y + height/2);
=======
	    if (image == null) {
    		g.setColor(Color.WHITE);
    		g.fillRect(x, y, width, height);
    		
    		g.setColor(Color.BLACK);
    		g.drawString("AND", x + 10, y + height/2);
    		
    		for (IO s : inputs) s.paint(g);
		} else {
			g.drawImage(image, x, y, width, height,null);
		}
>>>>>>> 48418d78893638bf8de5f3b87f171a9f8023c1e9
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Check if its on an input/output point and tell SE
	}
	
	public Component clone() {
		And c = new And(x, y);
		
		c.width = width;
		c.height = height;
		
		c.inputMin = inputMin;
		
		c.inputs = new ArrayList<Connector>();
		c.output = null;
		
		return c;
	}
}
