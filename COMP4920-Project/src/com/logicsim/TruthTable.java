package com.logicsim;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class TruthTable {

	private int width = 250;
	private int height = 300;
	private int posX = 500;
	private int posY = 25;
	
	private Image correctImage;
	private Image wrongImage;
	
	private ArrayList<ArrayList<String>> expectTable;
	private ArrayList<ArrayList<String>> userTable;
	private boolean visible; 
	private boolean correct;
	
	public TruthTable(ArrayList<ArrayList<String>> expectedTable, ArrayList<ArrayList<String>> uTable){
		width = 250;
		height = 300;
		posX = 500;
		posY = 25;
		
		expectTable = expectedTable;
		userTable = uTable;
		visible = false;
		correct = true;
		
		correctImage = ImageLoader.loadImage("images/tick.png");
		wrongImage = ImageLoader.loadImage("images/cross.png");
	}
	
	public void paint(Graphics g) {
		if(visible == false) {
			return;
		}
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
			if (correctImage != null) g.drawImage(correctImage, posX, posY, null);
		} else {
			if (wrongImage != null) g.drawImage(wrongImage, posX, posY, null);
		}
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void setUserTable(ArrayList<ArrayList<String>> n) {
		userTable = n;
		
		checkAnswer();
	}
	
	public void checkAnswer() {
		for (int i = 0; i < userTable.get(userTable.size()-1).size(); i++) {
			if(expectTable.size() >= i) {
				if (expectTable.get(i) != userTable.get(i)) {
					correct = false;
					break;
				}
			}
		}
	}
}
