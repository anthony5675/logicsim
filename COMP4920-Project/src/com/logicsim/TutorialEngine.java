package com.logicsim;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

/**
 * Class to handle most tutorial logic and hold most of the back end of it.
 * Including all the different tutorial objects
 * @author Jayden, Andre, Mitchell, Anthony
 *
 */
public class TutorialEngine extends SimulatorEngine implements MouseListener, MouseInputListener {
	
	public static final int MAX_STATE = 4;
	public static final int CHALLENGE_START = 3;

	private TutorialCanvas tut;
	private ArrayList<Component> comps;
	private TutorialToolbox tb;
	private Tooltip tt; 
	private Button next;
	private Button submit;

	// Simulator objects
	private Component toBeAdded;
	private Component beingDragged;
	private ConnectPoint ioPressed;

	private int state;
	/**
	 * Initializes a SimulatorEngine object
	 * @param s == Canvas object which has generated this back end engine
	 */
	public TutorialEngine(TutorialCanvas t, int st) {
		super(t);
		tut = t;

		state = st;
		
		// Initialize any objects or variables that need it
		tb = new TutorialToolbox(this, st);

		comps = new ArrayList<Component>();

		tt = new Tooltip();

		toBeAdded = null;
		beingDragged = null;

		ioPressed = null;
		
		next = new Button(650, 450, 100, 40, Color.BLACK, Color.WHITE, "Next");
		submit = new Button(530, 450, 100, 40, Color.BLACK, Color.WHITE, "Submit");
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
		
		next.paint(g);
		if (state >= CHALLENGE_START) submit.paint(g);
	}

	/**
	 * Remove all components from the workspace
	 */
	public void clearComponents() {
		comps.clear();
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
		
		cp.setCon(c);
		ioPressed.setCon(c);
		comps.add(c);
		ioPressed = null;
	}

	private Connector buildConnector(ConnectPoint cp, Connector old) {
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
				g.addOutput(c, cp);
			} else {
				c.setOutput(g);
				c.setOutPoint(cp);
				g.addInput(c, cp);
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
		if (cp1.getCon() != null) return false;
		if (cp2.getCon() != null) return false;

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
				}
				// Inside toolbox mean not any object so return
				return;
			}

			// If the click was not the toolbox then check if it was any component
			for (Component c : comps) {
				if (c.wasClicked(e.getX(), e.getY())) {
					// Let it handle what happens to it (Might need to change
					c.mousePressed(e);
					return;
				}
			}
			
			if (next.wasClicked(e.getX(), e.getY())) {
				setState(state + 1);
			} else if (submit.wasClicked(e.getX(), e.getY())) {
				//pop-up of their answer
				if (tut instanceof ChallengeCanvas) {
					ChallengeCanvas chc = (ChallengeCanvas)tut;
					chc.checkAnswer();
				}
			}
		}
	}

	/**
	 * Change state so different frames can be utilised.
	 *
	 */

	public void setState(int newState) {
		// TODO: Fix real max state
		state = newState % TutorialEngine.MAX_STATE;
		tb.setState(state);
	}

	/**
	 * @return current state
	 */

	public int getState() {
		return state;
	}

	/**
	 * (Currently not used)
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

	/**
	 * Handles what happens when a click is stopped somewhere on the canvas at a specific time
	 * @param e == A mouse event object describing what happened when mouse was released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// If nothing to drag do not continue
		if (beingDragged == null) return;

		// Set final coordinates for component
		int x = e.getX();
		int y = e.getY();

		// Detect if a component was dropped with part of it in the toolbox still
		if (tb.wasClicked(beingDragged.getLeftEdge(), y)) {
			comps.remove(beingDragged);
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
		tut.repaint();
	}

	/**
	 * Handles what happens when the mouse is dragged somewhere on the canvas at a specific time
	 * @param e == A mouse event object describing what happened when the mouse was dragged
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// If nothing to drag do not continue
		if (beingDragged == null) return;

		// Set new coordinates for component
		int x = e.getX();
		int y = e.getY();
		beingDragged.setX(x);
		beingDragged.setY(y);
		update();
		tut.repaint();
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
		}
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

	public TutorialCanvas getTut() {
		return tut;
	}
	
	public void addComp(Component c) {
		comps.add(c);
	}

}