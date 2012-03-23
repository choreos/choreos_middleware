package eu.choreos.gmond;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.io.FileUtils;

public class GmondConf {
	private String gmondFile = "/etc/ganglia/gmond.conf";
	private List<String> fileLines;

	public List<String> getFileLines() {
		return fileLines;
	}

	public void setFileLines(List<String> fileLines) {
		this.fileLines = fileLines;
	}

	public GmondConf() {

	}
	
	public void load(String filePath) throws IOException {
		gmondFile = filePath;
		load();
	}

	public void load() throws IOException {
		// gmondString = FileUtils.readFileToString((new File(gmondFile)));
		fileLines = FileUtils.readLines((new File(gmondFile)));
	}

	public void setSendChannelHost(String host) {
		ArrayList<String> result = new ArrayList<String>();
		for (String line : fileLines) {
			line = changeAttribute("host = ", host, line);
			result.add(line);
		}

		fileLines = result;
	}

	public void setSendChannelPort(String port) {
		ArrayList<String> result = new ArrayList<String>();
		boolean insideUdpSendChannelSection = false;

		for (String line : fileLines) {

			if (line.contains("udp_send_channel") && line.contains("{"))
				insideUdpSendChannelSection = true;

			if (insideUdpSendChannelSection && line.contains("}"))
				insideUdpSendChannelSection = false;

			if (insideUdpSendChannelSection)
				line = changeAttribute("port = ", port, line);

			result.add(line);
		}

		fileLines = result;
	}

	public String changeAttribute(String attribute, String desiredValue,
			String searchEnvironment) {
		
		if (searchEnvironment.contains(attribute)) {
			int replacementPosition = searchEnvironment.indexOf(attribute)
					+ attribute.length();
			searchEnvironment = searchEnvironment.substring(0, replacementPosition) + desiredValue;
		}
		return searchEnvironment;
		
	}

	public void save() {
		try {
			FileUtils.writeLines((new File(gmondFile)), fileLines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
