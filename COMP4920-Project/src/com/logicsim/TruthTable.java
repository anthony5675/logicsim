package com.logicsim;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * Class to handle everything going on with a truth table
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class TruthTable {

	private int width;
	private int height;
	private int posX;
	private int posY;
	
	private Image correctImage;
	private Image wrongImage;
	
	private ArrayList<ArrayList<String>> expectTable;
	private ArrayList<ArrayList<String>> userTable;
	private boolean visible; 
	private boolean correct;
	
	/**
	 * Initialises a TruthTable object
	 * @param expectedTable == what will be looked for as correct
	 * @param uTable == a dummy table which will be updated later
	 */
	public TruthTable(ArrayList<ArrayList<String>> expectedTable, ArrayList<ArrayList<String>> uTable){
		width = 250;
		height = 325;
		posX = 475;
		posY = 25;
		
		expectTable = expectedTable;
		userTable = uTable;
		visible = false;
		correct = true;
		
		correctImage = ImageLoader.loadImage("images/tick.png");
		wrongImage = ImageLoader.loadImage("images/cross.png");
	}
	
	/**
	 * Paint each table and then an image of correct or incorrect
	 * @param g == Outward facing Graphics object to draw to
	 */
	public void paint(Graphics g) {
		if(visible == false) {
			return;
		}
		
		// Print what the user currently has setup
		g.setColor(Color.white);
		g.fillRect(posX, posY, width, height);
		g.setColor(Color.black);
		g.setFont(new Font("Ariel", Font.BOLD, 15));  
		g.drawString("User's Table", posX + 25, posY + 25);
		g.setFont(new Font("Ariel", Font.PLAIN, 14));  
		for(int i = 0; i < userTable.size(); i++) {
			for(int j = 0; j < userTable.get(0).size(); j++) {
				g.drawString(userTable.get(i).get(j),posX + 50*i + 25,posY + 20*j + 50);
			}
		}

		// Print what is expected
		int nextPosY = posY + 20*(userTable.get(0).size() -1) + 50;
		g.setFont(new Font("Ariel", Font.BOLD, 15));
		g.drawString("Expected Table", posX + 25, nextPosY + 25);
		g.setFont(new Font("Ariel", Font.PLAIN, 14));
		for(int i = 0; i < expectTable.size(); i++) {
			for(int j = 0; j < expectTable.get(0).size(); j++) {
				g.drawString(expectTable.get(i).get(j), posX + 50*i + 25, nextPosY + 20*j + 50);
			}
		}
		
		if (correct) {
			if (correctImage != null) g.drawImage(correctImage, posX + 10, posY + 275, 40, 40, null);
		} else {
			if (wrongImage != null) g.drawImage(wrongImage, posX + 10, posY + 275, 40, 40, null);
		}
	}
	
	/**
	 * Allows the table to shown or hidden
	 * @param b == show it visible or not?
	 */
	public void setVisible(boolean b) {
		visible = b;
	}
	
	/**
	 * @return Is the table currently visible?
	 */
	public boolean getVisible() {
		return visible;
	}
	
	/**
	 * Allows for a change in the user table so it can be updated
	 * @param n == new user table
	 */
	public void setUserTable(ArrayList<ArrayList<String>> n) {
		userTable = n;
		
		checkAnswer();
	}
	
	/**
	 * On each update decides if the users solution is correct
	 * and updates the image as required
	 */
	public void checkAnswer() {
		int outputCol = userTable.size() - 1;
		for (int i = 0; i < userTable.get(outputCol).size(); i++) {
			if (!expectTable.get(outputCol).get(i).equals(userTable.get(outputCol).get(i))) {
				correct = false;
				return;
			}
		}
		correct = true;
	}
}
