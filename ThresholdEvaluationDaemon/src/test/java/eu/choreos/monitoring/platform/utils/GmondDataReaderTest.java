package eu.choreos.monitoring.platform.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import eu.choreos.monitoring.platform.daemon.datatypes.Gmetric;
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
		gmondReader.update();

		assertEquals(1, gmondReader.getHosts().size());
		assertEquals("0.00", gmondReader.getMetricValue("load_one"));
	}
	
	@Test
	public void testParseGangliaCurrentMetricsWithManyHosts() throws Exception {
		when(socket.getInputStream()).thenReturn(
				getClass().getResourceAsStream("/ganglia_opencirrus.xml"));
		gmondReader.update();

		assertEquals(25, gmondReader.getHosts().size());
	}

}
