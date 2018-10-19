package com.logicsim;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

/**
 * Class to handle everything going with the Canvas object
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class TutorialCanvas extends SimulatorCanvas implements Runnable, MouseListener {
	private int state;
	private Image i;
	private Graphics buffer;

	/**
	 * Initializes a SimulatorCanvas Object
	 */
	public TutorialCanvas() {
		setSize(800, 600); // TODO: need to get correctly
		state = 1;
		// Setup back end and start running the simulation
		se = new SimulatorEngine(this, state);
	}

	/**
	 * Change state so different frames can be utilised.
	 *
	 */

	public void setState(int newState) {
		state = newState;
		se.setState(newState);
	}

	/**
	 * @return current state
	 */

	public int getState() {
		return state;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}