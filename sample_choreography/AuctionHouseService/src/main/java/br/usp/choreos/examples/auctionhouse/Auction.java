package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;

public class Auction {
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private ProductInfo productInfo;

    public Auction(ProductInfo productInfo, BigDecimal startingPrice) {
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
}
