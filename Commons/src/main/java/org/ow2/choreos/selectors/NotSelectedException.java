package org.ow2.choreos.selectors;

public class NotSelectedException extends Exception {

    private static final long serialVersionUID = -3828919903306146926L;

    public NotSelectedException() {

    }

    public NotSelectedException(String message) {
	super(message);
    }

}
