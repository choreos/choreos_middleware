package br.usp.choreos.examples.bidder;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import br.usp.choreos.examples.auctionhouse.AuctionHouse;
import br.usp.choreos.examples.auctionhouse.ProductInfo;

public class BidderTest {
    @Test
    public void shouldSendPlaceAnOfferToTheAuctionHouse() throws Exception {
	ProductInfo productInfo = new ProductInfo();
	productInfo.setDescription("test");
	productInfo.setHeadline("test");

	AuctionHouse auctionHouse = new AuctionHouse();
	int auctionId = auctionHouse.publishAuction(productInfo , BigDecimal.valueOf(0));	
	
	Bidder bidder = new Bidder();
	BigDecimal bidValue = BigDecimal.valueOf(10);
	bidder.placeOffer(auctionId, bidValue);
	
	assertEquals(bidValue, auctionHouse.getCurrentPrice(auctionId));
    }
}
