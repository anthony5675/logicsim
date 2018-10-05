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
public class SimulatorCanvas extends Canvas implements Runnable {
	private int state;
	private SimulatorEngine se;
	private Image i;
	private Graphics buffer;
	
	/**
	 * Initializes a SimulatorCanvas Object
	 */
	public SimulatorCanvas() {

		setSize(800, 600); // TODO: need to get correctly
		state = 0;
		// Setup back end and start running the simulation
		se = new SimulatorEngine(this, state);
	}

	/**
	 * From the Canvas extension, it repaints the canvas to keep the graphics up to date
	 * this happens every 17ms(ish) (about 60 times per second)
	 */
	@Override
	public void run() {
		while(true) {
			// Run object updates through SimulatorEngine
			se.update();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Implemented as part of the canvas extension
	 * This is run every time before the paint method when repaint() is called
	 * It is being used to double buffer the screen to prevent screen flickering or tearing
	 * @param g == Graphics object to draw any necessary information to
	 */
	@Override
	public void update(Graphics g) {
		// if this is the first time we are double buffering
		if (i == null) {
			// create a blank image the same width and height as this canvas
			i = createImage(this.getWidth(), this.getHeight());
			buffer = i.getGraphics();
		}
		
		// make image a copy of what is currently on the screen
		buffer.setColor(getBackground());
		buffer.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		buffer.setColor(getForeground());
		paint(buffer);
		
		// display on the outward facing graphics object
		g.drawImage(i, 0, 0, this);
	}
	
	/**
	 * From the Canvas extension
	 * This is called by repaint() and is used to actually draw output to the screen
	 * @param g == graphics object that can be drawn to in order to display
	 */
	@Override
	public void paint(Graphics g) {
		// Draw a black background to start with
		this.setState(0		);
		if(this.getState() == 0) {
			g.setColor(new Color(150, 150, 150));
			g.fillRect(0, 0, getWidth(), getHeight());
		
			// Drawn any extra objects
			se.paint(g);
		} else if(this.getState() == 1) {
			g.setColor(new Color(150, 150, 150));
			g.fillRect(0, 0, getWidth(), getHeight());
		
			// Drawn any extra objects
			se.paint(g);
		}
	}
	
	/** 
	 * Change state so different frames can be utilised.
	 * 
	 */
	
	public void setState(int newState) {
		this.state = newState;
		this.se.setState(newState);
	}
	
	/**
	 * @return current state
	 */
	
	public int getState() {
		return this.state;
	}
	
	/**
     * Provides access to the Simulator Engine
     * @return SimulatorEnginue which is currently handling the back of this Canvas
     */
	public SimulatorEngine getSimEngine() {
		return se;
	}

}