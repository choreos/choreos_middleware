package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService(serviceName = "AuctionHouseWS", targetNamespace = "http://auctionhouse.examples.choreos.usp.br", portName = "AuctionHouseWSPort")
public class AuctionHouseWS {
    private AuctionHouse auctionHouse = new AuctionHouse();

    @WebMethod(operationName = "publishAuction")
    @WebResult(name = "auctionId")
    public int publishAuction(@WebParam(name = "headline") String headline,
	    @WebParam(name = "description") String description, @WebParam(name = "startingPrice") String startingPrice)
	    throws AuctionHouseException {
	ProductInfo productInfo = new ProductInfo();
	productInfo.setHeadline(headline);
	productInfo.setDescription(description);

	try {
	    int auctionId = auctionHouse.publishAuction(null, productInfo, new BigDecimal(startingPrice));
	    return auctionId;
	} catch (NumberFormatException e) {
	    throw new AuctionHouseException(e);
	}
    }

    @WebMethod(operationName = "placeOffer")
    public void placeOffer(@WebParam(name = "auctionId") String auctionId, @WebParam(name = "offer") String offer)
	    throws AuctionHouseException {
	try {
	    auctionHouse.placeOffer(Integer.valueOf(auctionId), new BigDecimal(offer));
	} catch (NumberFormatException e) {
	    throw new AuctionHouseException(e);
	}
    }

    @WebMethod(operationName = "getCurrentPrice")
    @WebResult(name = "currentPrice")
    public BigDecimal getCurrentPrice(@WebParam(name = "auctionId") String auctionId) throws AuctionHouseException {
	try {
	    return auctionHouse.getCurrentPrice(Integer.valueOf(auctionId));
	} catch (NumberFormatException e) {
	    throw new AuctionHouseException(e);
	}
    }

    public static void main(String[] args) {
	AuctionHouseWS auctionHouseWS = new AuctionHouseWS();
	Endpoint.publish("http://localhost:8081/auction-house-service", auctionHouseWS);
    }
}
