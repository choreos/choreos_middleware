package br.usp.choreos.examples.auctionhouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class AuctionHouseTest {

    private AuctionHouse ah;
    private ProductInfo pi;

    @Before
    public void setUp() throws Exception {
	ah = new AuctionHouse();

	pi = new ProductInfo();
	pi.setHeadline("teste");
	pi.setDescription("teste");
    }

    @Test
    public void firstAuctionPublishedIDShouldBe0() throws Exception {
	int id = ah.publishAuction(pi, BigDecimal.valueOf(0));

	assertEquals(0, id);
    }

    @Test
    public void secondAuctionPublishedIDShouldBe1() throws Exception {
	ah.publishAuction(pi, BigDecimal.valueOf(0));
	int id = ah.publishAuction(pi, BigDecimal.valueOf(0));

	assertEquals(1, id);
    }

    @Test
    public void publishedAuctionProductInfoShouldHaveHeadline() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setDescription("teste");

	try {
	    ah.publishAuction(pi, BigDecimal.valueOf(0));
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid headline", ae.getMessage());
	}
    }

    @Test
    public void startingPriceShouldNotBeNull() throws Exception {
	try {
	    ah.publishAuction(pi, null);
	    fail("Expected an Exception");
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid starting price", ae.getMessage());
	}
    }

    @Test
    public void startingPriceShouldNotBeNegative() throws Exception {
	try {
	    ah.publishAuction(pi, BigDecimal.valueOf(-1));
	    fail("Expected an Exception");
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid starting price", ae.getMessage());
	}
    }

    @Test
    public void publishedAuctionShouldHaveDescription() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setHeadline("teste");

	try {
	    ah.publishAuction(pi, BigDecimal.valueOf(0));
	    fail("Expected an Exception");
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid description", ae.getMessage());
	}
    }

    @Test
    public void firstOfferShouldNotBeLessThanStartingPrice() throws Exception {
	try {
	    int auctionId = ah.publishAuction(pi, BigDecimal.valueOf(2));
	    ah.placeOffer(auctionId, BigDecimal.valueOf(1));
	    fail("Expected an Exception");
	} catch (AuctionHouseException e) {
	    assertEquals("offer is less than starting price", e.getMessage());
	}
    }

    @Test
    public void offerShouldBeGreaterThanCurrentPrice() throws Exception {
	try {
	    int auctionId = ah.publishAuction(pi, BigDecimal.valueOf(1));
	    ah.placeOffer(auctionId, BigDecimal.valueOf(1));
	    ah.placeOffer(auctionId, BigDecimal.valueOf(1));
	    fail("Expected an Exception");
	} catch (AuctionHouseException e) {
	    assertEquals("offer is less than or equal to current price", e.getMessage());
	}
    }

    @Test
    public void offerShouldBeStored() throws Exception {
	int auctionId = ah.publishAuction(pi, BigDecimal.valueOf(1));
	ah.placeOffer(auctionId, BigDecimal.valueOf(2));
	assertEquals(BigDecimal.valueOf(2), ah.getCurrentPrice(auctionId));
    }

    @Test
    public void auctionMustExistToPlaceAnOffer() throws Exception {
	int auctionId = ah.publishAuction(pi, BigDecimal.valueOf(1));
	try {
	    ah.placeOffer(auctionId + 1, BigDecimal.valueOf(2));
	} catch (AuctionHouseException e) {
	    assertEquals("invalid auction id", e.getMessage());
	}
    }

}
