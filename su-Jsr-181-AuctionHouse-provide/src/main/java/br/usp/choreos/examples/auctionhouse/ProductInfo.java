package br.usp.choreos.examples.auctionhouse;

public class ProductInfo {
	
	private String headline;
	private String description;
	private double initialBid;

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

	public void setInitialBid(double initialBid) {
		this.initialBid = initialBid;
	}

	public Object getInitialBid() {
		return initialBid;
	}

}
