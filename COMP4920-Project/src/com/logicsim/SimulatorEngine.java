package com.logicsim;
import java.awt.*;
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
 * Including all the different sim objects
 * @author Jayden, Andre, Mitchell, Anthony
 *
 */
public class SimulatorEngine implements MouseListener, MouseInputListener {
	
	public static final int MAX_STATE = 4;

	private SimulatorCanvas sim;
	private ArrayList<Component> comps;
	private Toolbox tb;
	private Tooltip tt;

	// Simulator objects
	private Component toBeAdded;
	private Component beingDragged;
	private ConnectPoint ioPressed;
	
	/**
	 * Initializes a SimulatorEngine object
	 * @param s == Canvas object which has generated this back end engine
	 */
	public SimulatorEngine(SimulatorCanvas s) {
		sim = s;

		// Initialize any objects or variables that need it
		tb = new Toolbox(this);

		comps = new ArrayList<Component>();

		tt = new Tooltip();

		toBeAdded = null;
		beingDragged = null;

		ioPressed = null;
	}

	/**
	 * Runs any updates necessary on any objects
	 */
	public void update() {
		// Asks each component to update its own state
		for(Component c : comps) c.update();
		if (beingDragged != null) beingDragged.update();
	}

	/**
	 * Paint each individual object to the screen using the passed outward facing graphics object
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
		// Ask each object to draw itself
		tb.paint(g);
		for(Component c : comps) c.paint(g);
		if (beingDragged != null) beingDragged.paint(g);

		if(tt.isToggled()) {
			tt.paint(g);
		}
	}

	/**
	 * Return the list of components
	 * @return ArrayList containing components in workspace.
	 */
	public ArrayList<Component> getComponents() {
		return comps;
	}

	/**
	 * Allows an object to be preped for adding to the canvas
	 * @param tba == a new component which will be added to the canvas eventually
	 */
	public void setToBeAdded(Component tba) {
		toBeAdded = tba;
	}

	/**
	 * Allows indication of an IO point has just been pressed
	 * @param bool == has an IO point been pressed
	 */
	public void setIOPressed(ConnectPoint cp) {
		if(ioPressed == null) {
			ioPressed = cp;
			return;
		}

		// Check if compatible ConnectPoints
		if(!compatibleConnectPoints(ioPressed, cp)) {
			ioPressed = null;
			return;
		}
		// Handle just one ConnectPoint
		Connector c = buildConnector(cp, null);
		// Handle Second
		c = buildConnector(ioPressed, c);
		
		if (cp.getComp().getOutPoint() == cp) {
			cp.addCon(c);
		} else {
			if (cp.getCons().size() == 0) {
				cp.addCon(c);
			}
		}
		if (ioPressed.getComp().getOutPoint() == ioPressed) {
			ioPressed.addCon(c);
		} else {
			if (ioPressed.getCons().size() == 0) {
				ioPressed.addCon(c);
			}
		}
		comps.add(c);
		ioPressed = null;
	}

	public Connector buildConnector(ConnectPoint cp, Connector old) {
		Connector c;
		if (old == null) {
			c = new Connector();
		} else {
			c = old;
		}

		if (cp.getComp() instanceof Gate) {
			Gate g = (Gate)cp.getComp();
			if(g.getOutPoint() == cp) {
				c.setInput(g);
				c.setInPoint(cp);
				g.addOutput(c);
			} else {
				c.setOutput(g);
				c.setOutPoint(cp);
				g.addInput(c);
			}
		} else if (cp.getComp() instanceof Source) {
			Source s = (Source)cp.getComp();
			c.setInput(s);
			c.setInPoint(cp);
			s.setOutput(c);
		} else if (cp.getComp() instanceof Output) {
			Output o = (Output)cp.getComp();
			c.setOutput(o);
			c.setOutPoint(cp);
			o.setInput(c);
		} else {
			return null;
		}
		return c;
	}

	private boolean compatibleConnectPoints(ConnectPoint cp1, ConnectPoint cp2) {
		if ((cp1.getComp() instanceof Source) && (cp2.getComp() instanceof Source)) return false;
		if ((cp1.getComp() instanceof Output) && (cp2.getComp() instanceof Output)) return false;
		if (cp1.getComp() == cp2.getComp()) return false;
		
		// In Points can only have one connector so if cp1 is NOT an out point it is an in point
		// If so check if it has even one connector if so return false
		if (cp1.getComp().getOutPoint() != cp1 && cp1.getCons().size() > 0) return false;
		if (cp2.getComp().getOutPoint() != cp2 && cp2.getCons().size() > 0) return false;

		if ((cp1.getComp() instanceof Gate) && !(cp2.getComp() instanceof Gate)) {
			Gate g = (Gate)cp1.getComp();

			if(g.getOutPoint() == cp1) {
				// Gate out connected to Source
				if (cp2.getComp() instanceof Source) return false;
			} else {
				// Gate in connected to Output
				if (cp2.getComp() instanceof Output) return false;
			}
		} else if (!(cp1.getComp() instanceof Gate) && (cp2.getComp() instanceof Gate)) {
			Gate g = (Gate)cp2.getComp();

			if(g.getOutPoint() == cp2) {
				// Gate out connected to Source
				if (cp1.getComp() instanceof Source) return false;
			} else {
				// Gate in connected to Output
				if (cp1.getComp() instanceof Output) return false;
			}
		} else if ((cp1.getComp() instanceof Gate) && (cp2.getComp() instanceof Gate)) {
			Gate g1 = (Gate)cp1.getComp();
			Gate g2 = (Gate)cp2.getComp();

			if(g1.getOutPoint() == cp1) {
				// Gate in connected to Gate in
				if (g2.getOutPoint() == cp2) return false;
			} else {
				// Gate out connected to Gate out
				if (!(g2.getOutPoint() == cp2)) return false;
			}
		}
		return true;
	}

