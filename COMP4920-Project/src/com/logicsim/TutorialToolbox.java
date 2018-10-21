package com.logicsim;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle all dealings with a "Toolbox"
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class TutorialToolbox {

	private int x, y, width, height;
	private TutorialEngine te;
	private ArrayList<Component> comps;
	private int state;
	/**
	 * Initializes an And object
	 * @param x == The canvas this object resides in
	 */
	public TutorialToolbox(TutorialEngine s, int st) {
		te = s;
		state = st;

		x = 0;
		y = 0;

		width = 150;
		height = 800; // TODO: Set these correctly and add to update if we do resizing
		
		comps = new ArrayList<Component>();

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
		g.setFont(new Font("Arial", Font.PLAIN, 24));
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
			if (c.wasClicked(e.getX(), e.getY())) te.setToBeAdded((Component) c.clone());
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
		state = newState;
		
		fillToolbox();
	}

	private void fillToolbox() {
		int compHeight = 550;
		String gap = "              ";
		comps.clear();

		if (getState() == 0) {
			comps.add(new Source(width/2 - IO.WIDTH/2, 100, te));
			comps.add(new Output(width/2 - Gate.WIDTH/2, 100 + (compHeight/4), te));
			comps.add(new TextMessage(475, 25, te, "Welcome to the Digital Logic Simulator Tutorial!\n\nLogic gates are the building blocks of any digital\nsystem. In the toolbox region you can see the\ndifferent gates that are available.\n "
					+ "\nTo begin try dragging one of each connector\nonto the workspace region and connect them up\nby clicking the small boxes on their sides\nconsecutively."
					+ "\n\nTo see the output of the circuit, click the input\nand look at the corresponding output. \n\nRed corresponds to 0 and green corresponds\nto 1. "
					+ "\n\nLastly to see further information of the gates\nor their behaviour, try right clicking them. When \nyou're done click next to progress.\n\n"));
		} else if (getState() == 1) {
			comps.add(new Source(width/2 - IO.WIDTH/2, 100, te));
			comps.add(new And(width/2 - Gate.WIDTH/2, 100 + ((compHeight-100)/4), te));
			comps.add(new Output(width/2 - Gate.WIDTH/2, 100 + (compHeight*2/4), te));
			comps.add(new TextMessage(475, 25, te, "AND Gates\n\nNow that we have an understanding of the\n"
					+ "basics of utilising this program and the input\n/output connectors, let us try and create a\n"
					+ "circuit using an AND gate. \n\nTry conneting an AND gate to an input and \noutput connector.\n\n"
					+ "The AND gate consists of two inputs and one \nouput. If both inputs are 1 then the output will \nalso be 1, otherwise the ouput is always 0.\n\n"
					+ "Remember you can right-click the components\nto see more information of the gates/connectors\nand "
					+ "see if you obtain the expected output."));
			
		} else if (getState() == 2) {
			comps.add(new Source(width/2 - IO.WIDTH/2, 100, te));
			comps.add(new And(width/2 - Gate.WIDTH/2, 100 + ((compHeight)/4), te));
			comps.add(new Or(width/2 - Gate.WIDTH/2, 100 + (compHeight*2/4), te));
			comps.add(new Output(width/2 - Gate.WIDTH/2, 100 + (compHeight*3/4), te));
			comps.add(new TextMessage(475, 25, te, "OR Gates\n\nWe've seen AND gates, now let us introduce\n"
					+ "OR gates.\n\n"
					+ "The OR gate consists of two inputs and one\n"
					+ "output. If at least one of the inputs are 1 "
					+ "then\nthe output is 1, otherwise 0.\n\n"
					+ "Try conneting an OR gate to an input and \noutput connector.\n\n"
					+ "Use the tooltip (right-click gates) to identify \nwhich gate is which and try exploring different \ncombination.\n\n"
					+ "Remember you can right-click the components\nto see more information of the gates/connectors and \n" 
					+ "see if you obtain the expected output.\n\n"));
		} else if (this.getState() == 3) {
			comps = new ArrayList<Component>();
			comps.add(new Source(width/2 - IO.WIDTH/2, 75, te));
			comps.add(new And(width/2 - Gate.WIDTH/2, 50 + (compHeight/6), te));
			comps.add(new NAnd(width/2 - Gate.WIDTH/2, 50 + (compHeight*2/6), te));
			comps.add(new NOr(width/2 - Gate.WIDTH/2, 50 + (compHeight*3/6), te));
			comps.add(new Not(width/2 - Gate.WIDTH/2, 50 + (compHeight*4/6), te));
			comps.add(new Output(width/2 - IO.WIDTH/2, 50 + (compHeight*5/6), te));
			comps.add(new TextMessage(475, 25, te, "Inverting gates\n\n"
					+ "In this tutorial you're given an AND, NAND, NOT\nand"
					+ " a NOR gate. The NOT gate is used to invert\n"
					+ "values. \n\nConnect a NOT gate and an AND gate and\n"
					+ "notice that the circuits values are the same\n"
					+ "as a NAND gate (right-click NAND to check).\n\n"
					+ "A NOR gate is a univeral gate, meaning it\n"
					+ "can represent any other gate through a\n"
					+ "combination of NOR gates.\n\n"
					+ "A trick is a NOR gate can be represented\n"
					+ "as a NOT gate, so if you connect both inputs\n"
					+ "into the same connector, it will behave as a\n"
					+ "NOT gate."));
		}else if(this.getState() == 4) {
			//Challenge mode 
			comps = new ArrayList<Component>();
			comps.add(new Source(width/2 - IO.WIDTH/2, 75, te));
			comps.add(new And(width/2 - Gate.WIDTH/2, 50 + (compHeight/8), te));
			comps.add(new NAnd(width/2 - Gate.WIDTH/2, 50 + (compHeight*2/8), te));
			comps.add(new Or(width/2 - Gate.WIDTH/2, 50 + (compHeight*3/8), te));
			comps.add(new NOr(width/2 - Gate.WIDTH/2, 50 + (compHeight*4/8), te));
			comps.add(new XOr(width/2 - Gate.WIDTH/2, 50 + (compHeight*5/8), te));
			comps.add(new Not(width/2 - Gate.WIDTH/2, 50 + (compHeight*6/8), te));
			comps.add(new Output(width/2 - IO.WIDTH/2, 50 + (compHeight*7/8), te));
			//add not gate for challenge
			comps.add(new TextMessage(475, 25, te, "Welcome to the Logic Gate Challenge!!\n\n"
				                              	+ "Here you will encounter a set of challenges."
				                              	+ "\n\nIn every challenge a set of input and output\n"
				                              	+ "gates will be pre-set onto the canvas.\n\n"
				                              	+ "Your job is to solve the truth table provided per\n"
				                              	+ "challenge.\n\n"
				                              	+ "In this challenge you're given a set of gates.\n\n"
				                              	+ "Try to obtain the truth table and when you're\n"
				                              	+ "done click next finish to progress to the next\nchallenge.\n\n"
				                              	+ "Remember that if you need any help, use the \ntooltip "
				                              	+ "as a hint.\n \n"
				                              	+ "Input 1     Input 2     Output\n" + 
					                            "0"+gap+"0"+gap+"0\n" + 
					                            "0"+gap+"1"+gap+"1\n" + 
					                            "1"+gap+"0"+gap+"1\n" + 
					                            "1"+gap+"1"+gap+"0\n \n"));

		} else if(this.getState() == 5) {
			comps = new ArrayList<Component>();
			comps.add(new And(width/2 - Gate.WIDTH/2, 100 + (compHeight/3), te));
			comps.add(new Not(width/2 - Gate.WIDTH/2, 100 + (compHeight*2/3), te));
			//add not gate for challenge
			comps.add(new TextMessage(475, 25, te, "Challenge 2\n\n" 
					                            + "In this challenge you're given an AND and a\nNOT gate.\n\n"
					                            + "Use the tooltip to figure out how to get the\ntruth table.\n\n"
					                            + "This task helps to build an NAND gate.\n\n"
					                            + "NAND refers to a "
					                            + "negative AND gate.\n\n"
					                            + "Input 1     Input 2     Output\n" + 
					                            "0"+gap+"0"+gap+"1\n" + 
					                            "0"+gap+"1"+gap+"1\n" + 
					                            "1"+gap+"0"+gap+"1\n" + 
					                            "1"+gap+"1"+gap+"0\n \n"));
		} else if(this.getState() == 6) {
			comps = new ArrayList<Component>();
			comps.add(new NOr(width/2 - Gate.WIDTH/2, 100 + (compHeight/3), te));
			comps.add(new TextMessage(475, 25, te, "Challenge 3\n\n" 
					                            + "A NOR gate is the base and a universal gate.\n\nThis means other gates "
					                            + "can be represented in a\n"
					                            + "combination of NOR gates.\n\n"
					                            + "Below is a truth table of a not gate\n\n"
					                            + "Try to reconstruct it using a NOR construction.\n\n\n"
					                            + "Input 1     Output\n"
					                            + "0"+gap+"1\n"
					                            + "1"+gap+"0\n \n"));
		}
	}

	/**
	 * @return current state
	 */
	public int getState() {
		return state;
	}


}
