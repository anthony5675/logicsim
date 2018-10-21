package com.logicsim;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class TruthTable {
	private int col;
	private int row;
	private int x;
	private int y;
	private int width = 250;
	private int height = 300;
	private int posX = 500;
	private int posY = 25;
	
	
	private SimulatorEngine se;
	
	private ArrayList<ArrayList<String>> expectTable;
	private ArrayList<ArrayList<String>> userTable;
	private boolean visible; 
	
	public TruthTable(ArrayList<ArrayList<String>> expectedTable, ArrayList<ArrayList<String>> userTable){
		this.col = col;
		this.row = row;
		this.x = x;
		this.y = y;
		
		this.expectTable = expectedTable;
		this.userTable = userTable;
		this.se = se;
		this.visible = false;
	}
	
	public void paint(Graphics g) {
		if(visible == false) {
			return;
		}
		g.setColor(Color.white);
		g.fillRect(posX, posY, width, height);
		g.setColor(Color.black);
		g.setFont(new Font("Aeriel", Font.BOLD, 15));  
		g.drawString("User's Table", posX + 25, posY + 25);
		g.setFont(new Font("Aeriel", Font.PLAIN, 14));  
		for(int i = 0; i < userTable.size(); i++) {
			for(int j = 0; j < userTable.get(0).size(); j++) {
				g.drawString(userTable.get(i).get(j),posX + 50*i + 25,posY + 20*j + 50);
			}
		}
		int nextPosY = posY + 20*(userTable.get(0).size() -1) + 50;
		g.setFont(new Font("Aeriel", Font.BOLD, 15));
		g.drawString("Expected Table", posX + 25, nextPosY + 25);
		g.setFont(new Font("Aeriel", Font.PLAIN, 14));
		for(int i = 0; i < expectTable.size(); i++) {
			for(int j = 0; j < expectTable.get(0).size(); j++) {
				g.drawString(expectTable.get(i).get(j), posX + 50*i + 25, nextPosY + 20*j + 50);
			}
		}
	}
	
	public void setVisible(boolean b) {
		this.visible = b;
	}
	
	public boolean getVisible() {
		return this.visible;
	}
}
