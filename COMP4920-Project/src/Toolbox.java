import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Toolbox {
	
	private int x, y, width, height;
	private SimulatorCanvas sim;
	private ArrayList<Component> comps;
	
	public Toolbox(SimulatorCanvas s) {
		sim = s;
		
		x = 0;
		y = 0;
		
		width = 150;
		height = sim.getHeight();
		
		comps = new ArrayList<Component>();
		comps.add(new And(width/2 - 50/2, 200));
		comps.add(new Or(width/2 - 50/2, 260));
	}
	
	public void paint(Graphics g) {
		g.setColor(new Color(150, 150, 150));
		g.fillRect(x, y, width, height);
		
		for (Component c : comps) c.paint(g);
	}

	public boolean wasClicked(int i, int j) {
		if (i >= x && i <= width) {
			if (j >= y && j <= height) {
				return true;
			}
		}
		return false;
	}

	public void mousePressed(MouseEvent e) {
		
		for (Component c : comps) {
			if (e.getX() >= c.getX() && e.getX() <= c.getX() + c.getWidth()) {
				if (e.getY() >= c.getY() && e.getY() <= c.getY() + c.getHeight()) {
					if (!(c instanceof IO)) c.mousePressed(e);
					sim.getSimEngine().setToBeAdded((Component) c.clone());
				}
			}
		}
		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
