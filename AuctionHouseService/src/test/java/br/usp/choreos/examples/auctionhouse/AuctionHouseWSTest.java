package br.usp.choreos.examples.auctionhouse;

import javax.xml.ws.Endpoint;

import org.junit.Test;

import br.usp.ime.choreos.vv.WSClient;

public class AuctionHouseWSTest {
    @Test
    public void shouldPublishTheRightWS() throws Exception {
	AuctionHouseWS auctionHouseWS = new AuctionHouseWS();
	Endpoint endpoint = Endpoint.create(auctionHouseWS);
	endpoint.publish("http://localhost:6166/AuctionHouseService");
	
	WSClient wsClient = new WSClient("http://localhost:6166/AuctionHouseService?wsdl");
	wsClient.request("publishAuction", "test_headline", "test_description", "1");
    }
}
