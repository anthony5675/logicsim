package com.logicsim;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class to create in initialize a window for the simulator to run in
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class SimulatorWindow extends JFrame {

	public static final int WINWIDTH = 800;
	public static final int WINHEIGHT = 600;

	private JTabbedPane tabs;
	// list possibly for saving feature
	private ArrayList<SimulatorCanvas> sims = new ArrayList<SimulatorCanvas>();
	private int numSims;

	ChangeListener cl = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			addWorkspace();
		}
	};

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
		// Set basics of the window including the dimensions (using Layout) and title
		setLayout(new BorderLayout());
		setTitle("Logic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Setup the the canvas and tab
		initTabs();
		add(tabs);
		pack();

		// Draw the JFrame in the middle of the screen
		setLocationRelativeTo(null);
	}

	/**
	 * Initialize the JTabbedPane with a single workspace.
	 */
	private void initTabs() {
		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.add(createWorkspace(), numSims);
		numSims++;
		tabs.setTabComponentAt(0, new JLabel("Workspace 0"));
		tabs.add(new JPanel(), "+", numSims++);
		tabs.addChangeListener(cl);
	}

	/**
	 * Create a new workspace by adding a canvas to JPanel.
	 * Creates thread for each workspace.
	 * @return JPanel containing canvas workspace
	 */
	private JPanel createWorkspace() {
		SimulatorCanvas sim = new SimulatorCanvas();
		sims.add(sim);
		sim.setFocusable(true);
		sim.addMouseListener(sim.getSimEngine());
		sim.addMouseMotionListener(sim.getSimEngine());
		//sim.addKeyListener(sim);
		sim.setVisible(true);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new Toolbar(sim), BorderLayout.PAGE_START);
		panel.add(sim);

		Thread thread = new Thread(sim);
		thread.start();
		return panel;
	}

	/**
	 * Add a workspace when plus button is clicked.
	 */
	private void addWorkspace() {
		int index = numSims - 1;
		if(tabs.getSelectedIndex() == index) {
			tabs.add(createWorkspace(), index);
			tabs.setTabComponentAt(index, new JLabel("Workspace " + String.valueOf(index)));
			tabs.removeChangeListener(cl);
			tabs.setSelectedIndex(index);
			tabs.addChangeListener(cl);
			numSims++;
		}
	}

	/**
	 * Provides the simulator canvas list
	 * @return SimulatorCanvas list currently displaying everything
	 */
	public ArrayList<SimulatorCanvas> getSimulatorList() {
		return sims;
	}


}
