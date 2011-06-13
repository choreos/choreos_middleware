package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;

public class Auction {
    private Seller seller;
    private BigDecimal startingPrice;
    private Bidder currentBidder;
    private BigDecimal currentPrice;
    private ProductInfo productInfo;

  
    public Auction(Seller seller, ProductInfo productInfo, BigDecimal startingPrice) {
	this.seller = seller;
	this.startingPrice = startingPrice;
	this.productInfo = productInfo;
    }

    public BigDecimal getStartingPrice() {
	return startingPrice;
    }

    public ProductInfo getProductInfo() {
	return productInfo;
    }

    public BigDecimal getCurrentPrice() {
	return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
	this.currentPrice = currentPrice;
    }

    public void setSeller(Seller seller) {
	this.seller = seller;
    }

    public Seller getSeller() {
	return seller;
    }
    
    public void setCurrentBidder(Bidder currentBidder) {
	this.currentBidder = currentBidder;
    }

    public Bidder getCurrentBidder() {
	return currentBidder;
    }
    
    public boolean hasOffer() {
	return currentPrice != null;
    }
    
    public boolean isBetterOffer(BigDecimal newOffer) {
	if (hasOffer()) {
	    return currentPrice.compareTo(newOffer) < 0;
	} else {
	    return startingPrice.compareTo(newOffer) <= 0;
	}
    }
}
