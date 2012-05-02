package eu.choreos.monitoring.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HostnameHandlerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldReturnPathToScript() throws IOException {
		assertEquals("/bin/bash /tmp/hostname.sh",
				HostnameHandler.getScriptCommand());
	}

	@Test
	public void shouldCreateFileInTmpFolder() {
		HostnameHandler.getScriptCommand();
		File tmpFile = new File("/tmp/hostname.sh");
		assertTrue("Could not find created file!", tmpFile.exists());
	}

	@Test
	public void createdFileShouldContainOriginalContents() throws IOException {
		HostnameHandler.getScriptCommand();
		File tmpFile = new File("/tmp/hostname.sh");
		String fileContents = FileUtils.readFileToString(tmpFile);
		assertTrue("Could not find created file!", 
				fileContents.contains("IP_ADDRESS_REGEX='([12]?[0-9]?[0-9]\\.){3}[12]?[0-9]?[0-9]"));
	}

}
