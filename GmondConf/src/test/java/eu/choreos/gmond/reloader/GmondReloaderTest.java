package eu.choreos.gmond.reloader;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.gmond.reloader.GmondReloader;
import static org.junit.Assert.*;
public class GmondReloaderTest {
	
	private GmondReloader gmondReloader;

	@Before
	public void setUp() {
		gmondReloader = new GmondReloader(true);
	}

	@Test
	public void testReloadTrue() {
		assertTrue(gmondReloader.reload());
	}

}
