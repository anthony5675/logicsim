package com.logicsim;

import java.awt.*;
import javax.swing.*;
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
	private MenuWindow mw;
	private int numSims;

	ChangeListener cl = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			addWorkspace();
		}
	};

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
		tabs.add(createWorkspace(), "Simulator", numSims);
		numSims++;
		tabs.setTabComponentAt(0, new WorkspaceTab(this));
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
		sim.setFocusable(true);
		sim.addMouseListener(sim.getSimEngine());
		sim.addMouseMotionListener(sim.getSimEngine());
		sim.setVisible(true);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new Toolbar(this, sim, mw), BorderLayout.PAGE_START);
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
			tabs.add(createWorkspace(), "Workspace " + String.valueOf(index), index);
			tabs.setTabComponentAt(index, new WorkspaceTab(this));
			tabs.removeChangeListener(cl);
			tabs.setSelectedIndex(index);
			tabs.addChangeListener(cl);
			numSims++;
		}
	}

	/**
	 * Removes a tabbed workspace via the close button
	 * @param index == The index of the tab in JTabbedPane
	 */
	public void removeWorkspace(int index) {
		tabs.remove(index);
		numSims--;

		if(index == numSims - 1 && index > 0) {
			tabs.setSelectedIndex(numSims - 2);
		} else {
			tabs.setSelectedIndex(index);
		}

		if(numSims == 1) {
			System.exit(0);
		}
	}

	/**
	 * Get the tabbed pane of the window
	 * @return JTabbedPane
	 */
	public JTabbedPane getTabs() {
		return tabs;
	}

	/**
	 * Set the menu window for this frame
	 * @param mw == The main menu which runs the application
	 */
	public void setMenu(MenuWindow mw) {
		this.mw = mw;
	}

	/**
	 * Switch to the initial menu when requested from the toolbar.
	 */
	public void switchToMenu() {
		setVisible(false);
		mw.setVisible(true);
	}

}
