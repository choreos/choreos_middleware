package br.usp.choreos.examples.auctionhouse;

public class AuctionHouse {

    private int nextId = 0;

    public int publishAuction(ProductInfo productInfo) throws AuctionHouseException {

	if (productInfo.getHeadline() == null || productInfo.getHeadline().equals(""))
	    throw new AuctionHouseException("invalid headline");
	if (productInfo.getDescription() == null || productInfo.getDescription().equals(""))
	    throw new AuctionHouseException("invalid description");
	if (productInfo.getStartingPrice() == null)
	    throw new AuctionHouseException("invalid starting price");

	return nextId++;
    }

}
