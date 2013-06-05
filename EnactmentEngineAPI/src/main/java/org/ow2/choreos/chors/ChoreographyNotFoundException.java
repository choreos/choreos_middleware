package org.ow2.choreos.chors;

public class ChoreographyNotFoundException extends Exception {

    private static final long serialVersionUID = -6826427981088351560L;
    private final String chorId;

    public ChoreographyNotFoundException(String chorId) {
	this.chorId = chorId;
    }

    public ChoreographyNotFoundException(String chorId, String message) {
	super(message);
	this.chorId = chorId;
    }

    @Override
    public String toString() {
	return "Choreography " + chorId + " not found.";
    }
}
