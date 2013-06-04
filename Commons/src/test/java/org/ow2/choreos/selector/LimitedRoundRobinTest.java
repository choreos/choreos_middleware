package org.ow2.choreos.selector;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.selectors.LimitedRoundRobinSelector;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.Selector;
import org.ow2.choreos.utils.Concurrency;

public class LimitedRoundRobinTest {

	private StringRetriever retriever;
	
	@Before
	public void setup() {

		this.retriever = new StringRetriever();
		this.retriever.addObject("0");
		this.retriever.addObject("1");
		this.retriever.addObject("2");
	}
	
	@Test
	public void shouldCreateSomeObjectsAndAfterRoundRobin() throws NotSelectedException {

		final int LIMIT = 3;
		StringFactory fac = new StringFactory();
		Selector<String, String> selector = 
				new LimitedRoundRobinSelector<String, String>(LIMIT, retriever, fac);
		String requirements = "requirements";
		int quantity = 1;
		
		final String[] expectedSequence = new String[]{"0", "1", "2", "0", "1", "2", "0"};
		
		for (int i=0; i<expectedSequence.length; i++) {
			String obj = selector.select(requirements, quantity).get(0);
			assertEquals(expectedSequence[i], obj);
		}
	}
	
	@Test
	public void shouldCreateSomeObjectsAndAfterRoundRobinWithQuantityEquals2() throws NotSelectedException  {

		final int LIMIT = 3;
		StringFactory fac = new StringFactory();
		Selector<String, String> selector = 
				new LimitedRoundRobinSelector<String, String>(LIMIT, retriever, fac);
		String requirements = "requirements";
		int quantity = 2;
		
		final String[][] expectedSequence = new String[][]{new String[]{"0", "1"}, new String[]{"2", "0"}};
		
		for (int i=0; i<expectedSequence.length; i++) {
			
			List<String> selected = selector.select(requirements, quantity);
			String obj0 = selected.get(0);
			String obj1 = selected.get(1);
			assertEquals(expectedSequence[i][0], obj0);
			assertEquals(expectedSequence[i][1], obj1);
		}
	}
	
	@Test
	public void shouldCreateSomeObjectsAndAfterRoundRobinConcurrently() throws NotSelectedException, InterruptedException, ExecutionException {

		final int LIMIT = 3;
		StringFactory fac = new StringFactory();
		Selector<String, String> limtiedRRSelector = 
				new LimitedRoundRobinSelector<String, String>(LIMIT, retriever, fac);
		int quantity = 1;

		final int N = 6;
		ExecutorService executor = Executors.newFixedThreadPool(N);
		List<CallableSelector> selectors = new ArrayList<CallableSelector>();
		List<Future<String>> futures = new ArrayList<Future<String>>();
		for (int i=0; i<N; i++) {
			CallableSelector selector = new CallableSelector(limtiedRRSelector, quantity);
			selectors.add(selector);
			Future<String> f = executor.submit(selector);
			futures.add(f);
		}
		
		Concurrency.waitExecutor(executor, 1);
		
		int count = 0, count0 = 0, count1 = 0, count2 = 0;
		for (Future<String> f: futures) {
			String object = f.get();
			count++;
			if (object.equals("0"))
				count0++;
			if (object.equals("1"))
				count1++;
			if (object.equals("2"))
				count2++;
		}
		
		assertEquals(6, count);
		assertEquals(2, count0);
		assertEquals(2, count1);
		assertEquals(2, count2);
	}
	
	private class CallableSelector implements Callable<String> {

		Selector<String, String> selector;
		int quantity;
		
		CallableSelector(Selector<String, String> selector, int quantity) {
			this.selector = selector;
			this.quantity = quantity;
		}
		
		@Override
		public String call() throws Exception {
			List<String> nodes = selector.select("requirements", quantity);
			return nodes.get(0);
		}
	}
}
