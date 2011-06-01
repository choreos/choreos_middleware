package br.usp.choreos.examples.bidder;

public class BidderException extends Exception {

    private static final long serialVersionUID = 8564382846502595280L;

    public BidderException() {
	super();
    }

    public BidderException(String cause, Throwable nested) {
	super(cause, nested);
    }

    public BidderException(String cause) {
	super(cause);
    }

    public BidderException(Throwable nested) {
	super(nested);
    }

}
