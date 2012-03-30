package eu.choreos.gmond;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.io.FileUtils;

import sun.security.krb5.internal.UDPClient;

import eu.choreos.gmond.reloader.GmondReloader;

public class GmondConf {
	private String gmondFile = "/etc/ganglia/gmond.conf";
	private List<String> fileLines;
	private int searchIndex;

	public List<String> getFileLines() {
		return fileLines;
	}

	public GmondConf() {
		searchIndex = 0;

	}

	public static void main(String... args) {
		// String fileName = "/etc/ganglia/gmond.conf";
		// String hostName = "";
		// String port = "";
		//
		// if (args.length != 2 && args.length != 4 && args.length != 6)
		// System.out
		// .println("USAGE: java -jar gmondconf [-h host] [-p port] [-f configFile]");
		//
		// for (int i = 0; i < args.length; i += 2) {
		// if (args[i].equals("-f"))
		// fileName = args[i + 1];
		// if (args[i].equals("-h"))
		// hostName = args[i + 1];
		// if (args[i].equals("-p"))
		// port = args[i + 1];
		// }
		//
		// GmondConf gmondConf = new GmondConf();
		// gmondConf.load(fileName);
		//
		// if (hostName.length() > 0) {
		// gmondConf.setSendChannelHost(hostName);
		// }
		//
		// if (port.length() > 0) {
		// gmondConf.setSendChannelPort(port);
		// }
		//
		// gmondConf.save();
		//
		//
		// GmondReloader reloader = new GmondReloader();
		// if (reloader.reload())
		// System.out.println("gmond restarted.");
		// else
		// System.out.println("could not restart gmond");
		//

	}

	public void load(String filePath) {
		gmondFile = filePath;
		load();
	}

	public void load() {
		try {
			fileLines = FileUtils.readLines((new File(gmondFile)));
		} catch (IOException e) {
			System.out.println("ERROR: Could not find desired file: "
					+ gmondFile);
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

		List<Integer> udpSendChannelLines = getUdpSendChannelHost(host);
		for (Integer index : udpSendChannelLines){
			fileLines.remove(fileLines.get(index));
		}
	}

	public void updateUdpSendChannel(String newHost, String currentHost,
			int newPort) {

		List<Integer> udpSendChannelLines = getUdpSendChannelHost(currentHost);
		for (Integer index : udpSendChannelLines){
			String line = fileLines.get(index);
			line = setAttributeIfApplicable("host", newHost, line);
			line = setAttributeIfApplicable("port", newPort+ "", line);
			fileLines.set(index, line);
		}
	}

	private String setAttributeIfApplicable(String attr, String newValue, String line) {
		if(line.contains(attr) && line.contains("=")){
			String[] parts = line.split("=");
			line = parts[0] + "= " + newValue;
		}
		return line;
	}

	public List<Integer> getUdpSendChannelHost(String host) {
		List<Integer> udpSendChannel = new ArrayList<Integer>();

		while ((udpSendChannel = findNextUdpSendChannel(fileLines)) != null) {
			for (int index : udpSendChannel) {
				String line = fileLines.get(index);
				if (line.contains("host") && line.contains("=")
						&& line.contains(host))
					return udpSendChannel;
			}
		}
		return null;
	}

	public void updateUdpSendChannelHost(String newHost, String currentHost) {

		searchIndex = 0;
		List<Integer> indexes = getUdpSendChannelHost(currentHost);

		for (int index : indexes) {
			if (fileLines.get(index).contains("host")
					&& fileLines.get(index).contains(currentHost)) {
				fileLines.set(index, "  host = " + newHost);
			}
		}

	}

	public void updateUdpSendChannelPort(String host, int port) {
		updateUdpSendChannel(host, host, port);
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

	public List<Integer> findNextUdpSendChannel(List<String> fileContents) {
		List<Integer> lineIndexes = new ArrayList<Integer>();
		int initialLine = searchIndex;
		for (int i = initialLine; i < fileContents.size(); i++) {
			String line = fileContents.get(i);

			if (line.contains("udp_send_channel") && line.contains("{")) {

				do {
					lineIndexes.add(i);
					searchIndex = i;
				} while (!fileContents.get(i++).contains("}"));

				return lineIndexes;
			}
		}
		return null;
	}

}
