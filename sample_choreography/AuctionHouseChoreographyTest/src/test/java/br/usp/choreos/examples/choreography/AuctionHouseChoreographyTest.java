package br.usp.choreos.examples.choreography;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.choreos.examples.auctionhouse.AuctionHouseWS;
import br.usp.choreos.examples.auctionhouse.ProductInfo;
import br.usp.choreos.examples.bidder.BidderWS;
import br.usp.choreos.examples.seller.SellerWS;
import br.usp.ime.choreos.vv.WSClient;

public class AuctionHouseChoreographyTest {

    private static Logger log = Logger.getLogger(AuctionHouseChoreographyTest.class.getName());

    private static Endpoint auctionHouseEndpoint;
    private static Endpoint bidderEndpoint;
    private static Endpoint sellerEndpoint;
    private static WSClient auctionHouseWsClient;
    private static WSClient bidderWsClient;
    private static WSClient sellerWsClient;
    private static AuctionHouseWS auctionHouseWS;
    private static BidderWS bidderWS;
    private static SellerWS sellerWS;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	auctionHouseWS = new AuctionHouseWS();
	auctionHouseEndpoint = Endpoint.create(auctionHouseWS);
	auctionHouseEndpoint.publish("http://localhost:6166/AuctionHouseService");

	auctionHouseWsClient = new WSClient("http://localhost:6166/AuctionHouseService?wsdl");

	bidderWS = new BidderWS("http://localhost:6166/BidderService?wsdl");
	bidderEndpoint = Endpoint.create(bidderWS);
	bidderEndpoint.publish("http://localhost:6166/BidderService");

	bidderWsClient = new WSClient("http://localhost:6166/BidderService?wsdl");

	sellerWS = new SellerWS("http://localhost:6166/SellerService?wsdl", "seller_id", "payment_information");
	sellerEndpoint = Endpoint.create(sellerWS);
	sellerEndpoint.publish("http://localhost:6166/SellerService");

	sellerWsClient = new WSClient("http://localhost:6166/SellerService?wsdl");
    }

    @Test
    public void performAuctionProcess() throws Exception {
	ProductInfo productInfo = new ProductInfo();
	productInfo.setHeadline("product_headline");
	productInfo.setDescription("product_description");
	int auctionId = sellerWS.getSeller().sell("http://localhost:6166/AuctionHouseService?wsdl", productInfo,
		BigDecimal.valueOf(1));

	log.info("sell done, auctionId=" + auctionId);
	assertTrue("selling error", auctionId >= 0);
	
	bidderWS.getBidder().buy(auctionId, "http://localhost:6166/AuctionHouseService?wsdl", BigDecimal.valueOf(1));
	
	log.info("offer placed");

	auctionHouseWS.getAuctionHouse().finishAuction(auctionId);
	
	log.info("auction finished");
	
//	bidderWS.getBidder().pay(0, "payment_confirmation", "delivery_information");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	auctionHouseEndpoint.stop();
	bidderEndpoint.stop();
	sellerEndpoint.stop();
    }

}
