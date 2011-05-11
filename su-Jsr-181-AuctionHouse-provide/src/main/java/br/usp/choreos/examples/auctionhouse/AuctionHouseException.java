package br.usp.choreos.examples.auctionhouse;

public class AuctionHouseException extends Exception {

	private static final long serialVersionUID = -2318475950515670095L;

	public AuctionHouseException() {
		super();
	}

	public AuctionHouseException(String cause, Throwable nested) {
		super(cause, nested);
	}

	public AuctionHouseException(String cause) {
		super(cause);
	}

	public AuctionHouseException(Throwable nested) {
		super(nested);
	}

	
}
