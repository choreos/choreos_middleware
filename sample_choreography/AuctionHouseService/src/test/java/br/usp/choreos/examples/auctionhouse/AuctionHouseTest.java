package br.usp.choreos.examples.auctionhouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class AuctionHouseTest {

    private AuctionHouse auctionHouse;
    private Seller seller;
    private ProductInfo productInfo;
    private BigDecimal startingPrice;

    @Before
    public void setUp() throws Exception {
	auctionHouse = new AuctionHouse();

	seller = new Seller("http://test_seller?wsdl");

	productInfo = new ProductInfo();
	productInfo.setHeadline("test_headline");
	productInfo.setDescription("test_description");

	startingPrice = BigDecimal.valueOf(0);
    }

    @Test
    public void firstAuctionPublishedIDShouldBe0() throws Exception {
	int id = auctionHouse.publishAuction(seller, productInfo, startingPrice);

	assertEquals(0, id);
    }

    @Test
    public void secondAuctionPublishedIDShouldBe1() throws Exception {
	auctionHouse.publishAuction(seller, productInfo, startingPrice);
	int id = auctionHouse.publishAuction(seller, productInfo, startingPrice);

	assertEquals(1, id);
    }

    @Test
    public void publishedAuctionSellerShouldNotBeNull() throws Exception {
	try {
	    auctionHouse.publishAuction(null, productInfo, startingPrice);
	    fail("Expected an Exception");
	} catch (AuctionHouseException e) {
	    assertEquals("invalid seller", e.getMessage());
	}
    }

    @Test
    public void publishedAuctionSellerUriShouldNotBeNull() throws Exception {
	try {
	    auctionHouse.publishAuction(new Seller(null), productInfo, startingPrice);
	    fail("Expected an Exception");
	} catch (AuctionHouseException e) {
	    assertEquals("invalid seller", e.getMessage());
	}
    }

    @Test
    public void publishedAuctionProductInfoShouldHaveHeadline() throws Exception {
	ProductInfo pi = new ProductInfo();
	pi.setDescription("teste");

	try {
	    auctionHouse.publishAuction(seller, pi, startingPrice);
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid headline", ae.getMessage());
	}
    }

    @Test
    public void publishedAuctionStartingPriceShouldNotBeNull() throws Exception {
	try {
	    auctionHouse.publishAuction(seller, productInfo, null);
	    fail("Expected an Exception");
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid starting price", ae.getMessage());
	}
    }

    @Test
    public void startingPriceShouldNotBeNegative() throws Exception {
	try {
	    auctionHouse.publishAuction(seller, productInfo, BigDecimal.valueOf(-1));
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
	    auctionHouse.publishAuction(seller, pi, startingPrice);
	    fail("Expected an Exception");
	} catch (AuctionHouseException ae) {
	    assertEquals("invalid description", ae.getMessage());
	}
    }

    @Test
    public void firstOfferShouldNotBeLessThanStartingPrice() throws Exception {
	try {
	    int auctionId = auctionHouse.publishAuction(seller, productInfo, BigDecimal.valueOf(2));
	    auctionHouse.placeOffer(auctionId, BigDecimal.valueOf(1));
	    fail("Expected an Exception");
	} catch (AuctionHouseException e) {
	    assertEquals("offer is less than starting price", e.getMessage());
	}
    }

    @Test
    public void offerShouldBeGreaterThanCurrentPrice() throws Exception {
	try {
	    int auctionId = auctionHouse.publishAuction(seller, productInfo, BigDecimal.valueOf(1));
	    auctionHouse.placeOffer(auctionId, BigDecimal.valueOf(1));
	    auctionHouse.placeOffer(auctionId, BigDecimal.valueOf(1));
	    fail("Expected an Exception");
	} catch (AuctionHouseException e) {
	    assertEquals("offer is less than or equal to current price", e.getMessage());
	}
    }

    @Test
    public void offerShouldBeStored() throws Exception {
	int auctionId = auctionHouse.publishAuction(seller, productInfo, BigDecimal.valueOf(1));
	auctionHouse.placeOffer(auctionId, BigDecimal.valueOf(2));
	assertEquals(BigDecimal.valueOf(2), auctionHouse.getCurrentPrice(auctionId));
    }

    @Test
    public void auctionMustExistToPlaceAnOffer() throws Exception {
	int auctionId = auctionHouse.publishAuction(seller, productInfo, BigDecimal.valueOf(1));
	try {
	    auctionHouse.placeOffer(auctionId + 1, BigDecimal.valueOf(2));
	} catch (AuctionHouseException e) {
	    assertEquals("invalid auction id", e.getMessage());
	}
    }

}
