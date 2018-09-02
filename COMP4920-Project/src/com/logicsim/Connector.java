package com.logicsim;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Connector extends IO {
	
	private Component input;
	private Component output;

	@Override
	public int calculate() {
		return input.calculate();
	}

	@Override
	public void update() {
	}

	@Override
	public void paint(Graphics g) {	
		// Draw Straight Line
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public Component clone() {
		return null;
	}

}
