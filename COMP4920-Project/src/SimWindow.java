import java.awt.*;
import javax.swing.JFrame;

public class SimWindow extends JFrame {

	public static final int WINWIDTH = 800;
	public static final int WINHEIGHT = 600;
	
	private SimulatorCanvas sim;
	
	/**
	 * Main method which runs the application overall
	 * @param args == arguments passed in a runtime
	 */
	public static void main(String[] args) {
		SimWindow sw = new SimWindow();
		sw.setVisible(true);
	}
	
	/**
	 * Initializes a Simulator Object
	 */
	public SimWindow() {
		
		// Define a new main canvas and set its properties
		sim = new SimulatorCanvas();
		sim.setFocusable(true);
		sim.addMouseListener(sim.getSimEngine());
//		sim.addKeyListener(sim);
		sim.setVisible(true);
		
		// Set basics of the window including the dimensions (using Layout) and title
		// Put canvases onto JFrame
		setLayout(new BorderLayout());
		add(sim);
		setTitle("Logic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		
		// Draw the JFrame in the middle of the screen
		setLocationRelativeTo(null);
	}
	
	/**
	 * Provides the simulator canvas
	 * @return usual front most canvas used for menu
	 */
	public SimulatorCanvas getSimulator() {
		return sim;
	}
	
}
