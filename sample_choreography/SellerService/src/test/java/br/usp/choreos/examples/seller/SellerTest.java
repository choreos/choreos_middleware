package br.usp.choreos.examples.seller;

import java.math.BigDecimal;

import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.choreos.examples.auctionhouse.ProductInfo;

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
    public void sellerShouldPublishAnAuction() throws Exception {
	seller.sell(auctionHouseUri, productInfo, startingPrice);

	testAuctionHouseWS.getLastPublishAuctionParameters();

	Assert.assertEquals(seller.getUri() + "," + seller.getId() + "," + productInfo.getHeadline() + ","
		+ productInfo.getDescription() + "," + startingPrice, testAuctionHouseWS
		.getLastPublishAuctionParameters());
    }

    @Test
    public void informAuctionResultAuctionShouldBelongToThisSeller() throws Exception {
	seller.sell(auctionHouseUri, productInfo, startingPrice);

	try {
	    int unknownAuctionId = 42;
	    seller.informAuctionResult(unknownAuctionId, true, "http://bidder_uri", BigDecimal.valueOf(1));
	    Assert.fail("Expected an exception");
	} catch (Exception e) {
	    Assert.assertEquals("unknown auction id", e.getMessage());
	}
    }

    @Test
    public void contactBidderShouldCallBidder() throws Exception {
	seller.sell(auctionHouseUri, productInfo, startingPrice);

	TestBidderWS testBidderWS = new TestBidderWS();
	Endpoint endpoint = Endpoint.publish("http://localhost:6167/TestBidderService", testBidderWS);

	BigDecimal finalPrice = BigDecimal.valueOf(1);
	seller.informAuctionResult(0, true, "http://localhost:6167/TestBidderService?wsdl", finalPrice);

	Assert.assertEquals(0 + "," + finalPrice + "," + seller.getPaymentInformation() + "," + seller.getUri(),
		testBidderWS.getLastSendPaymentInformationParameters());
    }
}
