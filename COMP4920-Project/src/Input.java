import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Input extends IO {
	
	private boolean state;
	
	public Input() {
		state = false;
	}
	
	public Input(boolean s) {
		state = s;
	}

	@Override
	public int calculate() {
		if (state == false) return 0;
		return 1;
	}

	@Override
	public void paint(Graphics g) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public Component clone() {
		return null;
	}
}
