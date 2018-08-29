package com.logicsim;
import java.util.ArrayList;

public abstract class Gate extends Component {
	
	protected int inputMin;
	protected int inputMax;

	protected ArrayList<IO> inputs;
	protected IO output;
	
	public Gate() {
		inputs = new ArrayList<IO>();
		output = null;
		
		inputMax = 0;
	}
	
	public boolean addInput(int x, int y) {
		if (inputMax > 0 && inputs.size() >= inputMax) return false;
		inputs.add(new Source(x, y));
		return true;
	}

	public boolean addOutput() {
		if (output != null) return false;
		output = new Output();
		return true;
	}
	
	public boolean addConnector(int x, int y, Component from, Component to) {
		return false;
	}

}