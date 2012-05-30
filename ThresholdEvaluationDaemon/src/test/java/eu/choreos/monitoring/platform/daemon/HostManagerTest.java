package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class HostManagerTest {

	private HostManager hostManager;
	private GmondDataReader dataReader;

	@Before
	public void setUp() throws GangliaException {
		dataReader = mock(GmondDataReader.class);
		hostManager = new HostManager(dataReader);
	}
	
	@Test
	public void shouldNotFindHostsDown() {
	}

}
