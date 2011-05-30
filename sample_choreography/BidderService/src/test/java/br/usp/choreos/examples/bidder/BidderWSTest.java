package br.usp.choreos.examples.bidder;

import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.choreos.vv.WSClient;

public class BidderWSTest {

    private BidderWS bidderWS;
    private Endpoint endpoint;
    private WSClient wsClient;

    @Before
    public void setUp() throws Exception {
	bidderWS = new BidderWS("http://localhost:6166/BidderService?wsdl");
	endpoint = Endpoint.create(bidderWS);
	endpoint.publish("http://localhost:6166/BidderService");

	wsClient = new WSClient("http://localhost:6166/BidderService?wsdl");
    }

    @After
    public void tearDown() {
	endpoint.stop();
    }

    @Test
    public void shouldBeAbleToCallInformPaymentInformation() throws Exception {
	wsClient.request("informPaymentInformation", "0", "http://test_seller", "payment_information", "1");
    }

}