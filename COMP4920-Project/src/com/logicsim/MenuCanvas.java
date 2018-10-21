package com.logicsim;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

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

	private boolean onSplash;
	private float splashOpacity;
	private Image splashScreen;
	private int waited;

	/**
	 * Initializes a SimulatorCanvas Object
	 */
	public MenuCanvas(MenuWindow m) {
		setSize(800, 600); // TODO: need to get correctly
		
		menu = m;
		
		onSplash = true;
		splashOpacity = 0f;
		waited = 0;
		
		splashScreen = ImageLoader.loadImage("images/splash.png");
		
		int bwidth = 200;
		int bheight = 40;
		
		buttons = new Button[5];
		buttons[0] = new Button((getWidth()/4)-(bwidth/2), (getHeight()/2)-(bheight/2), bwidth, bheight, Color.WHITE, Color.BLACK, "Simulator");
		buttons[1] = new Button((getWidth()*3/4)-(bwidth/2), (getHeight()/2)-(bheight/2), bwidth, bheight, Color.WHITE, Color.BLACK, "Instructions");
		buttons[2] = new Button((getWidth()/4)-(bwidth/2), buttons[1].getY() + 2*bheight, bwidth, bheight, Color.WHITE, Color.BLACK, "Tutorial");
		buttons[3] = new Button((getWidth()*3/4)-(bwidth/2), buttons[1].getY() + 2*bheight, bwidth, bheight, Color.WHITE, Color.BLACK, "Challenges");
		buttons[4] = new Button((getWidth()/2)-(bwidth/2), buttons[3].getY() + 2*bheight, bwidth, bheight, Color.WHITE, Color.BLACK, "Exit");
	
		Thread  thread = new Thread(this);
		thread.start();
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
		if (onSplash) {
			if(splashOpacity != 0) splashOpacity--;
		} else {
			if(splashOpacity < 100) splashOpacity++;
		}
		if (splashOpacity == 0) {
			waited++;
			// This controls, delay (*delay in ms*/17)
			if(waited == 50/17) {
				onSplash = false;
				return;
			}
		}
		
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
		if (!onSplash) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.setFont(new Font("Arial", Font.PLAIN, 100));
			g.setColor(Color.CYAN);
			
			String title = "Logic Simulator";
			int stringWidth = g.getFontMetrics().stringWidth(title);
	
			g.drawString(title, (getWidth()/2) - (stringWidth/2), getHeight()/4);
			
			for (Button b : buttons) {
				b.paint(g);
			}
		}
		
		// this draws the splash screen at this desired transparency using a rescaling operation
		float transparencyFactor = Math.abs(splashOpacity/100.0f - 1.0f);
		if(!(transparencyFactor == 0)) {
			RescaleOp rescale = new RescaleOp(
					new float[]{1f, 1f, 1f, transparencyFactor},
					new float[]{0f, 0f, 0f, 0f}, null);

			if(g instanceof Graphics2D) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.drawImage((BufferedImage) splashScreen, rescale, 0,0);
			} else {
				g.drawImage(splashScreen, 0, 0, this);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (onSplash) return;
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
	
	public void setMenu(MenuWindow m) {
		menu = m;
	}
}