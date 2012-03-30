package br.usp.choreos.examples.bidder;

import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "BidderWS", targetNamespace = "http://bidder.examples.choreos.usp.br", portName = "BidderWSPort")
public class BidderWS {

    private Bidder bidder;

    public BidderWS(String uri) {
	bidder = new Bidder(uri);
    }

    @WebMethod(operationName = "informPaymentInformation")
    public void informPaymentInformation(@WebParam(name = "auctionId") String auctionId,
	    @WebParam(name = "sellerUri") String sellerUri,
	    @WebParam(name = "paymentInformation") String paymentInformation,
	    @WebParam(name = "finalPrice") String finalPrice) throws BidderException {
	try {
	    int id = Integer.parseInt(auctionId);
	    bidder.setPaymentInformation(id, sellerUri, paymentInformation, new BigDecimal(finalPrice));
	} catch (NumberFormatException e) {
	    throw new BidderException(e);
	}
    }

    public Bidder getBidder() {
	return bidder;
    }
}
