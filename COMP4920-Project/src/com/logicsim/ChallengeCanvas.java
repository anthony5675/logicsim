package com.logicsim;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

/**
 * Class to handle everything going with the Canvas object
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class ChallengeCanvas extends TutorialCanvas implements Runnable {
	private int state;
	private TutorialEngine te;
	
	private ArrayList<Source> staticSources;
	private Output staticOutput;

	/**
	 * Initializes a SimulatorCanvas Object
	 */
	public ChallengeCanvas() {
		setSize(800, 600); // TODO: need to get correctly
		// Setup back end and start running the simulation
		te = new TutorialEngine(this, TutorialEngine.CHALLENGE_START);
		
		staticOutput = null;
		staticSources = new ArrayList<Source>();
		
		setState(TutorialEngine.CHALLENGE_START);
	}

	/**
	 * From the Canvas extension, it repaints the canvas to keep the graphics up to date
	 * this happens every 17ms(ish) (about 60 times per second)
	 */
	@Override
	public void run() {
		while(true) {
			// Run object updates through TutorialEngine
			te.update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * From the Canvas extension
	 * This is called by repaint() and is used to actually draw output to the screen
	 * @param g == graphics object that can be drawn to in order to display
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(150, 150, 150));
		g.fillRect(0, 0, getWidth(), getHeight());

		// Drawn any extra objects
		te.paint(g);
	}

	/**
	 * Change state so different frames can be utilised.
	 *
	 */

	public void setState(int newState) {
		state = newState;
		te.setState(newState);
		
		changeState();
	}

	/**
	 * @return current state
	 */

	public int getState() {
		return state;
	}
	
	private void changeState() {
		staticSources.clear();
		if (state == TutorialEngine.CHALLENGE_START) {
			staticSources.add(new Source(getWidth()/4, getHeight()*2/5, te));
			staticSources.add(new Source(getWidth()/4, getHeight()*4/5, te));
			
			staticOutput = new Output(getWidth()*2/3 - 10, getHeight()*3/5 + 5, te);
			
			for (Source si : staticSources) te.addComp(si);
			te.addComp(staticOutput);
		
		} else if(state == TutorialEngine.CHALLENGE_START+1) {
			staticSources.add(new Source(getWidth()/4, getHeight()*2/5, te));
			staticSources.add(new Source(getWidth()/4, getHeight()*4/5, te));
			
			staticOutput = new Output(getWidth()*2/3 - 10, getHeight()*3/5 + 5, te);
			
			for (Source si : staticSources) te.addComp(si);
			te.addComp(staticOutput);
		} else if(state == TutorialEngine.CHALLENGE_START+2) {
			staticSources.add(new Source(getWidth()/3, getHeight()/2, te));
			staticOutput = new Output(getWidth()*2/3, getHeight()/2, te);
			
			for (Source si : staticSources) te.addComp(si);
			te.addComp(staticOutput);
		}
		
	}
	
	public boolean checkAnswer(ArrayList<Integer> expectedOutput) {
		ArrayList<Integer> userInput = new ArrayList<Integer>();
		ArrayList<Integer> userOutput = new ArrayList<Integer>();
		for(Source si : staticSources) {
			userInput.add(si.calculate());
		}
		
		if(userInput.size() == 1) {
			for(int i = 0; i < userInput.size(); i++) {
				staticSources.get(0).setState(intToBoolean(i));
				userOutput.add(staticOutput.calculate());				
			}
		} else if(userInput.size() == 2) {
			for(int i = 0; i < userInput.size(); i++) {
				staticSources.get(1).setState(intToBoolean(i));
				for(int j = 0; j < userInput.size(); j++) {
					staticSources.get(1).setState(intToBoolean(j));
					userOutput.add(staticOutput.calculate());				
				}
			}
		}
				
		
		for(int i = 0; i < staticSources.size(); i++) {
			if (userInput.get(i) == 0) {
				staticSources.get(i).setState(false);
			} else {
				staticSources.get(i).setState(true);
			}
		}
		if(expectedOutput.equals(userOutput)) {
			return true;
		}
		return false;
	}
	
	public boolean intToBoolean (int i) {
		if(i == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Provides access to the Simulator Engine
	 * @return SimulatorEnginue which is currently handling the back of this Canvas
	 */
	public TutorialEngine getTutEngine() {
		return te;
	}
}