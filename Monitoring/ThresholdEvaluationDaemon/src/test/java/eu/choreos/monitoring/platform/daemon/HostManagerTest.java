package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.daemon.datatypes.Metric;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class HostManagerTest {

	private HostManager hostManager;
	private GmondDataReader dataReader;
	private List<Host> hostList;

	@Before
	public void setUp() throws GangliaException {
		dataReader = mock(GmondDataReader.class);
		hostManager = new HostManager(dataReader);
		hostList = new ArrayList<Host>();
	}

	@Test
	public void shouldNotFindHostsDown() throws GangliaException {
		HashMap<String, Metric> hashMap = new HashMap<String, Metric>();
		hashMap.put("test", (new Metric("test", "0.0", 10, 30, 0)));
		hostList.add(new Host("test", "test", "ip", hashMap, 20,30));
		when(dataReader.getUpToDateHostsInfo()).thenReturn(hostList);

		hostManager.getDataReaderHostInfo();

		assertEquals(0, hostManager.getHostsDown().size());
		assertEquals(false, hostManager.thereAreHostsDown());
	}

	@Test
	public void shouldFindHostsDown() throws GangliaException {
		HashMap<String, Metric> hashMap = new HashMap<String, Metric>();
		hashMap.put("load_one", new Metric("load_one", "value", 90, 20, 10));
		hostList.add(new Host("test", "test", "ip", hashMap, 35, 20));
		when(dataReader.getUpToDateHostsInfo()).thenReturn(hostList);

		hostManager.getDataReaderHostInfo();

		assertEquals(1, hostManager.getHostsDown().size());
		assertEquals(true, hostManager.thereAreHostsDown());
	}
}
