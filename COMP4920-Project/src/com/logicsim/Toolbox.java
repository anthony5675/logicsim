package com.logicsim;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle all dealings with a "Toolbox"
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class Toolbox {

	private int x, y, width, height;
	private SimulatorEngine se;
	private ArrayList<Component> comps;
	private int state;
	/**
	 * Initializes an And object
	 * @param x == The canvas this object resides in
	 */
	public Toolbox(SimulatorEngine s, int st) {
		se = s;
		state = st;

		x = 0;
		y = 0;

		width = 150;
		height = 800; // TODO: Set these correctly and add to update if we do resizing

		// Add dumby components to the toolbox
		fillToolbox();
	}

	/**
	 * If there is an image, paint just that
	 * If there is not an image paint a gray rectangle
	 * Then paint all dumby objects inside the toolbox
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
		g.setColor(new Color(50, 50, 50));
		g.fillRect(x, y, width, height);

		g.setColor(Color.WHITE);

		// Draw Toolbox title
		String t = "Toolbox";
		g.setFont(new Font("Aerial", Font.PLAIN, 24));
		FontMetrics fm = g.getFontMetrics();
		g.drawString(t, (width/2)-(fm.stringWidth(t)/2), 50);

		for (Component c : comps) c.paint(g);
	}

	/**
	 * Was the toolbox clicked inside of?
	 * @param i == the x part of the click
	 * @param j == the y part of the click
	 * @return If the click at (i, j) was inside the component
	 */
	public boolean wasClicked(int i, int j) {
		if (i >= x && i <= x + width) {
			if (j >= y && j <= y + height) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Handles what will happen if something is clicked inside of the toolbox
	 * @param e == A mouse event object describing what happened when clicked
	 */
	public void mousePressed(MouseEvent e) {

		for (Component c : comps) {
			// If one of the components was clicked
			// Add a new instance of this component to the canvas
			if (c.wasClicked(e.getX(), e.getY())) se.setToBeAdded((Component) c.clone());
		}

	}

	/**
	 * Provides the X coordinate of the toolbox
	 * @return Integer describing the X coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Provides the Y coordinate of the toolbox
	 * @return Integer describing the Y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Provides the width of the toolbox
	 * @return Integer describing the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Provides the height of the component
	 * @return Integer describing the height
	 */
	public int getHeight() {
		return height;
	}

	public ArrayList<Component> getComponents() {
		return comps;
	}

	/**
	 * Change state so different frames can be utilised.
	 *
	 */
	public void setState(int newState) {
		this.state = newState;
		
		fillToolbox();
	}

	private void fillToolbox() {
		int compHeight = 550;

		if(this.getState() == 0) {
			comps = new ArrayList<Component>();
			comps.add(new Source(width/2 - IO.WIDTH/2, 100, se));
			comps.add(new And(width/2 - Gate.WIDTH/2, 100 + (compHeight/4), se));
			comps.add(new Or(width/2 - Gate.WIDTH/2, 100 + (compHeight*2/4), se));
			comps.add(new Output(width/2 - IO.WIDTH/2, 100 + (compHeight*3/4), se));
		} else if (this.getState() == 1) {
			comps = new ArrayList<Component>();
			comps.add(new Source(width/2 - IO.WIDTH/2, 100, se));
			comps.add(new Output(width/2 - Gate.WIDTH/2, 100 + (compHeight/4), se));
			comps.add(new TextMessage(500, 25, se, "Welcome to the Digital Logic Simulator Tutorial!\n\nLogic gates are the building blocks of any digital\nsystem. In the toolbox region you can see the\ndifferent gates that are available.\n "
					+ "\nTo begin try dragging one of each connector\nontothe workspace region and connect them up\nby clicking the small boxes on their sides\nconsecutively."
					+ "\n\nTo see the output of the circuit, click the input\nand look at the corresponding output. \n\nRed corresponds to 0 and green corresponds\nto 1. "
					+ "\n\nLastly to see further information of the gates\nor their behaviour, try right clicking them. When \nyou're done click next to progress.\n\n"));
		} else if (this.getState() == 2) {
			comps = new ArrayList<Component>();
			comps.add(new Source(width/2 - IO.WIDTH/2, 100, se));
			comps.add(new And(width/2 - Gate.WIDTH/2, 100 + ((compHeight-100)/4), se));
			comps.add(new Output(width/2 - Gate.WIDTH/2, 100 + (compHeight*2/4), se));
			comps.add(new TextMessage(500, 25, se, "AND Gates\n\nNow that we have an understanding of the\n"
					+ "basics of utilising this program and the input\n/output connectors, let us try and create a\n"
					+ "circuit using an AND gate. \n\nTry conneting an AND gate to an input and \noutput connector.\n\n"
					+ "Remember you can right-click the components\nto see more information of the gates/connectors\nand "
					+ "see if you obtain the expected output."));
		} else if (this.getState() == 3) {
			comps = new ArrayList<Component>();
			comps.add(new Source(width/2 - IO.WIDTH/2, 100, se));
			comps.add(new And(width/2 - Gate.WIDTH/2, 100 + ((compHeight)/4), se));
			comps.add(new Or(width/2 - Gate.WIDTH/2, 100 + (compHeight*2/4), se));
			comps.add(new Output(width/2 - Gate.WIDTH/2, 100 + (compHeight*3/4), se));
			comps.add(new TextMessage(500, 25, se, "OR Gates\n\nWe've seen AND gates, now let us introduce\n"
					+ "OR gates.\n\n"
					+ "Use the tooltip (right-click gates) to identify \nwhich gate is which and try exploring different \ncombination."
					+ "\nCan you create the truth table ........ etc."
					+ "\n\nCongratulations you have finished the basic\ntutorial. Click the Finish button when you're\ndone."));
		}else if (this.getState() == 4) {
			comps = new ArrayList<Component>();
			comps.add(new Source(width/2 - IO.WIDTH/2, 100, se));
			comps.add(new And(width/2 - Gate.WIDTH/2, 100 + (compHeight/4), se));
			comps.add(new Or(width/2 - Gate.WIDTH/2, 100 + (compHeight*2/4), se));
			comps.add(new Output(width/2 - IO.WIDTH/2, 100 + (compHeight*3/4), se));
		}
	}

	/**
	 * @return current state
	 */
	public int getState() {
		return this.state;
	}


}
