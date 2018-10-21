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

	/**
	 * When a gate is being deleted we must ensure any connector are also
	 * @param se == The SimulatorEngine requesting this
	 */
	public void dispose(SimulatorEngine se) {
		// If any outpoint exists then we must remove all connectors
		if (outPoint != null) {
			for (Connector c : outPoint.getCons()) {
				if (c.getInput() instanceof Gate) ((Gate)c.getInput()).output = null;
				if (c.getOutput() instanceof Gate) {
					Gate g = (Gate)c.getOutput();
					if (g.inputs.contains(c)) g.inputs.remove(c);
				}
				// Inform other connected ConnectPoint to remove this Connector
				if (c.getOutPoint() == outPoint) {
					c.getInPoint().setState(false);
					c.getInPoint().removeCon(c);
				} else if (c.getInPoint() == outPoint) {
					c.getOutPoint().setState(false);
					c.getOutPoint().removeCon(c);
				}
				se.getComponents().remove(c);
			}
		}
		
		// For each inPoint there is only one Connector if any
		if (inPoints == null) return;
		for (ConnectPoint cp : inPoints) {
			if (cp.getCons().size() > 0) {
				Connector c = cp.getCons().get(0);
				if (c.getInput() instanceof Gate) ((Gate)c.getInput()).output = null;
				if (c.getOutput() instanceof Gate) {
					Gate g = (Gate)c.getOutput();
					if (g.inputs.contains(c)) g.inputs.remove(c);
				}
				// Inform other connected ConnectPoint to remove this Connector
				if (c.getOutPoint() == outPoint) {
					c.getInPoint().setState(false);
					c.getInPoint().removeCon(c);
				} else if (c.getInPoint() == outPoint) {
					c.getOutPoint().setState(false);
					c.getOutPoint().removeCon(c);
				}
				se.getComponents().remove(c);
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