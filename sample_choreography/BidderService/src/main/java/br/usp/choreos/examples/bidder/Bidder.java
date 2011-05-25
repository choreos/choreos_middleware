package br.usp.choreos.examples.bidder;

import java.math.BigDecimal;

import br.usp.ime.choreos.vv.WSClient;

public class Bidder {

    private String uri;

    public Bidder() {
    }

    public Bidder(String uri) {
	this.uri = uri;
    }

    public void buy(int auctionId, String auctionHouseUri, BigDecimal offer) throws BidderException {
	try {
	    WSClient wsClient = new WSClient(auctionHouseUri);
	    wsClient.request("placeOffer", "" + auctionId, uri, offer.toString());
	} catch (Exception e) {
	    throw new BidderException(e);
	}
    }

    public void pay(int auctionId, String sellerUri, String paymentInformation, BigDecimal finalPrice,
	    String paymentConfirmation, String deliveryInformation) throws BidderException {
	try {
	    WSClient wsClient = new WSClient(sellerUri);
	    wsClient.request("informPaymentAndDeliveryInformation", "" + auctionId, paymentConfirmation,
		    deliveryInformation);
	} catch (Exception e) {
	    throw new BidderException(e);
	}
    }

}
