package com.logicsim;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MenuWindow extends JFrame {

	private MenuCanvas menu;
	private InstructionsCanvas instructions;
	private TutorialCanvas tutorial;
	private ChallengeCanvas challenges;
	private SimulatorWindow simulator;

	/**
	 * Main method which runs the application overall
	 * @param args == arguments passed in a runtime
	 */
	public static void main(String[] args) {
		MenuWindow mw = new MenuWindow();
		mw.pack();
		mw.setResizable(false);
		mw.setVisible(true);
	}
	
	/**
	 * Initializes a Menu Object
	 */
	public MenuWindow() {
		// Create a new canvas to draw onto the JFrame
		menu = new MenuCanvas(this);
		// Allow canvas to handle its own mouse input
		menu.setFocusable(true);
		menu.addMouseListener(menu);
		menu.setVisible(true);

		instructions = new InstructionsCanvas();
		instructions.setFocusable(true);
		instructions.addMouseListener(instructions);
		instructions.setVisible(false);
		
		tutorial = new TutorialCanvas();
		tutorial.setFocusable(true);
		tutorial.addMouseListener(tutorial);
		tutorial.setVisible(false);
		
		challenges = new ChallengeCanvas();
		challenges.setFocusable(true);
		challenges.addMouseListener(challenges);
		challenges.setVisible(false);
		
		simulator = new SimulatorWindow();
		simulator.pack();
		simulator.setResizable(false);
		simulator.setVisible(false);
		
		// Set basics of the window including the dimensions and title
		// Put canvas onto JFrame
		setLayout(new BorderLayout());
		add(instructions);
		add(tutorial);
		add(challenges);
		add(menu);
		setTitle("Digial Logic Sim");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
		// Draw the JFrame in the middle of the screen
		setLocationRelativeTo(null);
	}
	
	public SimulatorWindow getSimulator() {
		return simulator;
	}
	
	public TutorialCanvas getTutorial() {
		return tutorial;
	}
	
	public InstructionsCanvas getInstructions() {
		return instructions;
	}
	
	public ChallengeCanvas getChallenges() {
		return challenges;
	}

}
