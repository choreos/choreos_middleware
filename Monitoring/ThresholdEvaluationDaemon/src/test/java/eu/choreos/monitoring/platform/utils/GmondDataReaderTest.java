package eu.choreos.monitoring.platform.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.daemon.datatypes.Metric;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class GmondDataReaderTest {

	private GmondDataReader gmondReader;
	private Socket socket;
	
	@Before
	public void setUp() throws IOException, GangliaException {
		gmondReader = new GmondDataReader("http://localhost/", 8649);
		socket = mock(Socket.class);
		gmondReader.setSocket(socket);

	}

	@Test
	public void testParseGangliaCurrentMetricsWithOneHost() throws Exception {
		when(socket.getInputStream()).thenReturn(
				getClass().getResourceAsStream("/campinas.xml"));
		List<Host> hosts = gmondReader.getUpToDateHostsInfo();

		assertEquals(1, hosts.size());
		assertEquals("0.00", hosts.get(0).getMetricValue("load_one"));
	}
	
	@Test
	public void testParseGangliaCurrentMetricsWithManyHosts() throws Exception {
		when(socket.getInputStream()).thenReturn(
				getClass().getResourceAsStream("/ganglia_opencirrus.xml"));

		List<Host> hosts = gmondReader.getUpToDateHostsInfo();

		assertEquals(22, hosts.size());
		assertEquals("opencirrus-08039.hpl.hp.com", hosts.get(0).getHostName());
		//assertEquals("opencirrus-07901.hpl.hp.com", hosts.get(13).getHostName());
		//assertEquals("cirrus078-mgmt-n3.hpl.hp.com", hosts.get(24).getHostName());
		
	}
}
