package com.logicsim;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Class to handle everything going on with an OR gate
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class TextMessage extends Component {
	String message;

	/**
     * Initializes an Or object
     * @param x == x coordinate to set where the OR gate will draw
     * @param y == y coordinate to set where the OR gate will draw
	 * @param s 
     */
	public TextMessage(int i, int j, SimulatorEngine s, String text) {
		super();
		x = i;
		y = j;
		width = 200;
		height = 375;
		message = text;
		se = s;
	}

	/**
	 * Figures out the current state of the gate (1 or 0)
	 * @return Integer value indicating if it is on or off
	 */
	@Override
	public int calculate() {
		return 0;
	}

	/**
	 * Runs updates to the values of variables if necessary
	 */
	@Override
	public void update() {}

	/**
	 * If there is an image, paint just that
	 * If there is not an image paint a white circle with a smaller white circle attached
	 * @param g == Outward facing Graphics object to draw to
	 */
	@Override
	public void paint(Graphics g) {
		g.setFont(new Font("Ariel", Font.PLAIN, 14)); 
		createBox(g, message, x, y);
		drawFormatString(g, message, x, y);
	}
	
	/**
	 * Build the message box and draw it to the screen
	 * @param g == Graphics object to draw to
	 * @param text == text to display
	 * @param x == where to start drawing
	 * @param y == where to start drawing
	 */
	void createBox(Graphics g, String text, int x, int y) {
		int count = 0;
		int width = 0;
		for (String line : text.split("\n")) {
			if(width <  g.getFontMetrics().stringWidth(line)) {
				width = g.getFontMetrics().stringWidth(line);
			}
			if(line != "") {
				count ++;
			}
		}
		for (String line : text.split("\n\n")) {
			count--;
		}
		g.setColor(new Color(77, 77, 77));
		g.fillRoundRect(x-7, y-5, width+13, count*g.getFontMetrics().getHeight(), 5, 5);
		g.setColor(Color.CYAN);
		g.drawRoundRect(x-7, y-5, width+13, count*g.getFontMetrics().getHeight(), 5, 5);
	}
	
	/**
	 * Build and draw the actual text of the message
	 * @param g == Graphics object to draw to
	 * @param text == text to display
	 * @param x == where to start drawing
	 * @param y == where to start drawing
	 */
	void drawFormatString(Graphics g, String text, int x, int y) {
		int count = 0;
	    for (String line : text.split("\n")) {
	        if(count == 0) {
	        	g.setColor(Color.CYAN);
	        	g.drawString(line, x, y += g.getFontMetrics().getHeight()-7);
	        } else {
	        	g.setColor(Color.WHITE);
	        	g.drawString(line, x, y += g.getFontMetrics().getHeight()-7);	
	        }
	    	count++;	
	    }	
	}
	/**
	 * Handles what clicking on an OR gate will do
	 * @param e == A mouse event object describing what happened when clicked
	 */
	@Override
	public void mousePressed(MouseEvent e) {}
	
	/**
	 * Provides a way for left hand edge detection can be done
	 * @return The horizontal location of the input point (left most point)
	 */
	public int getLeftEdge() {
		return x;
	}
	
	/**
	 * Provides a way for right hand edge detection can be done
	 * @return The horizontal location of the output point + output point width (right most point)
	 */
	public int getRightEdge() {
		return x + width;
	}

	/**
	 * Create an exact copy of this object
	 * @return A new cloned object
	 */
	@Override
	public Component clone() {
		Or c = new Or(x, y, se);
		
		c.width = width;
		c.height = height;
		
		c.inputs = new ArrayList<Connector>();
		c.output = null;
		
		return c;
	}

}