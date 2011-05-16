package br.usp.choreos.examples.auctionhouse;

import static org.junit.Assert.assertEquals;

import javax.xml.ws.Endpoint;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.choreos.vv.ResponseItem;
import br.usp.ime.choreos.vv.WSClient;

public class AuctionHouseWSTest {
    private WSClient wsClient;

    @Before
    public void setUp() throws Exception {
	AuctionHouseWS auctionHouseWS = new AuctionHouseWS();
	Endpoint endpoint = Endpoint.create(auctionHouseWS);
	endpoint.publish("http://localhost:6166/AuctionHouseService");
	
	wsClient = new WSClient("http://localhost:6166/AuctionHouseService?wsdl");
    }
    
    @Test
    public void firstPublishShouldReturnTheFirstId() throws Exception {
	ResponseItem responseItem = wsClient.request("publishAuction", "test_headline", "test_description", "1");
	int auctionId = responseItem.getChild("auctionId").getContentAsInt();
	assertEquals(0, auctionId);
    }
}
