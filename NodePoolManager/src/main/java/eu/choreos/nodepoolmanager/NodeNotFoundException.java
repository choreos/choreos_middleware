package eu.choreos.nodepoolmanager;

@SuppressWarnings("serial")
public class NodeNotFoundException extends Exception {

	public NodeNotFoundException() {
		super();
	}
	
	public NodeNotFoundException(String message) {
		super(message);
	}

}
