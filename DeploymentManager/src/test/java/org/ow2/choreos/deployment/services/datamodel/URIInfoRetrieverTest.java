package org.ow2.choreos.deployment.services.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.ow2.choreos.services.datamodel.URIInfoRetriever;

public class URIInfoRetrieverTest {

	@Test
	public void shouldRetrieveHostname() {

		final String HOST = "choreos-node";
		URIInfoRetriever info = new URIInfoRetriever("http://" + HOST + "/airline");
		String hostname = info.getHostname();
		String ip = info.getIp();
		
		assertEquals(HOST, hostname);
		assertNull(ip);
	}
	
	@Test
	public void shouldRetrieveHostnameWithPort() {

		final String HOST = "choreos-node";
		URIInfoRetriever info = new URIInfoRetriever("http://" + HOST + ":1234/airline");
		String hostname = info.getHostname();
		String ip = info.getIp();
		
		assertEquals(HOST, hostname);
		assertNull(ip);
	}
	
	@Test
	public void shouldRetrieveHostnameWithoutHttp() {

		final String HOST = "choreos-node";
		URIInfoRetriever info = new URIInfoRetriever(HOST + ":1234/airline");
		String hostname = info.getHostname();
		String ip = info.getIp();
		
		assertEquals(HOST, hostname);
		assertNull(ip);
	}
	
	@Test
	public void shouldRetrieveHostnameWithoutHttpAndWithoutPort() {

		final String HOST = "choreos-node";
		URIInfoRetriever info = new URIInfoRetriever(HOST + "/airline");
		String hostname = info.getHostname();
		String ip = info.getIp();
		
		assertEquals(HOST, hostname);
		assertNull(ip);
	}
	
	@Test
	public void shouldRetrieveIp() {

		final String IP = "192.168.56.102";
		URIInfoRetriever info = new URIInfoRetriever("http://" + IP + ":1234/airline");
		String hostname = info.getHostname();
		String ip = info.getIp();
		
		assertEquals(IP, ip);
		assertNull(hostname);
	}

}
