package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;
import java.util.HashMap;

public class AuctionHouse {

    private int nextId = 0;

    private HashMap<Integer, Auction> auctions = new HashMap<Integer, Auction>();

    public int publishAuction(Seller seller, ProductInfo productInfo, BigDecimal startingPrice)
	    throws AuctionHouseException {

	if (seller == null || seller.getUri() == null)
	    throw new AuctionHouseException("invalid seller");
	if (productInfo.getHeadline() == null || productInfo.getHeadline().equals(""))
	    throw new AuctionHouseException("invalid headline");
	if (productInfo.getDescription() == null || productInfo.getDescription().equals(""))
	    throw new AuctionHouseException("invalid description");
	if (startingPrice == null || startingPrice.compareTo(BigDecimal.valueOf(0)) < 0)
	    throw new AuctionHouseException("invalid starting price");

	Auction auction = new Auction(seller, productInfo, startingPrice);
	int id = nextId++;
	auctions.put(id, auction);

	return id;
    }

    public void placeOffer(int auctionId, Bidder bidder, BigDecimal offer) throws AuctionHouseException {

	if (bidder == null || bidder.getUri() == null)
	    throw new AuctionHouseException("invalid bidder");

	Auction auction = getAuction(auctionId);

	if (auction.isBetterOffer(offer)) {
	    auction.setCurrentPrice(offer);
	    auction.setCurrentBidder(bidder);
	} else {
	    if (auction.hasOffer())
		throw new AuctionHouseException("offer is less than or equal to current price");
	    else
		throw new AuctionHouseException("offer is less than starting price");
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
