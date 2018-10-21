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

		mw.getMenu().setMenu(mw);
		mw.getTutorial().setMenu(mw);
		mw.getChallenges().setMenu(mw);
		mw.getInstructions().setMenu(mw);
		mw.getSimulator().setMenu(mw);
	}

	/**
	 * Initializes a Menu Object
	 */
	public MenuWindow() {
		// Set basics of the window including the dimensions and title
		// Put canvas onto JFrame
		setLayout(new BorderLayout());
		setTitle("Digial Logic Sim");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		Thread instructionThread = new Thread(instructions);
		instructionThread.start();

		tutorial = new TutorialCanvas();
		tutorial.setFocusable(true);
		tutorial.addMouseListener(tutorial.getTutEngine());
		tutorial.addMouseMotionListener(tutorial.getTutEngine());
		tutorial.setVisible(false);

		Thread tutorialThread = new Thread(tutorial);
		tutorialThread.start();

		challenges = new ChallengeCanvas();
		challenges.addMouseListener(challenges.getTutEngine());
		challenges.addMouseMotionListener(challenges.getTutEngine());
		challenges.setVisible(false);

		Thread challengesThread = new Thread(challenges);
		challengesThread.start();

		simulator = new SimulatorWindow();
		simulator.pack();
		simulator.setResizable(false);
		simulator.setVisible(false);

		add(instructions);
		pack();
		add(tutorial);
		pack();
		add(challenges);
		pack();
		add(menu);
		pack();

		// Draw the JFrame in the middle of the screen
		setLocationRelativeTo(null);
	}

	public SimulatorWindow getSimulator() {
		return simulator;
	}

	public MenuCanvas getMenu() {
		return menu;
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