package eu.choreos.gmond.reloader;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.platform.utils.ShellHandler;

public class GmondReloaderTest {
	
	private GmondReloader gmondReloader;
	private ShellHandler runtime;

	@Before
	public void setUp() throws Exception {
		gmondReloader = new GmondReloader();
		runtime = mock(ShellHandler.class);
		
	}

	//@Test
	public void testRestartTrue() throws Exception {
		when(runtime.runLocalCommand("/etc/init.d/ganglia-monitor restart")).thenReturn("");
		when(runtime.runLocalCommand("ps -ef | grep gmond")).thenReturn("anything but what it should");
		
		gmondReloader.setRuntime(runtime);
		assertTrue(gmondReloader.reload());
		verify(runtime, times(1)).runLocalCommand("/etc/init.d/ganglia-monitor restart");
	}

	//@Test
	public void testReloadTrue() throws Exception {
		when(runtime.runLocalCommand("kill -1 $( cat /var/run/gmond.pid )")).thenReturn("");
		when(runtime.runLocalCommand("ps -ef | grep gmond")).thenReturn("/usr/sbin/gmond");
		
		gmondReloader.setRuntime(runtime);
		assertTrue(gmondReloader.reload());
		verify(runtime, times(1)).runLocalCommand("kill -1 $( cat /var/run/gmond.pid )");
	}
}
