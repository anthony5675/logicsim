import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Class to handle most simulator logic and hold most of the back end of it.
 * Including all the different sim objects and their different states
 * @author Jayden, Daniel, Sharon and Shravan
 *
 */
public class SimulatorEngine {
	
	private SimulatorCanvas sim;
	private int dx;
	
	public SimulatorEngine(SimulatorCanvas s) {
		sim = s;
		
		// Initialize any objects or variables that need it
		dx = 0;
	}
	
	/**
	 * Runs any updates necessary on any objects
	 */
	public void update() {
		dx++;
	}
	
	/**
	 * Paint each individual object to the screen using the passed outward facing graphics object
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(dx, sim.getHeight()/2, 50, 50);
	}

}