	/**
	 * Provides if an IO point has just been pressed
	 * @return boolean describing if an IO point was just pressed
	 */
	public ConnectPoint getIOPressed() {
		return ioPressed;
	}

	/**
	 * Handles what clicking somewhere on the canvas will do at a specific time
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (tt.isToggled()) {
			tt.toggleTip();
		}

		if (SwingUtilities.isLeftMouseButton(e)) {
			// If the click was made inside the toolbox
			if (tb.wasClicked(e.getX(), e.getY())) {
				// Let the toolbox handle what goes on inside it
				// when clicked
				tb.mousePressed(e);

				// If afterwards there is a new object to
				// then add it and prep it for being dragged
				if (toBeAdded != null) {
					toBeAdded.setX(e.getX());
					toBeAdded.setY(e.getY());

					beingDragged = toBeAdded;
					toBeAdded = null;

					sim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				// Inside toolbox mean not any object so return
				return;
			}

			// If the click was not the toolbox then check if it was any component
			for (Component c : comps) {
				if (c.wasClicked(e.getX(), e.getY())) {
					// Let it handle what happens to it (Might need to change
					c.mousePressed(e);

					sim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					return;
				}
			}
		}
	}

	/**
	 * Handles what happens when a click is stopped somewhere on the canvas at a specific time
	 * @param e == A mouse event object describing what happened when mouse was released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

		sim.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// If nothing to drag do not continue
		if (beingDragged == null) return;

		// Set final coordinates for component
		int x = e.getX();
		int y = e.getY();

		// Detect if a component was dropped with part of it in the toolbox still
		if (tb.wasClicked(beingDragged.getLeftEdge(), y)) {
			beingDragged = null;
			return;
		}

		beingDragged.setX(x);
		beingDragged.setY(y);
		beingDragged.update();

		// Need to check for collisions properly
		for (Component c : comps) {
			if (c != beingDragged && componentColide(c, beingDragged)) {
				beingDragged = null;
				return;
			}
		}

		comps.add(beingDragged);

		// Make sure nothing will continue to drag and draw everything again
		beingDragged = null;
		update();
		sim.repaint();
	}

	/**
	 * Handles what happens when the mouse is dragged somewhere on the canvas at a specific time
	 * @param e == A mouse event object describing what happened when the mouse was dragged
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// If nothing to drag do not continue
		if (beingDragged == null) {
			for (Component c : comps) {
				if (c.wasClicked(e.getX(), e.getY())) {
					beingDragged = c;
				}
			}
			if (beingDragged == null) {
				return;
			} else {
				comps.remove(beingDragged);
			}
		}

		// Set new coordinates for component
		int x = e.getX();
		int y = e.getY();
		beingDragged.setX(x);
		beingDragged.setY(y);
		update();
		sim.repaint();
	}

	/**
	 * Auto imported method, not used
	 * @param e == A mouse event object describing what happened
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			for (Component c : tb.getComponents()) {
				if (e.getX() >= c.getX() && e.getX() < c.getX() + c.getWidth()) {
					if (e.getY() >= c.getY() && e.getY() <= c.getY() + c.getHeight()) {

						// TODO: USE THIS WHEN ALL IS SETUP
//				if (c.wasClicked(e.getX(), e.getY())) {
						tt.setX(c.getX() + (c.getWidth()/2));
						tt.setY(c.getY() + (c.getHeight()/2));
						tt.setInfo(c.getClass().getSimpleName());
						tt.toggleTip();
						break;
					}
				}
			}
			
			// If it was not in the toolbox component then maybe it was on
			// worksapce component, if so a right click means delete
			Component toRemove = null;
			for (Component c : comps) {
				if (c.wasClicked(e.getX(), e.getY())) {
					toRemove = c;
				}
			}
			comps.remove(toRemove);
		}
	}

	/**
	 * This checks if at all two different components collide (one would draw on top of the other)
	 * @param old == one of the components (usually the one already there)
	 * @param newC == Another component which might collide with old
	 * @return If the components collide of not
	 */
	private boolean componentColide(Component old, Component newC) {
		// IF the left side of the component will be inside the other component
		if (newC.getLeftEdge() >= old.getLeftEdge() && newC.getLeftEdge() <= old.getRightEdge() ||
				// IF the right side of the component will be inside the other component
				newC.getRightEdge() >= old.getLeftEdge() &&
						newC.getRightEdge() <= old.getRightEdge()) {

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

	public SimulatorCanvas getSim() {
		return sim;
	}

	/**
	 * Auto imported method, not used
	 * @param e == A mouse event object describing what happened
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Auto imported method, not used
	 * @param e == A mouse event object describing what happened
	 */
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * Auto imported method, not used
	 * @param e == A mouse event object describing what happened
	 */
	@Override
	public void mouseMoved(MouseEvent e) {}

}
