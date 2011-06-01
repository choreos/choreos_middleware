package br.usp.choreos.examples.bidder;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.usp.choreos.examples.auctionhouse.AuctionHouseException;

@WebService(serviceName = "TestAuctionHouseWS", targetNamespace = "http://bidder.examples.choreos.usp.br", portName = "TestAuctionHouseWSPort")
public class TestAuctionHouseWS {

    private String lastPlaceOfferRequestParameters;

    @WebMethod(operationName = "placeOffer")
    public void placeOffer(@WebParam(name = "auctionId") String auctionId,
	    @WebParam(name = "bidderUri") String bidderUri, @WebParam(name = "offer") String offer)
	    throws AuctionHouseException {
	lastPlaceOfferRequestParameters = auctionId + "," + bidderUri + "," + offer;
    }

    public String getLastPlaceOfferRequestParameters() {
	return lastPlaceOfferRequestParameters;
    }

}
