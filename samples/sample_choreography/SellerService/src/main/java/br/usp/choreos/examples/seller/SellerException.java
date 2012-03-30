package br.usp.choreos.examples.seller;

public class SellerException extends Exception {
    
    private static final long serialVersionUID = 500081170757324961L;
    
    public SellerException() {
	super();
    }

    public SellerException(String cause, Throwable nested) {
	super(cause, nested);
    }

    public SellerException(String cause) {
	super(cause);
    }

    public SellerException(Throwable nested) {
	super(nested);
    }

}
