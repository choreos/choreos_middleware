package br.usp.choreos.examples.seller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.usp.choreos.examples.auctionhouse.ProductInfo;
import br.usp.ime.choreos.vv.ResponseItem;
import br.usp.ime.choreos.vv.WSClient;

public class Seller {

    private String uri;
    private String id;
    private String paymentInformation;
    private Map<Integer, Auction> auctions = new HashMap<Integer, Auction>();

    public Seller() {
    }
    
    public Seller(String uri, String id, String paymentInformation) {
	this.uri = uri;
	this.id = id;
	this.paymentInformation = paymentInformation;
    }

    public int sell(String auctionHouseUri, ProductInfo productInfo, BigDecimal startingPrice) throws SellerException {
	try {
	    WSClient wsClient = new WSClient(auctionHouseUri);
	    ResponseItem item = wsClient.request("publishAuction", uri, id, productInfo.getHeadline(), productInfo
		    .getDescription(), startingPrice.toString());

	    String auctionId = item.getChild("auctionId").getContent();

	    Auction auction = new Auction();
	    auction.setProductInfo(productInfo);
	    auction.setStartingPrice(startingPrice);
	    auction.setAuctionHouseUri(auctionHouseUri);
	    auction.setAuctionId(auctionId);
	    auctions.put(Integer.parseInt(auctionId), auction);
	    return Integer.parseInt(auctionId);
	} catch (Exception e) {
	    throw new SellerException(e);
	}
    }

    public void informAuctionResult(int auctionId, boolean isSold, String bidderUri, BigDecimal finalPrice)
	    throws SellerException {
	Auction auction = getAuction(auctionId);

	auction.setBidderUri(bidderUri);
	auction.setFinalPrice(finalPrice);

	try {
	    WSClient wsClient = new WSClient(bidderUri);
	    wsClient.request("sendPaymentInformation", auction.getAuctionId(), "" + auction.getFinalPrice(),
		    paymentInformation, uri);
	} catch (Exception e) {
	    throw new SellerException(e);
	}

    }

    public void confirmPayment(int auctionId, String paymentConfirmation) throws SellerException {
	Auction auction = getAuction(auctionId);
	auction.setPaymentConfirmation(paymentConfirmation);
    }

    public void informDelivery(int auctionId, String deliveryInformation) throws SellerException {
	Auction auction = getAuction(auctionId);
	auction.setDeliveryInformation(deliveryInformation);
    }

    public Auction getAuction(int auctionId) throws SellerException {
	Auction auction = auctions.get(auctionId);
	if (auction == null)
	    throw new SellerException("unknown auction id");
	return auction;
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

    public void setPaymentInformation(String paymentInformation) {
	this.paymentInformation = paymentInformation;
    }

    public String getPaymentInformation() {
	return paymentInformation;
    }

    class Auction {
	private ProductInfo productInfo;
	private String auctionHouseUri;
	private String auctionId;
	private BigDecimal startingPrice;
	private String bidderUri;
	private BigDecimal finalPrice;
	private String paymentConfirmation;
	private String deliveryInformation;

	public void setProductInfo(ProductInfo productInfo) {
	    this.productInfo = productInfo;
	}

	public ProductInfo getProductInfo() {
	    return productInfo;
	}

	public void setAuctionHouseUri(String auctionHouseUri) {
	    this.auctionHouseUri = auctionHouseUri;
	}

	public String getAuctionHouseUri(String auctionHouseUri) {
	    return auctionHouseUri;
	}

	public void setAuctionId(String auctionId) {
	    this.auctionId = auctionId;
	}

	public String getAuctionId() {
	    return auctionId;
	}

	public void setStartingPrice(BigDecimal startingPrice) {
	    this.startingPrice = startingPrice;
	}

	public BigDecimal getStartingPrice() {
	    return startingPrice;
	}

	public void setBidderUri(String bidderUri) {
	    this.bidderUri = bidderUri;
	}

	public String getBidderUri() {
	    return bidderUri;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
	    this.finalPrice = finalPrice;
	}

	public BigDecimal getFinalPrice() {
	    return finalPrice;
	}

	public void setPaymentConfirmation(String paymentConfirmation) {
	    this.paymentConfirmation = paymentConfirmation;
	}

	public String getPaymentConfirmation() {
	    return paymentConfirmation;
	}

	public void setDeliveryInformation(String deliveryInformation) {
	    this.deliveryInformation = deliveryInformation;
	}

	public String getDeliveryInformation() {
	    return deliveryInformation;
	}
    }

}
