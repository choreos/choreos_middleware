package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;

public class AuctionHouse {

    private int nextId = 0;

    public int publishAuction(ProductInfo productInfo, BigDecimal startingPrice) throws AuctionHouseException {

	if (productInfo.getHeadline() == null || productInfo.getHeadline().equals(""))
	    throw new AuctionHouseException("invalid headline");
	if (productInfo.getDescription() == null || productInfo.getDescription().equals(""))
	    throw new AuctionHouseException("invalid description");
	if (startingPrice == null || startingPrice.compareTo(BigDecimal.valueOf(0)) == -1)
	    throw new AuctionHouseException("invalid starting price");

	return nextId++;
    }

}
