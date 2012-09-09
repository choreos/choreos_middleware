package org.ow2.choreos.npm;

public class NotAppliedRecipe extends Exception {

	private static final long serialVersionUID = -3910285345563830341L;

	public NotAppliedRecipe () {
		super();
	}
	
	public NotAppliedRecipe(String message) {
		super(message);
	}
}
