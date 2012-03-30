package eu.choreos.monitoring;

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

public class GmondDataReaderTest {

	private GmondDataReader gmondReader;

	@Before
	public void setUp() throws IOException {
		gmondReader = new GmondDataReader("http://localhost/", 8649);
		Socket socket = mock(Socket.class);

		when(socket.getInputStream()).thenReturn(
				getClass().getResourceAsStream("/campinas.xml"));

		gmondReader.setSocket(socket);

	}

	@Test
	public void testGetGangliaCurrentMetrics() {
		InputStream in = getClass().getResourceAsStream("/campinas.xml");
		Document dom = gmondReader.convertToDomDocument(in);

		dom.getDocumentElement().normalize();
		NodeList clusterNodeList = dom.getElementsByTagName("CLUSTER");

		assertEquals("LCPD",
				((Element) clusterNodeList.item(0)).getAttribute("NAME"));
	}

	@Test
	public void testGetMetricByName() throws IOException {
		Gmetric result = gmondReader.getAllMetrics().get("mem_total");
		assertEquals("4118892", result.getValue());
	}

	@Test
	public void testGetAllMetrics() {
		Map<String, Gmetric> result = gmondReader.getAllMetrics();
		
		String memTotal = result.get("mem_total").getValue();
		assertEquals("4118892", memTotal);
		
		String load = result.get("load_five").getValue();
		assertEquals("0.04", load);
		
		String swap = result.get("swap_free").getValue();
		assertEquals("1951740", swap);
		
		
	}
}
