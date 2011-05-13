package br.usp.choreos.examples.auctionhouse;

import java.math.BigDecimal;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName = "AuctionHouseWS", targetNamespace = "http://auctionhouse.examples.choreos.usp.br", portName = "AuctionHouseWSPort")
public class AuctionHouseWS {

    @WebMethod(operationName = "publishAuction")
    @WebResult(name = "auctionId")
    public int publishAuction(String headline, String description, String startingPrice) throws AuctionHouseException {
	ProductInfo productInfo = new ProductInfo();
	productInfo.setHeadline(headline);
	productInfo.setDescription(description);
	productInfo.setStartingPrice(new BigDecimal(startingPrice));

	int auctionId = new AuctionHouse().publishAuction(productInfo);

	return auctionId;
    }

    public static void main(String[] args) {

    }
}
