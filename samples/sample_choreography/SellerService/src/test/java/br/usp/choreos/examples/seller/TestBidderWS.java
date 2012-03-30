package br.usp.choreos.examples.seller;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "TestBidderWS", targetNamespace = "http://seller.examples.choreos.usp.br", portName = "TestBidderWSPort")
public class TestBidderWS {

    private String lastSendPaymentInformationParameters;

    @WebMethod(operationName = "sendPaymentInformation")
    public void sendPaymentInformation(@WebParam(name = "auctionId") String auctionId,
	    @WebParam(name = "finalPrice") String finalPrice,
	    @WebParam(name = "paymentInformation") String paymentInformation,
	    @WebParam(name = "sellerUri") String sellerUri) {
	lastSendPaymentInformationParameters = auctionId + "," + finalPrice + "," + paymentInformation + ","
		+ sellerUri;
    }

    public String getLastSendPaymentInformationParameters() {
	return lastSendPaymentInformationParameters;
    }
}
