package br.usp.choreos.examples.bidder;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "TestSellerWS", targetNamespace = "http://bidder.examples.choreos.usp.br", portName = "TestSellerWSPort")
public class TestSellerWS {

    private String lastInformPaymentAndDeliveryInformationRequestParameters;

    @WebMethod(operationName = "informPaymentAndDeliveryInformation")
    public void informPaymentAndDeliveryInformation(@WebParam(name = "auctionId") String auctionId,
	    @WebParam(name = "paymentConfirmation") String paymentConfirmation,
	    @WebParam(name = "deliveryInformation") String deliveryInformation) {
	lastInformPaymentAndDeliveryInformationRequestParameters = auctionId + "," + paymentConfirmation + "," + deliveryInformation; 
    }

    public String getLastInformPaymentAndDeliveryInformationRequestParameters() {
	return lastInformPaymentAndDeliveryInformationRequestParameters;
    }

}