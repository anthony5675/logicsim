package com.logicsim;
import java.util.ArrayList;

public abstract class Gate extends Component {
	
	protected int inputMin;
	protected int inputMax;

	protected ArrayList<Connector> inputs;
	protected Connector output;
	
	public Gate() {
		inputs = new ArrayList<Connector>();
		output = null;
		
		inputMax = 0;
	}
	
	public boolean addInput(Connector c) {
		if (inputMax > 0 && inputs.size() >= inputMax) return false;
		inputs.add(c);
		return true;
	}

	public boolean addOutput(Connector c) {
		if (output != null) return false;
		output = c;
		return true;
	}
	
	public boolean addConnector(int x, int y, Component from, Component to) {
		return false;
	}

}