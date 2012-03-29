package br.usp.ime.ccsl.choreos.middleware.exceptions;

/**
 * Generic exception used mainly when underlying libraries throw some unclear
 * exception
 * 
 * @author leonardo, lucas
 * 
 */
public class FrameworkException extends Exception {

    private static final long serialVersionUID = -8372153536725153278L;

    private Exception originalException;

    public FrameworkException(Exception originalException) {

        super(originalException);
        this.originalException = originalException;
    }

    public Exception getOriginalException() {
        return originalException;
    }

}
