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

    @Test
    public void firstPublishShouldReturnTheFirstId() throws Exception {
	Item item = wsClient.request("publishAuction", "test_headline", "test_description", "1");
	int auctionId = item.getChild("auctionId").getContentAsInt();
	assertEquals(0, auctionId);
    }
    
    @After
    public void tearDown(){
	endpoint.stop();
    }
}
