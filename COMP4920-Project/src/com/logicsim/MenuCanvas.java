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
public class MenuCanvas extends Canvas implements Runnable, MouseListener {
	private Image i;
	private Graphics buffer;
	private Button[] buttons;
	private MenuWindow menu;

	/**
	 * Initializes a SimulatorCanvas Object
	 */
	public MenuCanvas(MenuWindow m) {
		setSize(800, 600); // TODO: need to get correctly
		
		menu = m;
		
		int bwidth = 200;
		int bheight = 40;
		
		buttons = new Button[5];
		buttons[0] = new Button((getWidth()/4)-(bwidth/2), (getHeight()/2)-(bheight/2), bwidth, bheight, Color.WHITE, Color.BLACK, "Simulator");
		buttons[1] = new Button((getWidth()*3/4)-(bwidth/2), (getHeight()/2)-(bheight/2), bwidth, bheight, Color.WHITE, Color.BLACK, "Instructions");
		buttons[2] = new Button((getWidth()/4)-(bwidth/2), buttons[1].getY() + 2*bheight, bwidth, bheight, Color.WHITE, Color.BLACK, "Tutorial");
		buttons[3] = new Button((getWidth()*3/4)-(bwidth/2), buttons[1].getY() + 2*bheight, bwidth, bheight, Color.WHITE, Color.BLACK, "Challenges");
		buttons[4] = new Button((getWidth()/2)-(bwidth/2), buttons[3].getY() + 2*bheight, bwidth, bheight, Color.WHITE, Color.BLACK, "Exit");
	}

	/**
	 * From the Canvas extension, it repaints the canvas to keep the graphics up to date
	 * this happens every 17ms(ish) (about 60 times per second)
	 */
	@Override
	public void run() {
		while(true) {
			// Run object updates through SimulatorEngine
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (Button b : buttons) {
			b.paint(g);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (buttons[0].wasClicked(e.getX(), e.getY())) {
			menu.getSimulator().setVisible(true);
			menu.setVisible(false);
		} else if (buttons[1].wasClicked(e.getX(), e.getY())) {
			menu.getInstructions().setVisible(true);
			setVisible(false);
		} else if (buttons[2].wasClicked(e.getX(), e.getY())) {
			menu.getTutorial().setVisible(true);
			setVisible(false);
		} else if (buttons[3].wasClicked(e.getX(), e.getY())) {
			menu.getChallenges().setVisible(true);
			setVisible(false);
		} else if (buttons[4].wasClicked(e.getX(), e.getY())) {
			System.exit(0);
		}
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