package org.ow2.choreos.breaker;

public class BreakerException extends Exception {

    private static final long serialVersionUID = 1L;

    public BreakerException() {
        super();
    }

    public BreakerException(Throwable cause) {
        super(cause);
    }
}
