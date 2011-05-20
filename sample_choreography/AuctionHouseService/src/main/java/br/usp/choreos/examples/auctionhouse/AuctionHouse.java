package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;
import java.util.HashMap;

public class AuctionHouse {

    private int nextId = 0;

    private HashMap<Integer, Auction> auctions = new HashMap<Integer, Auction>();

    public int publishAuction(ProductInfo productInfo, BigDecimal startingPrice) throws AuctionHouseException {

	if (productInfo.getHeadline() == null || productInfo.getHeadline().equals(""))
	    throw new AuctionHouseException("invalid headline");
	if (productInfo.getDescription() == null || productInfo.getDescription().equals(""))
	    throw new AuctionHouseException("invalid description");
	if (startingPrice == null || startingPrice.compareTo(BigDecimal.valueOf(0)) == -1)
	    throw new AuctionHouseException("invalid starting price");

	Auction auction = new Auction(productInfo, startingPrice);
	int id = nextId++;
	auctions.put(id, auction);

	return id;
    }

    public void placeOffer(int auctionId, BigDecimal offer) throws AuctionHouseException {

	Auction auction = getAuction(auctionId);

	if (auction.getCurrentPrice() == null) {
	    // No offers have been placed yet
	    if (auction.getStartingPrice().compareTo(offer) <= 0) {
		auction.setCurrentPrice(offer);
	    } else {
		throw new AuctionHouseException("offer is less than starting price");
	    }
	} else {
	    if (auction.getCurrentPrice().compareTo(offer) == -1) {
		auction.setCurrentPrice(offer);
	    } else {
		throw new AuctionHouseException("offer is less than or equal to current price");
	    }
	}
    }

    public BigDecimal getCurrentPrice(int auctionId) throws AuctionHouseException {
	Auction auction = getAuction(auctionId);
	return auction.getCurrentPrice();
    }
    
    private Auction getAuction(int auctionId) throws AuctionHouseException {
	Auction auction = auctions.get(auctionId);
	if (auction == null)
	    throw new AuctionHouseException("invalid auction id");
	return auction;

    }

}
