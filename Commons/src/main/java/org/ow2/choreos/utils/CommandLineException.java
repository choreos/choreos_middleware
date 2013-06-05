package org.ow2.choreos.utils;

/**
 * Used if exit status is > 0 (error situation)
 * 
 * @author leonardo
 * 
 */
public class CommandLineException extends Exception {

    private static final long serialVersionUID = -5893337300004636570L;

    public CommandLineException(String message) {
	super(message);
    }
}
