package com.logicsim;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

/**
 * Class to handle most simulator logic and hold most of the back end of it.
 * Including all the different sim objects and their different states
 * @author Jayden, Daniel, Sharon and Shravan
 *
 */
public class SimulatorEngine implements MouseListener, MouseInputListener {
	
	private SimulatorCanvas sim;
	private ArrayList<Component> comps;
	private Toolbox tb;
	
	private Component toBeAdded;
	private Component beingDragged;
	private boolean ioPressed;
	
	public SimulatorEngine(SimulatorCanvas s) {
		sim = s;
		
		// Initialize any objects or variables that need it
		tb = new Toolbox(sim);
		
		comps = new ArrayList<Component>();
		
		toBeAdded = null;
		beingDragged = null;
	}
	
	/**
	 * Runs any updates necessary on any objects
	 */
	public void update() {
		for(Component c : comps) c.update();
	}
	
	/**
	 * Paint each individual object to the screen using the passed outward facing graphics object
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
		tb.paint(g);
		for(Component c : comps) c.paint(g);
	}
	
	public void setToBeAdded(Component tba) {
		toBeAdded = tba;
	}

	public void setIOPressed(boolean bool) {
		ioPressed = bool;
	}
	
	public boolean getIOPressed() {
		return ioPressed;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (tb.wasClicked(e.getX(), e.getY())) {
			tb.mousePressed(e);

			if (toBeAdded != null) {
				toBeAdded.setX(e.getX());
				toBeAdded.setY(e.getY());
				
				comps.add(toBeAdded);
				beingDragged = toBeAdded;
				toBeAdded = null;
			}
			return;
		}
		
		for (Component c : comps) {
			if (c.wasClicked(e.getX(), e.getY())) {
				c.mousePressed(e);
				return;
			}
		}
	}

	private boolean componentColide(Component old, Component newC) {
		// IF the left side of the component will not be inside the other component
		if (newC.getX() >= old.getX() && newC.getX() <= old.getX() + old.getWidth() ||
			// IF the right side of the component will not be inside the other component
			newC.getX() + newC.getWidth() >= old.getX() &&
			newC.getX() + newC.getWidth() <= old.getX() + old.getWidth()) {
			
			// IF the top side of the component will not be inside the other component
			if (newC.getY() >= old.getY() && newC.getY() <= old.getY() + old.getHeight() ||
				// IF the bottom side of the component will not be inside the other component
				newC.getY() + newC.getHeight() >= old.getY() &&
				newC.getY() + newC.getHeight() <= old.getY() + old.getHeight()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (beingDragged == null) return;
		int x = e.getX();
		int y = e.getY();
		beingDragged.setX(x);
		beingDragged.setY(y);

		// Need to check for collisions properly
//		for (Component c : comps) {
//			if (c != beingDragged && componentColide(c, beingDragged)) comps.remove(beingDragged);
//		}

		beingDragged = null;
		sim.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (beingDragged == null) return;
		int x = e.getX();
		int y = e.getY();
		beingDragged.setX(x);
		beingDragged.setY(y);
		sim.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

}
