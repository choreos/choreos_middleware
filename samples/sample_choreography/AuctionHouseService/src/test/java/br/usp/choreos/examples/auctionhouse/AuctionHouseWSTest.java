package br.usp.choreos.examples.auctionhouse;

import static org.junit.Assert.assertEquals;

import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.choreos.vv.Item;
import br.usp.ime.choreos.vv.WSClient;

public class AuctionHouseWSTest {
    private WSClient wsClient;
    private Endpoint endpoint;

    @Before
    public void setUp() throws Exception {
	AuctionHouseWS auctionHouseWS = new AuctionHouseWS();
	endpoint = Endpoint.create(auctionHouseWS);
	endpoint.publish("http://localhost:6166/AuctionHouseService");

	wsClient = new WSClient("http://localhost:6166/AuctionHouseService?wsdl");
    }

    @After
    public void tearDown() {
	endpoint.stop();
    }

    @Test
    public void firstPublishShouldReturnTheFirstId() throws Exception {
	Item item = wsClient.request("publishAuction", "http://test_uri", "test_seller_id", "test_headline",
		"test_description", "1");
	int auctionId = item.getChild("auctionId").getContentAsInt();
	assertEquals(0, auctionId);
    }

    @Test
    public void getCurrentPriceShouldReturnTheOffer() throws Exception {
	Item item = wsClient.request("publishAuction", "http://test_uri", "test_seller_id", "test_headline",
		"test_description", "1");
	String auctionId = item.getChild("auctionId").getContent();

	wsClient.request("placeOffer", auctionId, "http://test_uri", "42");
	Item priceResponseItem = wsClient.request("getCurrentPrice", auctionId);

	int currentPrice = priceResponseItem.getChild("currentPrice").getContentAsInt();
	assertEquals(42, currentPrice);
    }
}
