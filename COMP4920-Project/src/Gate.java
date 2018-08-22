import java.util.ArrayList;

public abstract class Gate extends Component {
	
	protected int inputMin;

	protected ArrayList<IO> inputs;
	protected IO output;
	
	public Gate() {
		inputs = new ArrayList<IO>();
		output = null;
	}
	
	public boolean addInput() {
		if (inputs.size() == inputMin) return false;
		inputs.add(new Input());
		return true;
	}

	public boolean addOutput() {
		if (output != null) return false;
		output = new Output();
		return true;
	}

}