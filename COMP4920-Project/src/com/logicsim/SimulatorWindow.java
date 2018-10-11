package com.logicsim;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
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
		tabs.add(createWorkspace(true), "Tutorial", numSims);
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
	private JPanel createWorkspace(Boolean tutFlag) {
		SimulatorCanvas sim = new SimulatorCanvas();
		sim.setFocusable(true);
		sim.addMouseListener(sim.getSimEngine());
		sim.addMouseMotionListener(sim.getSimEngine());
		//sim.addKeyListener(sim);
		sim.setVisible(true);

		// Set the state for tutorial
		if(tutFlag) {
			sim.setState(1);
		}
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
			tabs.add(createWorkspace(false), "Workspace " + String.valueOf(index), index);
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
			addWorkspace();
		}
	}

	/**
	 * Get the tabbed pane of the window
	 * @return JTabbedPane
	 */
	public JTabbedPane getTabs() {
		return tabs;
	}

}
