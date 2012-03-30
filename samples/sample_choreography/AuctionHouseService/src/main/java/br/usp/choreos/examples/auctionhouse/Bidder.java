package br.usp.choreos.examples.auctionhouse;

public class Bidder {
    private String uri;
    private String id;

    public Bidder(String uri) {
	this(uri, null);
    }
    
    public Bidder(String uri, String id) {
	this.setUri(uri);
	this.setId(id);
    }

    public void setUri(String uri) {
	this.uri = uri;
    }

    public String getUri() {
	return uri;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getId() {
	return id;
    }
}
