package eu.choreos.gmond.reloader;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;

import org.junit.Before;
import org.junit.Test;
public class GmondReloaderTest {
	
	private GmondReloader gmondReloader;
	Runtime runtime;
	Process proc;

	@Before
	public void setUp() throws Exception {
		gmondReloader = new GmondReloader();
		runtime = mock(Runtime.class);
		proc = mock(Process.class);
		when(proc.waitFor()).thenReturn(0);
		when(proc.exitValue()).thenReturn(0);
	}

	@Test
	public void testRestartTrue() throws Exception {
		when(proc.getInputStream()).thenReturn(new StringBufferInputStream(""));
		when(runtime.exec("/etc/init.d/ganglia-monitor restart")).thenReturn(proc);
		when(runtime.exec("ps -ef | grep gmond")).thenReturn(proc);
		
		gmondReloader.setRuntime(runtime);
		assertTrue(gmondReloader.reload());
		verify(runtime, times(1)).exec("/etc/init.d/ganglia-monitor restart");
		verify(proc, times(2)).waitFor();
				
	}

	@Test
	public void testReloadTrue() throws Exception {
		when(proc.getInputStream()).thenReturn(new StringBufferInputStream("/usr/sbin/gmond"));
		when(runtime.exec("kill -1 $( cat /var/run/gmond.pid )")).thenReturn(proc);
		when(runtime.exec("ps -ef | grep gmond")).thenReturn(proc);
		
		gmondReloader.setRuntime(runtime);
		assertTrue(gmondReloader.reload());
		verify(runtime, times(1)).exec("kill -1 $( cat /var/run/gmond.pid )");
		verify(proc, times(2)).waitFor();
		
	}
}
