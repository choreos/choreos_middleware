package eu.choreos.gmond;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GmondConfTest {

	private GmondConf gmondConf;
	private File exampleFile;
	private File testFile;

	@Before
	public void setUp() throws IOException {
		gmondConf = new GmondConf();
		exampleFile = new File(ClassLoader.getSystemResource(
				"gmond.conf.template").getFile());
		testFile = new File("gmond.conf");

		FileUtils.copyFile(exampleFile, testFile);
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
		gmondConf.updateUdpSendChannelHost("localhost", "eclipse.ime.usp.br");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue("Altered line not located!",
				fileContents.contains("host = localhost"));

		assertEquals(location, fileContents.indexOf("host = localhost"));
	}
	
	@Test
	public void shouldChangeLinesWithHostAttribute(){
		String result = gmondConf.changeAttribute("host = ", "1234", "host = 0987");
		assertEquals("host = 1234", result);
	}
	
	@Test
	public void shouldNotChangeLinesWithoutHostAttribute(){
		String result = gmondConf.changeAttribute("host = ", "1234", "lorem ipsum");
		assertEquals("lorem ipsum", result);
	}

	@Test
	public void shouldChangeChannelPort() throws IOException {
		
		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("port = 1234"));

		int location = fileContents.indexOf("port = 8649");

		gmondConf.load(testFile.getAbsolutePath());
		gmondConf.updateUdpSendChannelPort("eclipse.ime.usp.br","1234");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		assertTrue("Altered line not located!",
				fileContents.contains("port = 1234"));

		assertEquals(location, fileContents.indexOf("port = 1234"));
	}
	
	//@Test
	public void shouldChangeHostFromMain() throws IOException {
		String fileContents = FileUtils.readFileToString(testFile);
		
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse(fileContents.contains("host = localhost"));

		int hostLocation = fileContents.indexOf("host = eclipse.ime.usp.br");

		GmondConf.main("-h", "localhost", "-f", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue("Altered line not located!",
				fileContents.contains("host = localhost"));

		assertEquals(hostLocation, fileContents.indexOf("host = localhost"));

		
	}
	
	//@Test
	public void shouldChangePortFromMain() throws IOException {
		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("port = 1234"));
		int portLocation = fileContents.indexOf("port = 8649");

		int hostLocation = fileContents.indexOf("host = eclipse.ime.usp.br");

		GmondConf.main( "-p", "1234", "-f", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);


		assertTrue("Altered line not located!",
				fileContents.contains("port = 1234"));

		assertEquals(portLocation, fileContents.indexOf("port = 1234"));
		
	}
	
	//@Test
	public void should() throws IOException {
		String fileContents = FileUtils.readFileToString(testFile);
		assertFalse(fileContents.contains("port = 1234"));
		
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse(fileContents.contains("host = localhost"));


		GmondConf.main("-h", "localhost", "-p", "1234", "-f", testFile.getAbsolutePath());

		fileContents = FileUtils.readFileToString(testFile);

		assertFalse("Original line was found!",
				fileContents.contains("host = eclipse.ime.usp.br"));
		assertTrue("Altered line not located!",
				fileContents.contains("host = localhost"));

		assertTrue("Altered line not located!",
				fileContents.contains("port = 1234"));

		
	}

}
