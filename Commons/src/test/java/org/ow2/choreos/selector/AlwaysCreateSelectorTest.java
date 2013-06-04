package org.ow2.choreos.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ow2.choreos.selectors.AlwaysCreateSelector;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.Selector;

public class AlwaysCreateSelectorTest {

	@Test
	public void shouldAlwaysCreateDifferentObjects() throws NotSelectedException {
		
		String requirements = "requirements";
		StringFactory fac = new StringFactory();
		List<String> objects = new ArrayList<String>();
		objects.add("a");
		objects.add("b");
		objects.add("c");
		
		Selector<String, String> selector = new AlwaysCreateSelector<String, String>(fac);
		List<String> selected1 = selector.select(objects, requirements, 2);
		List<String> selected2 = selector.select(objects, requirements, 2);
		
		assertEquals(2, selected1.size());
		assertEquals(2, selected2.size());
		assertTrue(selected1.get(0) != selected1.get(1));
		assertTrue(selected2.get(0) != selected2.get(1));
		assertTrue(selected1.get(0) != selected2.get(0));
	}
	
}
