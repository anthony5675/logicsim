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
	
	public static final int MAX_STATE = 6;
	public static final int CHALLENGE_START = 4;

	private TutorialCanvas tut;
	private ArrayList<Component> comps;
	private TutorialToolbox tb;
	private Tooltip tt; 
	private Button nextbtn;
	private Button submitbtn;
	private Button continuebtn;
	private Button clearbtn;
	private Button menubtn;
	private TruthTable table;

	// Simulator objects
	private Component toBeAdded;
	private Component beingDragged;
	private ConnectPoint ioPressed;

	private int state;

	/**
	 * Initializes a Tutorial object
	 * @param t == Canvas object which has generated this back end engine
	 * @param st == the initial state of the tutorial to manage which scene to start on
	 */
	public TutorialEngine(TutorialCanvas t, int st) {
		super(t);
		tut = t;
		
		// Initialize any objects or variables that need it
		tb = new TutorialToolbox(this, st);

		comps = new ArrayList<Component>();

		tt = new Tooltip();

		toBeAdded = null;
		beingDragged = null;

		ioPressed = null;
		
		nextbtn = new Button(635, 500, 100, 40, Color.BLACK, Color.WHITE, "Next");
		continuebtn = new Button(690, 500, 100, 40, Color.BLACK, Color.WHITE, "Continue");
		submitbtn = new Button(580, 500, 100, 40, Color.BLACK, Color.WHITE, "Submit");

		clearbtn = new Button(690, 550, 100, 40, Color.BLACK, Color.WHITE, "Clear");
		menubtn = new Button(580, 550, 100, 40, Color.BLACK, Color.WHITE, "Menu");

		setState(st);
	}

	/**
	 * Runs any updates necessary on any objects
	 */
	public void update() {
		if (tut instanceof ChallengeCanvas) {
			table.setUserTable(((ChallengeCanvas)tut).checkAnswer());
		}
		
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
		
		// Paint next button only on the tutorial sections
		// before the last one
		if(state < CHALLENGE_START - 1) nextbtn.paint(g);
		
		// Paint the continue button only during the challenges except the last screen
		if(state >= CHALLENGE_START && state < MAX_STATE) continuebtn.paint(g);
		
		// Paint the submit button and table only if it during the challenges
		if (state >= CHALLENGE_START) {
			submitbtn.paint(g);
			table.paint(g);
		}
		
		clearbtn.paint(g);
		menubtn.paint(g);
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
	 * Allows ConnectPoints to be connected using ioPressed
	 * to hold state and wait for another to be pressed
	 * @param cp = connect point clicked on
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
		
		// Add a connector to cp if it is an output point only
		// and to an input point iff it has no connector already
		if (cp.getComp().getOutPoint() == cp) {
			cp.addCon(c);
		} else {
			if (cp.getCons().size() == 0) {
				cp.addCon(c);
			}
		}

		// Add a connector to ioPressed if it is an output point only
		// and to an input point iff it has no connector already
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

	/**
	 * Sets up the properties of half a connector based on properties of the ConnectPoint
	 * @param cp1 == a connect point attempting to connect to cp2
	 * @param cp2 == a connect point attempting to connect to cp1
	 * @return true if can they be connected and false otherwise
	 */
	private boolean compatibleConnectPoints(ConnectPoint cp1, ConnectPoint cp2) {
		// Cannot connect source to source or output to output
		if ((cp1.getComp() instanceof Source) && (cp2.getComp() instanceof Source)) return false;
		if ((cp1.getComp() instanceof Output) && (cp2.getComp() instanceof Output)) return false;
		
		// Cannot connect anything to itself
		if (cp1.getComp() == cp2.getComp()) return false;
		
		// In Points can only have one connector so if cp1 is NOT an out point it is an in point
		// If so check if it has even one connector if so return false
		if (cp1.getComp().getOutPoint() != cp1 && cp1.getCons().size() > 0) return false;
		if (cp2.getComp().getOutPoint() != cp2 && cp2.getCons().size() > 0) return false;

		if ((cp1.getComp() instanceof Gate) && !(cp2.getComp() instanceof Gate)) {
			// If connect points belong to gate and not gate then
			// They are only valid if a gate out to output
			// or gate in from source
			Gate g = (Gate)cp1.getComp();

			if(g.getOutPoint() == cp1) {
				// Gate out connected to Source
				if (cp2.getComp() instanceof Source) return false;
			} else {
				// Gate in connected to Output
				if (cp2.getComp() instanceof Output) return false;
			}
		} else if (!(cp1.getComp() instanceof Gate) && (cp2.getComp() instanceof Gate)) {
			// If connect points belong to gate and not gate then
			// They are only valid if a gate out to output
			// or gate in from source
			Gate g = (Gate)cp2.getComp();

			if(g.getOutPoint() == cp2) {
				// Gate out connected to Source
				if (cp1.getComp() instanceof Source) return false;
			} else {
				// Gate in connected to Output
				if (cp1.getComp() instanceof Output) return false;
			}
		} else if ((cp1.getComp() instanceof Gate) && (cp2.getComp() instanceof Gate)) {
			// If connect points belong to gate and gate then
			// They are only valid if the out point of one connects
			// to an in point of the other
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

				// If afterwards there is a new object to add
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
					// Let it handle what happens to it
					c.mousePressed(e);
					return;
				}
			}
			
			// Next button and continue buttons advance state
			// Continue also hides the table if it is showing
			if (nextbtn.wasClicked(e.getX(), e.getY()) && state < CHALLENGE_START-1) {
				tut.setState(state + 1);
			} else if (continuebtn.wasClicked(e.getX(), e.getY()) && state >= CHALLENGE_START && state < MAX_STATE) {
				tut.setState(state + 1);
				table.setVisible(false);
			} else if (submitbtn.wasClicked(e.getX(), e.getY()) && state >= CHALLENGE_START) {
				// Submit shows then hides the table and changes its
				// text appropriately
				table.setVisible(!table.getVisible()); 
				if(table.getVisible() == true) {
					submitbtn.setText("Hide");
				} else {
					submitbtn.setText("Submit");
				}
			} else if (clearbtn.wasClicked(e.getX(), e.getY())) {
				// Clear the canvas and reset static elements
				// if it is on challenges
				clearComponents();
				if (tut instanceof ChallengeCanvas) {
					((ChallengeCanvas)tut).setState(state);
				}
			} else if (menubtn.wasClicked(e.getX(), e.getY())) {
				// return to the menu
				tut.getMenu().getMenu().setVisible(true);
				tut.setVisible(false);
				if (state > CHALLENGE_START) {
					state = CHALLENGE_START;
				} else {
					state = 0;
				}
			}
		}
	}

	/**
	 * Change state so different frames can be utilised.
	 */
	public void setState(int newState) {
		if(state < MAX_STATE) {
			state = newState;
		}
		tb.setState(state);
		clearComponents();
		
		updateTable();
	}

	/**
	 * Change what the expected table will look like per state
	 */
	private void updateTable() {
		if(state == CHALLENGE_START) {
			ArrayList<ArrayList<String>> expectedOutput = new ArrayList<ArrayList<String>>();
			ArrayList<String> indexOne = new ArrayList<String>();
			ArrayList<String> indexTwo = new ArrayList<String>();
			ArrayList<String> indexThree = new ArrayList<String>();
			
			indexOne.add("Input 1");
			indexOne.add(Integer.toString(0));
			indexOne.add(Integer.toString(0));
			indexOne.add(Integer.toString(1));
			indexOne.add(Integer.toString(1));
			
			indexTwo.add("Input 2");
			indexTwo.add(Integer.toString(0));
			indexTwo.add(Integer.toString(1));
			indexTwo.add(Integer.toString(0));
			indexTwo.add(Integer.toString(1));
			
			indexThree.add("Output");
			indexThree.add(Integer.toString(0));
			indexThree.add(Integer.toString(1));
			indexThree.add(Integer.toString(1));
			indexThree.add(Integer.toString(0));
			
			expectedOutput.add(indexOne);
			expectedOutput.add(indexTwo);
			expectedOutput.add(indexThree);
			
			table = new TruthTable(expectedOutput, expectedOutput);
			table.setVisible(false);
		} else if(state == CHALLENGE_START+1) {
			ArrayList<ArrayList<String>> expectedOutput = new ArrayList<ArrayList<String>>();
			ArrayList<String> indexOne = new ArrayList<String>();
			ArrayList<String> indexTwo = new ArrayList<String>();
			ArrayList<String> indexThree = new ArrayList<String>();
			
			indexOne.add("Input 1");
			indexOne.add(Integer.toString(0));
			indexOne.add(Integer.toString(0));
			indexOne.add(Integer.toString(1));
			indexOne.add(Integer.toString(1));
			
			indexTwo.add("Input 2");
			indexTwo.add(Integer.toString(0));
			indexTwo.add(Integer.toString(1));
			indexTwo.add(Integer.toString(0));
			indexTwo.add(Integer.toString(1));
			
			indexThree.add("Output");
			indexThree.add(Integer.toString(1));
			indexThree.add(Integer.toString(1));
			indexThree.add(Integer.toString(1));
			indexThree.add(Integer.toString(0));
			
			expectedOutput.add(indexOne);
			expectedOutput.add(indexTwo);
			expectedOutput.add(indexThree);
			
			table = new TruthTable(expectedOutput, expectedOutput);
			table.setVisible(false);
		} else if(state == CHALLENGE_START+2) {
			ArrayList<ArrayList<String>> expectedOutput = new ArrayList<ArrayList<String>>();
			ArrayList<String> indexOne = new ArrayList<String>();
			ArrayList<String> indexTwo = new ArrayList<String>();
			
			indexOne.add("Input 1");
			indexOne.add(Integer.toString(0));
			indexOne.add(Integer.toString(1));

			
			indexTwo.add("Output");
			indexTwo.add(Integer.toString(1));
			indexTwo.add(Integer.toString(0));

			
			expectedOutput.add(indexOne);
			expectedOutput.add(indexTwo);
			
			table = new TruthTable(expectedOutput, expectedOutput);
			table.setVisible(false);
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
	 * On right click show tooltip
	 * @param e == A mouse event object describing what happened
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			for (Component c : tb.getComponents()) {
				if (c.wasClicked(e.getX(), e.getY())) {
					tt.setX(c.getX() + (c.getWidth()/2));
					tt.setY(c.getY() + (c.getHeight()/2));
					tt.setInfo(c.getClass().getSimpleName());
					tt.toggleTip();
					break;
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

	/**
	 * Provide the canvas this manages when asked
	 */
	public TutorialCanvas getTut() {
		return tut;
	}
	
	/**
	 * Add a component to be drawn here
	 * @param c == component to be added
	 */
	public void addComp(Component c) {
		comps.add(c);
	}

}