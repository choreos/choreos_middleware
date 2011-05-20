package br.usp.choreos.examples.auctionhouse;

public class Seller {

    private String uri;
    private String id;

    public Seller(String uri) {
	this(uri, null);
    }

    public Seller(String uri, String id) {
	this.uri = uri;
	this.setId(id);
    }

    public String getUri() {
	return uri;
    }

    public void setUri(String uri) {
	this.uri = uri;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getId() {
	return id;
    }
}
