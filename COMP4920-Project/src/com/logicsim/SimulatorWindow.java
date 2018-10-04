package com.logicsim;
import java.awt.*;
import javax.swing.JFrame;

/**
 * Class to create in initialize a window for the simulator to run in
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class SimulatorWindow extends JFrame {

	public static final int WINWIDTH = 800;
	public static final int WINHEIGHT = 600;
	
	private SimulatorCanvas sim;
	
	/**
	 * Main method which runs the application overall
	 * @param args == arguments passed in a runtime
	 */
	public static void main(String[] args) {
		SimulatorWindow sw = new SimulatorWindow();
		sw.setVisible(true);
		sw.pack();
		sw.setResizable(false);

	}
	
	/**
	 * Initializes a Simulator Object
	 */
	public SimulatorWindow() {
		
		// Define a new main canvas and set its properties
		sim = new SimulatorCanvas();
		sim.setFocusable(true);
		sim.addMouseListener(sim.getSimEngine());
		sim.addMouseMotionListener(sim.getSimEngine());
//		sim.addKeyListener(sim);
		sim.setVisible(true);
		
		// Set basics of the window including the dimensions (using Layout) and title
		// Put canvases onto JFrame
		setLayout(new BorderLayout());
		add(sim);
		setTitle("Logic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
		// Draw the JFrame in the middle of the screen
		setLocationRelativeTo(null);

		Toolbar tbar = new Toolbar(this, sim);
	}
	
	/**
	 * Provides the simulator canvas
	 * @return SimulatorCanvas currently displaying everything
	 */
	public SimulatorCanvas getSimulator() {
		return sim;
	}
	
}
