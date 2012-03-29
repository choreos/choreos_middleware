package br.usp.choreos.examples.seller;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.usp.choreos.examples.auctionhouse.AuctionHouseException;

@WebService(serviceName = "TestAuctionHouseWS", targetNamespace = "http://seller.examples.choreos.usp.br", portName = "TestAuctionHouseWSPort")
public class TestAuctionHouseWS {

    private String lastPublishAuctionParameters;

    @WebMethod(operationName = "publishAuction")
    @WebResult(name = "auctionId")
    public int publishAuction(@WebParam(name = "sellerUri") String sellerUri,
	    @WebParam(name = "sellerId") String sellerId, @WebParam(name = "headline") String headline,
	    @WebParam(name = "description") String description, @WebParam(name = "startingPrice") String startingPrice)
	    throws AuctionHouseException {
	lastPublishAuctionParameters = sellerUri + "," + sellerId + "," + headline + "," + description + ","
		+ startingPrice;
	return 0;
    }

    public String getLastPublishAuctionParameters() {
	return lastPublishAuctionParameters;
    }

}
