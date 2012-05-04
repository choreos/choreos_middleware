package eu.choreos.gmond;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.gmond.reloader.GmondReloader;

public class GmondConfTest {

	private GmondConf gmondConf;
	private File exampleFile;
	private File testFile;
	private File testMultipleFile;
	private File exampleMultipleFile;

	@Before
	public void setUp() throws IOException {
		gmondConf = new GmondConf();
		exampleFile = new File(ClassLoader.getSystemResource(
				"gmond.conf.template").getFile());
		testFile = new File("gmond.conf");

		FileUtils.copyFile(exampleFile, testFile);

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

	@Test
	public void shouldAddUdpSendChannel() throws IOException {
		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("host = 127.0.0.1"));

		gmondConf.load(testFile.getAbsolutePath());
		gmondConf.addUdpSendChannel("127.0.0.1", "8649");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue(fileContents.contains("host = 127.0.0.1"));
	}

	@Test
	public void shouldRemoveUdpSendChannel() throws IOException {
		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));

		gmondConf.load(testFile.getAbsolutePath());
		gmondConf.removeUdpSendChannel("eclipse.ime.usp.br");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse(fileContents.contains("host = eclipse.ime.usp.br"));
	}

	@Test
	public void shouldChangeChannelHost() throws IOException {

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse(fileContents.contains("host = localhost"));

		int location = fileContents.indexOf("host = eclipse.ime.usp.br");

		gmondConf.load(testFile.getAbsolutePath());
		gmondConf.updateUdpSendChannelHost("eclipse.ime.usp.br", "localhost");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue("Altered line not located!",
				fileContents.contains("host = localhost"));

		assertEquals(location, fileContents.indexOf("host = localhost"));
	}

	@Test
	public void shouldChangeChannelPort() throws IOException {

		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("port = 1234"));

		int location = fileContents.indexOf("port = 8649");

		gmondConf.load(testFile.getAbsolutePath());
		gmondConf.updateUdpSendChannelPort("eclipse.ime.usp.br", "1234");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue("Altered line not located!",
				fileContents.contains("port = 1234"));

		assertEquals(location, fileContents.indexOf("port = 1234"));
	}

	@Test
	public void shouldFindOneUdpSendChannel() throws IOException {

		List<String> fileContents = FileUtils.readLines(testFile);
		List<Integer> lineIndexes = gmondConf
				.findNextUdpSendChannel(fileContents);

		List<Integer> expectedLines = new ArrayList<Integer>();

		for (int i = 33; i <= 36; i++)
			expectedLines.add(i);

		assertEquals(expectedLines, lineIndexes);
	}

	@Test
	public void shouldFindTwoUdpSendChannel() throws IOException {

		List<String> fileContents = FileUtils.readLines(testMultipleFile);
		List<Integer> lineIndexes = gmondConf
				.findNextUdpSendChannel(fileContents);

		List<Integer> expectedLines = new ArrayList<Integer>();

		for (int i = 33; i <= 36; i++)
			expectedLines.add(i);

		assertEquals(expectedLines, lineIndexes);

		lineIndexes.clear();
		expectedLines.clear();
		lineIndexes = gmondConf.findNextUdpSendChannel(fileContents);

		for (int i = 38; i <= 41; i++)
			expectedLines.add(i);

		assertEquals(expectedLines, lineIndexes);
	}

	@Test
	public void shouldChangeSecondChannelHost() throws IOException {
		List<String> fileContents = FileUtils.readLines(testMultipleFile);
		assertTrue(fileContents.contains("  host = eclipse.ime.usp.br"));
		assertTrue(fileContents.contains("  host = 127.0.0.1"));
		assertFalse(fileContents.contains("  host = localhost"));

		int location = fileContents.indexOf("host = eclipse.ime.usp.br");

		gmondConf.load(testMultipleFile.getAbsolutePath());
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
	public void shouldGetCorrectUdpSendChannel() {
		gmondConf.load(testMultipleFile.getAbsolutePath());

		List<Integer> indexes = gmondConf.getUdpSendChannelHost("127.0.0.1");

		List<Integer> expected = new ArrayList<Integer>();
		for (int i = 38; i <= 41; i++) {
			expected.add(i);
		}

		assertEquals(expected, indexes);
	}


	// Warning suppressed to ensure usage of mock reloader
	@SuppressWarnings("static-access")
	@Test
	public void shouldChangeHostFromMain() throws Exception {
		// [--update-host currentHost newHost]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);

		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse(fileContents.contains("host = localhost"));

		int hostLocation = fileContents.indexOf("host = eclipse.ime.usp.br");

		gmondConf.main("--update-host", "eclipse.ime.usp.br", "localhost",
				"--config-file", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue("Altered line not located!",
				fileContents.contains("host = localhost"));

		assertEquals(hostLocation, fileContents.indexOf("host = localhost"));

	}

	@SuppressWarnings("static-access")
	@Test
	public void shouldChangePortFromMain() throws Exception {
		// [--update-port host port]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("port = 1234"));
		int portLocation = fileContents.indexOf("port = 8649");

		gmondConf.main("--update-port", "eclipse.ime.usp.br", "1234",
				"--config-file", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue("Altered line not located!",
				fileContents.contains("port = 1234"));

		assertEquals(portLocation, fileContents.indexOf("port = 1234"));
	}

	// Warning suppressed to ensure usage of mock reloader
	@SuppressWarnings("static-access")
	@Test
	public void shouldRemoveChannelFromMain() throws Exception {
		// [--remove host]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));

		gmondConf.main("--remove", "eclipse.ime.usp.br", "--config-file",
				testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));

	}

	// Warning suppressed to ensure usage of mock reloader
	@SuppressWarnings("static-access")
	@Test
	public void shouldUpdateHostAndPortChannelFromMain() throws Exception {
		// [--update-channel currentHost newHost newPort]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue(fileContents.contains("port = 8649"));
		assertFalse(fileContents.contains("host = localhost"));
		assertFalse(fileContents.contains("port = 1234"));

		GmondConf.main("--update-channel", "eclipse.ime.usp.br", "localhost",
				"1234", "--config-file", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		System.out.println(fileContents);
		
		assertFalse("Original host was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse("Original port was found!",
				fileContents.contains("port = 8649"));
		assertTrue("New host was not found!",
				fileContents.contains("host = localhost"));
		assertTrue("New port was not found!",
				fileContents.contains("port = 1234"));

	}



	// Warning suppressed to ensure usage of mock reloader
	@SuppressWarnings("static-access")
	@Test
	public void shouldAddChannelFromMain() throws Exception {
		// [--add host port]
		// [--config-file configFile]

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue(fileContents.contains("port = 8649"));
		assertFalse(fileContents.contains("host = localhost"));
		assertFalse(fileContents.contains("port = 1234"));

		GmondConf.main("--add", "localhost", "1234", "--config-file",
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
