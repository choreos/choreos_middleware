package br.usp.choreos.examples.seller;

import java.math.BigDecimal;

import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.choreos.examples.auctionhouse.ProductInfo;
import br.usp.choreos.examples.seller.Seller.Auction;

public class SellerTest {

    private Seller seller;
    private TestAuctionHouseWS testAuctionHouseWS;
    private Endpoint endpoint;
    private String auctionHouseUri;
    private ProductInfo productInfo;
    private BigDecimal startingPrice;

    @Before
    public void setUp() throws Exception {
	seller = new Seller("http://seller_uri", "seller_id", "payment_information");

	testAuctionHouseWS = new TestAuctionHouseWS();
	endpoint = Endpoint.create(testAuctionHouseWS);
	endpoint.publish("http://localhost:6166/TestAuctionHouseService");

	auctionHouseUri = "http://localhost:6166/TestAuctionHouseService?wsdl";

	productInfo = new ProductInfo();
	productInfo.setHeadline("headline");
	productInfo.setDescription("description");

	startingPrice = BigDecimal.valueOf(0);
    }

    @After
    public void tearDown() {
	endpoint.stop();
    }

    @Test
    public void sellShouldPublishAnAuctionToAuctionHouse() throws Exception {
	seller.sell(auctionHouseUri, productInfo, startingPrice);

	testAuctionHouseWS.getLastPublishAuctionParameters();

	Assert.assertEquals(seller.getUri() + "," + seller.getId() + "," + productInfo.getHeadline() + ","
		+ productInfo.getDescription() + "," + startingPrice, testAuctionHouseWS
		.getLastPublishAuctionParameters());
    }
    
    @Test
    public void getAuctionShouldThrowExceptionWhenAuctionIdIsNotFound() throws Exception {
	try {
	    seller.getAuction(42);
	    Assert.fail("Expected an exception");
	} catch (SellerException e) {
	    Assert.assertEquals("unknown auction id", e.getMessage());
	}
    }
    
    @Test
    public void sellShouldSaveAuctionUnderTheReturnedAuctionId() throws Exception {
	int auctionId = seller.sell(auctionHouseUri, productInfo, startingPrice);
	
	Auction auction = seller.getAuction(auctionId);
	Assert.assertEquals(productInfo, auction.getProductInfo());
	Assert.assertEquals(startingPrice, auction.getStartingPrice());
    }

    @Test
    public void informAuctionResultShouldSendPaymentInformationToBidder() throws Exception {
	int auctionId = seller.sell(auctionHouseUri, productInfo, startingPrice);

	TestBidderWS testBidderWS = new TestBidderWS();
	Endpoint endpoint = Endpoint.publish("http://localhost:6167/TestBidderService", testBidderWS);

	BigDecimal finalPrice = BigDecimal.valueOf(1);
	seller.informAuctionResult(auctionId, true, "http://localhost:6167/TestBidderService?wsdl", finalPrice);

	Assert.assertEquals(auctionId + "," + finalPrice + "," + seller.getPaymentInformation() + "," + seller.getUri(),
		testBidderWS.getLastSendPaymentInformationParameters());
    }

    @Test
    public void confirmPaymentShouldStorePaymentConfirmation() throws Exception {
	int auctionId = seller.sell(auctionHouseUri, productInfo, startingPrice);
	seller.confirmPayment(auctionId, "payment_confirmation");

	Auction auction = seller.getAuction(auctionId);
	Assert.assertEquals("payment_confirmation", auction.getPaymentConfirmation());
    }

    @Test
    public void informDeliveryShouldStoreDeliveryInformation() throws Exception {
	int auctionId = seller.sell(auctionHouseUri, productInfo, startingPrice);
	seller.informDelivery(auctionId, "delivery_information");

	Auction auction = seller.getAuction(auctionId);
	Assert.assertEquals("delivery_information", auction.getDeliveryInformation());
    }
}
