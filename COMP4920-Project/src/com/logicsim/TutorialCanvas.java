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
	private TutorialEngine te;
	private Image i;
	private Graphics buffer;

	/**
	 * Initializes a SimulatorCanvas Object
	 */
	public TutorialCanvas() {
		setSize(800, 600); // TODO: need to get correctly
		state = 1;
		// Setup back end and start running the simulation
		te = new TutorialEngine(this);
		te.setState(1);
		
		Thread  thread = new Thread(this);
		thread.start();
	}

	/**
	 * From the Canvas extension, it repaints the canvas to keep the graphics up to date
	 * this happens every 17ms(ish) (about 60 times per second)
	 */
	@Override
	public void run() {
		while(true) {
			// Run object updates through SimulatorEngine
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

	/**
	 * Provides access to the Simulator Engine
	 * @return SimulatorEnginue which is currently handling the back of this Canvas
	 */
	public TutorialEngine getTutEngine() {
		return te;
	}
}