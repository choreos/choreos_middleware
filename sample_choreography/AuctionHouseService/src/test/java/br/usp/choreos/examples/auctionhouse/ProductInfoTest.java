package br.usp.choreos.examples.auctionhouse;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ProductInfoTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void productInfoShouldHaveHealine() {
	ProductInfo pi = new ProductInfo();

	pi.setHeadline("teste");

	assertEquals("teste", pi.getHeadline());
    }

    @Test
    public void productInfoShouldHaveDescription() {
	ProductInfo pi = new ProductInfo();

	pi.setDescription("teste");

	assertEquals("teste", pi.getDescription());
    }
}
