package br.usp.choreos.examples.seller;

import java.math.BigDecimal;

import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.choreos.examples.auctionhouse.ProductInfo;
import br.usp.ime.choreos.vv.WSClient;

public class SellerWSTest {

    private Endpoint endpoint;
    private WSClient wsClient;
    private SellerWS sellerWS;
    private Endpoint auctionHouseEndpoint;
    private Endpoint bidderEndpoint;
    private Seller seller;
    private int auctionId;

    @Before
    public void setUp() throws Exception {
	sellerWS = new SellerWS("http://localhost:6166/SellerService?wsdl", "seller_id", "payment_information");
	endpoint = Endpoint.create(sellerWS);
	endpoint.publish("http://localhost:6166/SellerService");

	wsClient = new WSClient("http://localhost:6166/SellerService?wsdl");

	// Publish a TestAuctionHouseWS
	// Needed by the sell() method
	TestAuctionHouseWS testAuctionHouseWS = new TestAuctionHouseWS();
	auctionHouseEndpoint = Endpoint.publish("http://localhost:6167/TestAuctionHouseService", testAuctionHouseWS);
	ProductInfo productInfo = new ProductInfo();
	productInfo.setHeadline("headline");
	productInfo.setDescription("description");

	// Make the seller publish an auction through the TestAuctionHouseWS
	seller = sellerWS.getSeller();
	auctionId = seller.sell("http://localhost:6167/TestAuctionHouseService?wsdl", productInfo, BigDecimal
		.valueOf(0));

	// Publish a TestBidderWS
	// Needed by the informAuctionResult() method
	TestBidderWS testBidderWS = new TestBidderWS();
	bidderEndpoint = Endpoint.publish("http://localhost:6168/TestBidderService", testBidderWS);
    }

    @After
    public void tearDown() {
	endpoint.stop();
	auctionHouseEndpoint.stop();
	bidderEndpoint.stop();
    }

    @Test
    public void shouldBeAbleToRequestInformAuctionResult() throws Exception {
	wsClient.request("informAuctionResult", "" + auctionId, "" + true,
		"http://localhost:6168/TestBidderService?wsdl", "1");
    }

    @Test
    public void shouldBeAbleToRequestInformPaymentAndDeliveryInformation() throws Exception {
	// Act as if the auction has been finished
	seller.informAuctionResult(auctionId, true, "http://localhost:6168/TestBidderService?wsdl", BigDecimal
		.valueOf(1));

	wsClient.request("informPaymentAndDeliveryInformation", "" + auctionId, "payment_confirmation",
		"delivery_information");
    }

}
