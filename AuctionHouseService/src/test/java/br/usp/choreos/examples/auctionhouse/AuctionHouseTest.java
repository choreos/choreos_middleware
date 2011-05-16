package br.usp.choreos.examples.auctionhouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class AuctionHouseTest {

    private AuctionHouse ah;

    @Before
    public void setUp() throws Exception {
	ah = new AuctionHouse();
    }

    @Test
    public void firstAuctionPublishedIDShouldBe0() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setHeadline("teste");
	pi.setDescription("teste");

	int id = ah.publishAuction(pi, BigDecimal.valueOf(0));

	assertEquals(0, id);
    }

    @Test
    public void secondAuctionPublishedIDShouldBe1() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setHeadline("teste");
	pi.setDescription("teste");

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
	ProductInfo pi = new ProductInfo();
	pi.setHeadline("teste");
	pi.setDescription("teste");

	try {
	    ah.publishAuction(pi, null);
	    fail("Expected an Exception");
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid starting price", ae.getMessage());
	}
    }

    @Test
    public void startingPriceShouldNotBeNegative() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setHeadline("headline");
	pi.setDescription("description");

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
}
