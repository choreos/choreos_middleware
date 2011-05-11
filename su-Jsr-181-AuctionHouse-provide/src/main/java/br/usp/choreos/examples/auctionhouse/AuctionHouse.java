package br.usp.choreos.examples.auctionhouse;

public class AuctionHouse {
	
	private int nextId = 0;
	

	public int publishAuction(ProductInfo pi) throws AuctionHouseException {
		
		if (pi.getHeadline() == null || pi.getHeadline().equals(""))
			throw new AuctionHouseException("invalid headline");
		if (pi.getDescription() == null || pi.getDescription().equals(""))
			throw new AuctionHouseException("invalid description");
		
		return nextId++;
	}

}
