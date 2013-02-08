package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.ow2.choreos.deployment.Configuration;

public class FixedIPsRetrieverTest {

	@Test
	public void shouldParseIps() {
		
		Configuration.set("FIXED_VM_IPS", "192.168.56.101; 192.168.56.102");
		FixedIPsRetriever retriever = new FixedIPsRetriever();
		List<String> ips = retriever.retrieveIPs();
		assertEquals(2, ips.size());
		assertEquals("192.168.56.101", ips.get(0));
		assertEquals("192.168.56.102", ips.get(1));
	}
	
	@Test
	public void shouldParseSilengIp() {
		
		Configuration.set("FIXED_VM_IPS", "192.168.56.101");
		FixedIPsRetriever retriever = new FixedIPsRetriever();
		List<String> ips = retriever.retrieveIPs();
		assertEquals(1, ips.size());
		assertEquals("192.168.56.101", ips.get(0));
	}

	@Test
	public void shouldParseSilengIpWithExtraEndingSpace() {
		
		Configuration.set("FIXED_VM_IPS", "192.168.56.101 ");
		FixedIPsRetriever retriever = new FixedIPsRetriever();
		List<String> ips = retriever.retrieveIPs();
		assertEquals(1, ips.size());
		assertEquals("192.168.56.101", ips.get(0));
	}


	@Test
	public void shouldParseIpsWithExtraEndingSpace() {
		
		Configuration.set("FIXED_VM_IPS", "192.168.56.101; 192.168.56.102 ");
		FixedIPsRetriever retriever = new FixedIPsRetriever();
		List<String> ips = retriever.retrieveIPs();
		assertEquals(2, ips.size());
		assertEquals("192.168.56.101", ips.get(0));
		assertEquals("192.168.56.102", ips.get(1));
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldThrowExceptionBecauseTheComa() {
		
		Configuration.set("FIXED_VM_IPS", "192.168.56.101, 192.168.56.102");
		FixedIPsRetriever retriever = new FixedIPsRetriever();
		retriever.retrieveIPs();
	}

}
