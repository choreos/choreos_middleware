package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;

public class ProductInfo {

    private String headline;
    private String description;
    private BigDecimal startingPrice;

    public void setHeadline(String headline) {
	this.headline = headline;
    }

    public String getHeadline() {
	return headline;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Object getDescription() {
	return description;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
	this.startingPrice = startingPrice;
    }

    public BigDecimal getStartingPrice() {
	return startingPrice;
    }

}
