package br.usp.choreos.examples.bidder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.usp.ime.choreos.vv.WSClient;

public class Bidder {

    private String uri;

    private Map<Integer, PaymentInformation> auctionPaymentInformations = new HashMap<Integer, PaymentInformation>();

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

    public void setPaymentInformation(int auctionId, String sellerUri, String paymentInformation, BigDecimal finalPrice) {
	auctionPaymentInformations.put(auctionId, new PaymentInformation(sellerUri, paymentInformation, finalPrice));
    }

    public void pay(int auctionId, String paymentConfirmation, String deliveryInformation) throws BidderException {
	PaymentInformation paymentInformation = auctionPaymentInformations.get(auctionId);
	if (paymentInformation == null)
	    throw new BidderException("Payment information for this auction not found");

	try {
	    WSClient wsClient = new WSClient(paymentInformation.getSellerUri());
	    wsClient.request("informPaymentAndDeliveryInformation", "" + auctionId, paymentConfirmation,
		    deliveryInformation);
	} catch (Exception e) {
	    throw new BidderException(e);
	}
    }

    private class PaymentInformation {
	private String sellerUri;
	private String paymentInformation;
	private BigDecimal finalPrice;

	public PaymentInformation(String sellerUri, String paymentInformation, BigDecimal finalPrice) {
	    this.sellerUri = sellerUri;
	    this.paymentInformation = paymentInformation;
	    this.finalPrice = finalPrice;
	}

	public String getSellerUri() {
	    return sellerUri;
	}

	public String getPaymentInformation() {
	    return paymentInformation;
	}

	public BigDecimal getFinalPrice() {
	    return finalPrice;
	}
    }

}
