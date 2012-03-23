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
		int index = 0;
		ArrayList<String> result = new ArrayList<String>();
		for (String line : fileLines) {
			if (line.contains("host = ")) {
				int replacementPosition = line.indexOf("host = ")
						+ "host = ".length();
				line = line.substring(0, replacementPosition) + host;
			}
			result.add(line);
		}
		
		fileLines = result;
	}

	public void setSendChannelPort(String string) {

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
