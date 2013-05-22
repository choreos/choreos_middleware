package org.ow2.choreos.enactment;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.experiment.enact.LogParser;

public class LogParserTest {

	private List<String> LINES = new ArrayList<String>();

	@Before
	public void setUp() {
		LINES.add("[DEBUG 11:08:47 ConfigurationManager] Connected to Node [id=us-east-1/i-4553de3e, ip=50.17.106.155, hostname=null, chefName=null]");
		LINES.add("Unrelated line");
		LINES.add("[DEBUG 11:08:19 AWSCloudProvider] Node [id=us-east-1/i-3353de48, ip=107.21.168.80, hostname=null, chefName=null] created in 57716 miliseconds");
	}
	
	@Test
	public void test() throws IOException {
		
		List<String> parsedLines = LogParser.getAdditionalInfo(LINES);
		
		assertEquals(2, parsedLines.size());
		assertEquals(LINES.get(0), parsedLines.get(0));
		assertEquals(LINES.get(2), parsedLines.get(1));
	}

}
