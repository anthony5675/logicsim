package com.logicsim;

/**
 * Class to act as a container for all IO components
 * @author Jayden, Andre, Mitchell, Anthony
 */
public abstract class IO extends Component {

	public static final int WIDTH = 30;
	public static final int HEIGHT = 20;
	
	public void dispose(SimulatorEngine se) {
		if (outPoint != null) {
			for (Connector c : outPoint.getCons()) {
				if (c.getOutPoint() == outPoint) {
					c.getInPoint().removeCon(c);
				} else if (c.getInPoint() == outPoint) {
					c.getOutPoint().removeCon(c);
				}
				se.getComponents().remove(c);
			}
		}
		
		for (ConnectPoint cp : inPoints) {
			if (cp.getCons().size() > 0) {
				if (cp.getCons().get(0).getOutPoint() == outPoint) {
					cp.getCons().get(0).getInPoint().removeCon(cp.getCons().get(0));
				} else if (cp.getCons().get(0).getInPoint() == outPoint) {
					cp.getCons().get(0).getOutPoint().removeCon(cp.getCons().get(0));
				}
				se.getComponents().remove(cp.getCons().get(0));
			}
		}
	}

}
