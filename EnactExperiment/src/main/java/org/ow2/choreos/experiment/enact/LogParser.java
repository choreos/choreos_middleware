package org.ow2.choreos.experiment.enact;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class LogParser {

	private static final String LOG_FILE_PATH = "../ServiceDeployer/service_deployer.log"; 
	
	private static final String TIME_PATTERN = "\\d\\d:\\d\\d:\\d\\d";
	
	private static final Pattern NODE_CREATED = Pattern
			.compile("\\[DEBUG " + TIME_PATTERN + " AWSCloudProvider\\] Node \\[.+\\] created in \\d+ miliseconds");

	private static final Pattern SSH_ACCESSED = Pattern
			.compile("\\[DEBUG " + TIME_PATTERN + " ConfigurationManager\\] Connected to Node \\[.+\\]");

	public static List<String> getAdditionalInfo() throws IOException {
		
		File logFile = new File(LOG_FILE_PATH);
		List<String> sourceLines = FileUtils.readLines(logFile);
		return getAdditionalInfo(sourceLines);
	}

	public static List<String> getAdditionalInfo(List<String> source) throws IOException {
		
		List<String> lines = new ArrayList<String>();
		for (String line: source) {
			Matcher matcher =  NODE_CREATED.matcher(line);
			if (matcher.matches()) {
				lines.add(line);
			}
			matcher =  SSH_ACCESSED.matcher(line);
			if (matcher.matches()) {
				lines.add(line);
			}
		}
		
		return lines;
	}
}
