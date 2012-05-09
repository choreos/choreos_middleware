package eu.choreos.gmond.reloader;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
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
		verify(runtime, times(1)).exec("/etc/init.d/ganglia-monitor restart");
		verify(proc, times(1)).waitFor();
				
	}

}
