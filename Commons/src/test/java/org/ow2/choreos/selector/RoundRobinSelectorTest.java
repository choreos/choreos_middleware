package org.ow2.choreos.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.RoundRobinSelector;

public class RoundRobinSelectorTest {
	
	private List<String> obejcts;
	
	@Before
	public void setup() {

		this.obejcts = new ArrayList<String>();
		this.obejcts.add("Object1");
		this.obejcts.add("Object2");
		this.obejcts.add("Object3");
	}
	
	@Test
	public void shouldApplyRoundRobin() throws NotSelectedException {
		
		RoundRobinSelector<String, String> rr = new RoundRobinSelector<String, String>();
		
		String requirements = "requirements";
		
		int quantity = 1;
		List<String> a = rr.select(this.obejcts, requirements, quantity);
		List<String> b = rr.select(this.obejcts, requirements, quantity);
		List<String> c = rr.select(this.obejcts, requirements, quantity);
		List<String> d = rr.select(this.obejcts, requirements, quantity);
		
		assertEquals(quantity, a.size());
		assertEquals(quantity, b.size());
		assertEquals(quantity, c.size());
		assertEquals(quantity, d.size());
		
		assertFalse(a.get(0).equals(b.get(0)));
		assertFalse(a.get(0).equals(c.get(0)));
		assertFalse(c.get(0).equals(b.get(0)));
		
		assertTrue(a.get(0).equals(d.get(0)));
		
		quantity = 2;
		List<String> aa = rr.select(this.obejcts, requirements, quantity);
		List<String> bb = rr.select(this.obejcts, requirements, quantity);
		
		assertEquals(quantity, aa.size());
		assertEquals(quantity, bb.size());
		
		assertFalse(aa.get(0).equals(aa.get(1)));
		assertFalse(aa.get(1).equals(bb.get(0)));
		assertTrue(aa.get(0).equals(bb.get(1)));
		
	}
	
	@Test(expected=NotSelectedException.class)
	public void shouldNotSelect() throws NotSelectedException {
		
		RoundRobinSelector<String, String> rr = new RoundRobinSelector<String, String>();

		String requirements = "requirements";
		int tooLargeQuantity = 5;
		rr.select(this.obejcts, requirements, tooLargeQuantity); 
	}

}
