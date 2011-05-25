package br.usp.choreos.examples.auctionhouse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "TestSellerWS", targetNamespace = "http://auctionhouse.examples.choreos.usp.br", portName = "TestSellerWSPort")
public class TestSellerWS {

    private String lastAuctionResult;
    
    @WebMethod(operationName = "informAuctionResult")
    public void informAuctionResult(@WebParam(name = "auctionId") String auctionId, @WebParam(name = "isSold") String isSold,
	    @WebParam(name = "bidderUri") String bidderUri, @WebParam(name = "finalPrice") String finalPrice) {
	lastAuctionResult = auctionId + "," + isSold + "," + bidderUri + "," + finalPrice;
    }
    
    public String getLastAuctionResult() {
	return lastAuctionResult;
    }
}
