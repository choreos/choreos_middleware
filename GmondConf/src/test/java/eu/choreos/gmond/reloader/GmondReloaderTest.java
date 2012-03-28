package eu.choreos.gmond.reloader;

import java.io.IOException;

import org.junit.Before;
import static org.mockito.Mockito.*;
import org.junit.Test;

import eu.choreos.gmond.reloader.GmondReloader;
import static org.junit.Assert.*;
public class GmondReloaderTest {
	
	private GmondReloader gmondReloader;

	@Before
	public void setUp() {
		gmondReloader = new GmondReloader();
	}

	@Test
	public void testReloadTrue() throws Exception {
		Runtime runtime = mock(Runtime.class);
		Process proc = mock(Process.class);
		when(proc.waitFor()).thenReturn(0);
		when(runtime.exec("/etc/init.d/ganglia-monitor restart")).thenReturn(proc);
		
		gmondReloader.setRuntime(runtime);
		assertTrue(gmondReloader.reload());
	}

}
