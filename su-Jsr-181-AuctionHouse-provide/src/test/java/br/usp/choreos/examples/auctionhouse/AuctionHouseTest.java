package br.usp.choreos.examples.auctionhouse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuctionHouseTest {

	private AuctionHouse ah;

	@Before
	public void setUp() throws Exception {
		ah = new AuctionHouse();
	}

	@Test
	public void firstAuctionPublishedIDShouldBe0() throws AuctionHouseException {
		ProductInfo pi = new ProductInfo();
		pi.setHeadline("teste");
		pi.setDescription("teste");
		pi.setInitialBid(5.5);

		int id = ah.publishAuction(pi);

		assertEquals(0, id);
	}

	@Test
	public void secondAuctionPublishedIDShouldBe1() throws AuctionHouseException {
		ProductInfo pi = new ProductInfo();
		pi.setHeadline("teste");
		pi.setDescription("teste");
		pi.setInitialBid(5.5);


		ah.publishAuction(pi);
		int id = ah.publishAuction(pi);

		assertEquals(1, id);
	}

	@Test
	public void publishedAuctionShouldHaveHeadline() {
		ProductInfo pi = new ProductInfo();

		try {
			ah.publishAuction(pi);
			pi.setDescription("teste");
			pi.setInitialBid(5.5);

			fail("published Auction should fail because it has not a Headline");
		} catch (AuctionHouseException e) {

		}

	}
	
	@Test
	public void publishedAuctionShouldHaveDescription() {
		ProductInfo pi = new ProductInfo();

		try {
			ah.publishAuction(pi);
			pi.setHeadline("teste");
			pi.setInitialBid(5.5);

			fail("published Auction should fail because it has not a Description");
		} catch (AuctionHouseException e) {

		}

	}
	
}
