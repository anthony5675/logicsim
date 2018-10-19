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
public class ChallengeCanvas extends SimulatorCanvas implements Runnable, MouseListener {
	private Image i;
	private Graphics buffer;

	/**
	 * Initializes a SimulatorCanvas Object
	 */
	public ChallengeCanvas() {
		super();
		setSize(800, 600); // TODO: need to get correctly
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