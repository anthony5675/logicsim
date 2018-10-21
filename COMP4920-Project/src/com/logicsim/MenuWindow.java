package com.logicsim;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MenuWindow extends JFrame {

	private MenuCanvas menu;
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
		mw.getSimulator().setMenu(mw);
	}

	/**
	 * Initializes a Menu Object
	 */
	public MenuWindow() {
		// Set basics of the window including the dimensions and title
		// Put canvas onto JFrame
		setLayout(new BorderLayout());
		setTitle("Digital Logic Sim");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create a new canvas to draw onto the JFrame
		menu = new MenuCanvas(this);
		// Allow canvas to handle its own mouse input
		menu.setFocusable(true);
		menu.addMouseListener(menu);
		menu.setVisible(true);

		tutorial = new TutorialCanvas();
		tutorial.setPreferredSize(new Dimension(800, 600));
		tutorial.setFocusable(true);
		tutorial.addMouseListener(tutorial.getTutEngine());
		tutorial.addMouseMotionListener(tutorial.getTutEngine());
		tutorial.setVisible(false);

		Thread tutorialThread = new Thread(tutorial);
		tutorialThread.start();

		challenges = new ChallengeCanvas();
		challenges.setPreferredSize(new Dimension(800, 600));
		challenges.addMouseListener(challenges.getTutEngine());
		challenges.addMouseMotionListener(challenges.getTutEngine());
		challenges.setVisible(false);

		Thread challengesThread = new Thread(challenges);
		challengesThread.start();

		simulator = new SimulatorWindow();
		simulator.pack();
		simulator.setResizable(false);
		simulator.setVisible(false);

		add(tutorial);
		add(challenges);
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

	public ChallengeCanvas getChallenges() {
		return challenges;
	}

}