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
	public void shouldReplaceHostLineInAString() {
		ArrayList<String> testString = new ArrayList<String>();

		testString
				.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. ");
		testString
				.add("Vivamus at ante magna, eu volutpat purus. Nullam pretium ullamcorper ");
		testString
				.add("elit adipiscing porttitor. Pellentesque enim ipsum, posuere eu euismod ");
		testString.add("host = eclipse");
		testString
				.add("id, accumsan sed augue. Aliquam at libero eu elit tristique vulputate. ");
		testString
				.add("Maecenas viverra, nisl vel tempus cursus, urna nisl gravida dolor, ");
		testString
				.add("nec molestie neque tellus eget nisi. Lorem ipsum dolor sit amet, ");
		testString
				.add("consectetur adipiscing elit. Morbi id mauris sed lectus mollis ");
		testString.add("vehicula a vitae diam. ");

		gmondConf.setFileLines(testString);
		gmondConf.setSendChannelHost("campinas");
		List<String> resultLines = gmondConf.getFileLines();

		assertTrue("New content not found!",
				resultLines.get(3).contains("campinas"));
		assertFalse("Old content still present",
				resultLines.get(3).contains("eclipse"));

	}

	@Test
	public void shouldChangeChannelHost() throws IOException {

		String fileContents = FileUtils.readFileToString(testFile);
		assertTrue(fileContents.contains("host = eclipse.ime.usp.br"));
		assertFalse(fileContents.contains("host = localhost"));

		int location = fileContents.indexOf("host = eclipse.ime.usp.br");

		gmondConf.load(testFile.getAbsolutePath());
		gmondConf.setSendChannelHost("localhost");
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
		gmondConf.setSendChannelPort("1234");
		gmondConf.save();

		fileContents = FileUtils.readFileToString(testFile);

		System.out.println(fileContents);
		assertTrue("Altered line not located!",
				fileContents.contains("port = 1234"));

		assertEquals(location, fileContents.indexOf("port = 1234"));
	}

}
