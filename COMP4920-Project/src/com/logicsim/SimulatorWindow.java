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
		// Put canvases onto JFrame
		setLayout(new BorderLayout());
		setTitle("Logic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initTabs();
		add(tabs);
		pack();

		// Draw the JFrame in the middle of the screen
		setLocationRelativeTo(null);
	}

	private void initTabs() {
		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabs.add(createWorkspace(), numSims);
		tabs.setTabComponentAt(numSims, new JLabel("Workspace " + String.valueOf(numSims)));
		tabs.add(new JPanel(), "+", numSims++);
		tabs.addChangeListener(cl);
	}

	private JPanel createWorkspace() {
		SimulatorCanvas sim = new SimulatorCanvas();
		sims.add(sim);
		sim.setFocusable(true);
		sim.addMouseListener(sim.getSimEngine());
		sim.addMouseMotionListener(sim.getSimEngine());
		//sim.addKeyListener(sim);
		sim.setVisible(true);
		JPanel panel = new JPanel(new GridLayout());
		panel.add(sim);
		return panel;
	}

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
