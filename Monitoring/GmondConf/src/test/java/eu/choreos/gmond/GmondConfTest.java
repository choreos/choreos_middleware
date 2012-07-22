package eu.choreos.gmond;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.gmond.launcher.GmondConfConsole;
import eu.choreos.gmond.reloader.GmondReloader;

//Warning suppressed to ensure usage of mock reloader
@SuppressWarnings("static-access")
public class GmondConfTest {

	private GmondConf gmondConf;
	private File exampleFile;
	private File testFile;
	private File testMultipleFile;
	private File exampleMultipleFile;
	private GmondConfConsole gmondConfLauncher;

	@Before
	public void setUp() throws IOException {
		exampleFile = new File(ClassLoader.getSystemResource(
				"gmond.conf.template").getFile());
		testFile = new File("gmond.conf");

		FileUtils.copyFile(exampleFile, testFile);

		gmondConf = new GmondConf(testFile.getAbsolutePath());

		exampleMultipleFile = new File(ClassLoader.getSystemResource(
				"gmondmultipleudp.conf.template").getFile());
		testMultipleFile = new File("gmondMultiple.conf");

		FileUtils.copyFile(exampleMultipleFile, testMultipleFile);
		GmondReloader mockedReloader = mock(GmondReloader.class);
		when(mockedReloader.reload()).thenReturn(true);
		gmondConf.setReloader(mockedReloader);
	}

	@After
	public void tearDown() {
		FileUtils.deleteQuietly(testFile);
	}

	@Test(expected = Exception.class)
	public void shouldPrintUsage() throws Exception {
		GmondConfConsole.main("anything");
	}

	@Test
	public void shouldAddUdpSendChannel() throws IOException {
		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("host = 127.0.0.1"));

		gmondConf.addUdpSendChannel("127.0.0.1", "8649");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue("Should have added an udp send channel to the config file",
				fileContents.contains("host = 127.0.0.1"));
	}

	@Test
	public void shouldRemoveUdpSendChannel() throws IOException {
		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));

		
		gmondConf.removeUdpSendChannel("eclipse.ime.usp.br");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse(
				"Should have removed eclipse.ime.usp.br udp send channel from the config file",
				fileContents.contains("host = eclipse.ime.usp.br"));
	}

	@Test
	public void shouldChangeUdpSendChannelHost() throws IOException {

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse(fileContents.contains("host = localhost"));

		
		gmondConf.updateUdpSendChannelHost("eclipse.ime.usp.br", "localhost");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue("Altered line not located!",
				fileContents.contains("host = localhost"));

		System.out.println(fileContents);
	}

	@Test
	public void shouldUpdateUdpSendChannelPort() throws IOException {

		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("port = 1234"));

		int location = fileContents.indexOf("port = 8649");

		
		gmondConf.updateUdpSendChannelPort("eclipse.ime.usp.br", "1234");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue("Altered line not located!",
				fileContents.contains("port = 1234"));

		assertEquals(location, fileContents.indexOf("port = 1234"));
	}

	@Test
	public void shouldUpdateUdpSendChannelHostAndPort() throws IOException {

		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("port = 1234"));

		int location = fileContents.indexOf("port = 8649");

		
		gmondConf.updateUdpSendChannel("eclipse.ime.usp.br", "localhost",
				"1234");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue("Altered port not located!",
				fileContents.contains("port = 1234"));
		assertFalse("Previous port not removed!",
				fileContents.contains("port = 8649"));
		assertTrue("Altered host not located!",
				fileContents.contains("host = localhost"));
		assertFalse("Previous host not removed!",
				fileContents.contains("host = eclipse.ime.usp.br"));

		assertEquals(location, fileContents.indexOf("port = 1234"));
	}

	@Test
	public void shouldChangeSecondChannelHost() throws IOException {
		List<String> fileContents = FileUtils.readLines(testMultipleFile);
		assertTrue(fileContents.contains("  host = eclipse.ime.usp.br"));
		assertTrue(fileContents.contains("  host = 127.0.0.1"));
		assertFalse(fileContents.contains("  host = localhost"));

		int location = fileContents.indexOf("host = eclipse.ime.usp.br");

		gmondConf.setConfigFile(testMultipleFile.getAbsolutePath());
		gmondConf.updateUdpSendChannelHost("eclipse.ime.usp.br", "localhost");
		gmondConf.save();

		fileContents = FileUtils.readLines(testMultipleFile);

		assertFalse("Original line was found!",
				fileContents.contains("  host = eclipse.ime.usp.br"));
		assertTrue("Altered line not located!",
				fileContents.contains("  host = localhost"));
		assertTrue("Original line was found!",
				fileContents.contains("  host = 127.0.0.1"));

		assertEquals(location, fileContents.indexOf("host = localhost"));
	}

	@Test
	public void shouldChangeHostFromMain() throws Exception {
		// [--update-host currentHost newHost]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);

		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse(fileContents.contains("host = localhost"));

		gmondConfLauncher.main("--update-host", "eclipse.ime.usp.br",
				"localhost", "--config-file", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue("Altered line not located!",
				fileContents.contains("host = localhost"));
	}

	@Test
	public void shouldChangePortFromMain() throws Exception {
		// [--update-port host port]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("port = 1234"));
		int portLocation = fileContents.indexOf("port = 8649");

		gmondConfLauncher.main("--update-port", "eclipse.ime.usp.br", "1234",
				"--config-file", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue("Altered line not located!",
				fileContents.contains("port = 1234"));

		assertEquals(portLocation, fileContents.indexOf("port = 1234"));
	}

	@Test
	public void shouldRemoveChannelFromMain() throws Exception {
		// [--remove host]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));

		gmondConfLauncher.main("--remove", "eclipse.ime.usp.br",
				"--config-file", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));

	}

	@Test
	public void shouldUpdateHostAndPortChannelFromMain() throws Exception {
		// [--update-channel currentHost newHost newPort]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue(fileContents.contains("port = 8649"));
		assertFalse(fileContents.contains("host = localhost"));
		assertFalse(fileContents.contains("port = 1234"));

		gmondConfLauncher.main("--update-channel", "eclipse.ime.usp.br",
				"localhost", "1234", "--config-file",
				testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original host was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse("Original port was found!",
				fileContents.contains("port = 8649"));
		assertTrue("New host was not found!",
				fileContents.contains("host = localhost"));
		assertTrue("New port was not found!",
				fileContents.contains("port = 1234"));

	}

	@Test
	public void shouldAddChannelFromMain() throws Exception {
		// [--add host port]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue(fileContents.contains("port = 8649"));
		assertFalse(fileContents.contains("host = localhost"));
		assertFalse(fileContents.contains("port = 1234"));

		gmondConfLauncher.main("--add", "localhost", "1234", "--config-file",
				testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue("Original host was not found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue("Original port was not found!",
				fileContents.contains("port = 8649"));
		assertTrue("New host was not found!",
				fileContents.contains("host = localhost"));
		assertTrue("New port was not found!",
				fileContents.contains("port = 1234"));
	}

}
