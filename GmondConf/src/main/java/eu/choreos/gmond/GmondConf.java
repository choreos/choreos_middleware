package eu.choreos.gmond;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.io.FileUtils;

import eu.choreos.gmond.reloader.GmondReloader;

public class GmondConf {
	private String gmondFile = "/etc/ganglia/gmond.conf";
	private List<String> fileLines;

	public List<String> getFileLines() {
		return fileLines;
	}

	public GmondConf() {

	}

	public static void main(String... args) {
//		String fileName = "/etc/ganglia/gmond.conf";
//		String hostName = "";
//		String port = "";
//
//		if (args.length != 2 && args.length != 4 && args.length != 6)
//			System.out
//					.println("USAGE: java -jar gmondconf [-h host] [-p port] [-f configFile]");
//
//		for (int i = 0; i < args.length; i += 2) {
//			if (args[i].equals("-f"))
//				fileName = args[i + 1];
//			if (args[i].equals("-h"))
//				hostName = args[i + 1];
//			if (args[i].equals("-p"))
//				port = args[i + 1];
//		}
//
//		GmondConf gmondConf = new GmondConf();
//		gmondConf.load(fileName);
//
//		if (hostName.length() > 0) {
//			gmondConf.setSendChannelHost(hostName);
//		}
//
//		if (port.length() > 0) {
//			gmondConf.setSendChannelPort(port);
//		}
//
//		gmondConf.save();
//		
//		
//		GmondReloader reloader = new GmondReloader();
//		if (reloader.reload())
//			System.out.println("gmond restarted.");
//		else
//			System.out.println("could not restart gmond");
//		

	}

	public void load(String filePath) {
		gmondFile = filePath;
		load();
	}
	

	public void load() {
		// gmondString = FileUtils.readFileToString((new File(gmondFile)));
		try {
			fileLines = FileUtils.readLines((new File(gmondFile)));
		} catch (IOException e) {
			System.out.println("ERROR: Could not find desired file: "+ gmondFile);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void addUdpSendChannel(String host, String port) {
		fileLines.add("");
		fileLines.add("udp_send_channel {");
		fileLines.add("\thost = " + host);
		fileLines.add("\tport = " + port);
		fileLines.add("}");
	}
	
	public void removeUdpSendChannel(String host) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> udpSendChannelBlock = new ArrayList<String>();
		boolean insideUdpSendChannelSection = false;
		boolean udpSendChannelFound = false;

		for (String line : fileLines) {

			if (line.contains("udp_send_channel") && line.contains("{")) {
				insideUdpSendChannelSection = true;
				udpSendChannelFound = false;
			}

			if (insideUdpSendChannelSection && line.contains("}")) {
				insideUdpSendChannelSection = false;
				if (!udpSendChannelFound) {
					result.addAll(udpSendChannelBlock);
				}
			}

			if (insideUdpSendChannelSection && line.contains("host ="))
				if (line.contains(host))
					udpSendChannelFound = true;	
			
			if (!insideUdpSendChannelSection)
				result.add(line);
			else
				udpSendChannelBlock.add(line);
		}

		fileLines = result;
	}

	public void updateUdpSendChannelHost(String newHost, String currentHost) {
		ArrayList<String> result = new ArrayList<String>();
		for (String line : fileLines) {
			if (line.contains("host = " + currentHost))
				line = changeAttribute("host = ", newHost, line);
			result.add(line);
		}

		fileLines = result;
	}

	public void updateUdpSendChannelPort(String host, String port) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> udpSendChannelBlock = new ArrayList<String>();
		boolean insideUdpSendChannelSection = false;
		boolean udpSendChannelFound = false;

		for (String line : fileLines) {

			if (line.contains("udp_send_channel") && line.contains("{")) {
				insideUdpSendChannelSection = true;
				udpSendChannelFound = false;
			}

			if (insideUdpSendChannelSection && line.contains("}")) {
				insideUdpSendChannelSection = false;
				if (!udpSendChannelFound) {
					result.addAll(udpSendChannelBlock);
				} else {
					result.addAll(changePort(udpSendChannelBlock, port));
				}
			}

			if (insideUdpSendChannelSection && line.contains("host ="))
				if (line.contains(host))
					udpSendChannelFound = true;	
			
			if (!insideUdpSendChannelSection)
				result.add(line);
			else
				udpSendChannelBlock.add(line);
		}

		fileLines = result;
	}

	private List<String> changePort(List<String> block, String port) {
		ArrayList<String> result = new ArrayList<String>();
		for (String line : block) {
			if (line.contains("port = "))
				line = changeAttribute("port = ", port, line);
			result.add(line);
		}

		return result;
	}

	public String changeAttribute(String attribute, String desiredValue,
			String searchEnvironment) {

		if (searchEnvironment.contains(attribute)) {
			int replacementPosition = searchEnvironment.indexOf(attribute)
					+ attribute.length();
			searchEnvironment = searchEnvironment.substring(0,
					replacementPosition) + desiredValue;
		}
		return searchEnvironment;

	}

	public void save() {
		try {
			FileUtils.writeLines((new File(gmondFile)), fileLines);
		} catch (IOException e) {
			System.out.println("ERROR: Could not write to file " + gmondFile);
			e.printStackTrace();
			System.exit(1);
		}
	}

}
