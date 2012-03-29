package br.usp.choreos.examples.seller;

import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "SellerWS", targetNamespace = "http://seller.examples.choreos.usp.br", portName = "SellerWSPort")
public class SellerWS {

    private Seller seller;

    public SellerWS(String uri, String id, String paymentInformation) {
	seller = new Seller(uri, id, paymentInformation);
    }

    @WebMethod(operationName = "informAuctionResult")
    public void informAuctionResult(@WebParam(name = "auctionId") String auctionId,
	    @WebParam(name = "isSold") String isSold, @WebParam(name = "bidderUri") String bidderUri,
	    @WebParam(name = "finalPrice") String finalPrice) throws SellerException {
	try {
	    seller.informAuctionResult(Integer.parseInt(auctionId), Boolean.parseBoolean(isSold), bidderUri,
		    new BigDecimal(finalPrice));
	} catch (NumberFormatException e) {
	    throw new SellerException(e);
	}
    }

    @WebMethod(operationName = "informPaymentAndDeliveryInformation")
    public void informPaymentAndDeliveryInformation(@WebParam(name = "auctionId") String auctionId,
	    @WebParam(name = "paymentConfirmation") String paymentConfirmation,
	    @WebParam(name = "deliveryInformation") String deliveryInformation) throws SellerException {
	try {
	    int id = Integer.parseInt(auctionId);
	    seller.confirmPayment(id, paymentConfirmation);
	    seller.informDelivery(id, deliveryInformation);
	} catch (NumberFormatException e) {
	    throw new SellerException(e);
	}
    }

    public Seller getSeller() {
	return seller;
    }
}