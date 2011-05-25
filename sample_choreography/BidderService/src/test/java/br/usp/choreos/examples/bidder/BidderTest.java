package br.usp.choreos.examples.bidder;

import java.math.BigDecimal;

import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BidderTest {

    private Bidder bidder;

    @Before
    public void setUp() throws Exception {
	bidder = new Bidder("http://test_bidder");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void buyShouldPlaceAnOfferToAuctionHouse() throws Exception {
	TestAuctionHouseWS testAuctionHouseWS = new TestAuctionHouseWS();
	Endpoint endpoint = Endpoint.publish("http://localhost:6166/TestAuctionHouseService", testAuctionHouseWS);

	bidder.buy(0, "http://localhost:6166/TestAuctionHouseService?wsdl", BigDecimal.valueOf(1));

	Assert.assertEquals("0,http://test_bidder,1", testAuctionHouseWS.getLastPlaceOfferRequestParameters());

	endpoint.stop();
    }

    @Test
    public void requestPaymentShouldSendPaymentConfirmationAndDeliveryInformationToSeller() throws Exception {
	TestSellerWS testSellerWS = new TestSellerWS();
	Endpoint endpoint = Endpoint.publish("http://localhost:6166/TestSellerService", testSellerWS);

	bidder.pay(0, "http://localhost:6166/TestSellerService?wsdl", "payment_information", BigDecimal.valueOf(1),
		"payment_confirmation", "delivery_information");

	Assert.assertEquals("0,payment_confirmation,delivery_information", testSellerWS
		.getLastInformPaymentAndDeliveryInformationRequestParameters());

	endpoint.stop();
    }
}
