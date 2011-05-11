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
	pi.setStartingPrice(BigDecimal.valueOf(5));

	int id = ah.publishAuction(pi);

	assertEquals(0, id);
    }

    @Test
    public void secondAuctionPublishedIDShouldBe1() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setHeadline("teste");
	pi.setDescription("teste");
	pi.setStartingPrice(BigDecimal.valueOf(5));

	ah.publishAuction(pi);
	int id = ah.publishAuction(pi);

	assertEquals(1, id);
    }

    @Test
    public void publishedAuctionProductInfoShouldHaveHeadline() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setDescription("teste");
	pi.setStartingPrice(BigDecimal.valueOf(5));

	try {
	    ah.publishAuction(pi);
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid headline", ae.getMessage());
	}
    }

    @Test
    public void publishedAuctionProductInfoShouldHaveValidInitialBid() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setHeadline("teste");
	pi.setDescription("teste");

	pi.setStartingPrice(null);

	try {
	    ah.publishAuction(pi);
	    fail("Expected to launch an Exception");
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid starting price", ae.getMessage());
	}

    }

    @Test
    public void publishedAuctionShouldHaveDescription() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setHeadline("teste");
	pi.setStartingPrice(BigDecimal.valueOf(5));

	try {
	    ah.publishAuction(pi);
	    fail("Expected to launch an Exception");
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid description", ae.getMessage());
	}
    }

}
