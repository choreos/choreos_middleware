package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

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
	    productInfo.setStartingPrice(new BigDecimal(startingPrice));
	} catch (NumberFormatException e) {
	    throw new AuctionHouseException(e);
	}

	int auctionId = auctionHouse.publishAuction(productInfo);

	return auctionId;
    }
}
