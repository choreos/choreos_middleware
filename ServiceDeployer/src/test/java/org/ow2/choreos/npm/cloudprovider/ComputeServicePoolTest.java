package org.ow2.choreos.npm.cloudprovider;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.compute.ComputeService;
import org.junit.Test;

public class ComputeServicePoolTest {

	@Test
	public void test() {
		
		List<ComputeService> list1 = new ArrayList<ComputeService>();
		List<ComputeService> list2 = new ArrayList<ComputeService>();
		
		for (int i=0; i<ComputeServicePool.MAX_COMPUTE_SERVICES; i++) {
			ComputeService cs = ComputeServicePool.getComputeService("");
			list1.add(cs);
		}

		for (int i=0; i<ComputeServicePool.MAX_COMPUTE_SERVICES; i++) {
			ComputeService cs = ComputeServicePool.getComputeService("");
			list2.add(cs);
		}
		
		assertEquals(ComputeServicePool.MAX_COMPUTE_SERVICES, list1.size());
		assertEquals(ComputeServicePool.MAX_COMPUTE_SERVICES, list2.size());
		
		for (int i=0; i<ComputeServicePool.MAX_COMPUTE_SERVICES; i++) {
			
			ComputeService cs1 = list1.get(i);
			ComputeService cs2 = list1.get(i);
			assertTrue(cs1 == cs2);
		}
	}

}
