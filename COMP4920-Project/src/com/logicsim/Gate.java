package com.logicsim;
import java.util.ArrayList;

/**
 * Class to hold information about gates in general
 * @author Jayden, Andre, Mitchell, Anthony
 */
public abstract class Gate extends Component {
	
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	protected int inputMin;
	protected int inputMax;

	protected ArrayList<Connector> inputs;
	protected Connector output;
	
	/**
     * Initializes a Gate object
     */
	public Gate() {
		inputs = new ArrayList<Connector>();
		output = null;
		
		inputMax = 0;
		inPoints = new ArrayList<ConnectPoint>();
	}

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
	
	/**
	 * Add another input to a gate if there can be
	 * @param c == A new connector which will act as an input
	 * @return If adding the connector was successful
	 */
	public boolean addInput(Connector c) {
		// Is there a maximum number of inputs && is there no room for more
		// If so return failure
		if (inputMax > 0 && inputs.size() >= inputMax) return false;

		// Add the new connector
		inputs.add(c);
		return true;
	}

	/**
	 * Add an output to a gate
	 * @param c == A new connector which will act as an output
	 * @return If adding the connector was successful
	 */
	public boolean addOutput(Connector c) {
		// If there already is an output
		// return failure
		if (output != null) return false;
		
		// Else add the output and return success
		output = c;
		return true;
	}
}