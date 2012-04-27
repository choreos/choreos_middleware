package eu.choreos.gmond;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

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

	public static void main(String... args) throws Exception {
		String fileName = "/etc/ganglia/gmond.conf";

		validateArgs(args);

		fileName = getConfigFile(args);

		GmondConf gmondConf = new GmondConf();

		gmondConf.load(fileName);

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--add")) {
				gmondConf.addUdpSendChannel(args[i + 1], args[i + 2]);
				i += 2;
			}

			if (args[i].equals("--update-port")) {
				gmondConf.updateUdpSendChannelPort(args[i + 1],
						Integer.parseInt(args[i + 2]));
				i += 2;
			}

			if (args[i].equals("--update-channel")) {
				gmondConf.updateUdpSendChannel(args[i], args[i + 1],
						Integer.parseInt(args[i + 2]));
				i += 2;
			}

			if (args[i].equals("--update-host")) {
				gmondConf.updateUdpSendChannelHost(args[i + 1], args[i + 2]);
				i += 2;
			}

			if (args[i].equals("--remove")) {
				gmondConf.removeUdpSendChannel(args[i + 1]);
				i += 2;
			}

		}

		gmondConf.save();

		GmondReloader reloader = new GmondReloader();
		if (reloader.reload())
			System.out.println("gmond restarted.");
		else
			System.out.println("could not restart gmond. Are you root? Do you have the necessary privileges?");

	}

	private static String getConfigFile(String[] arguments) {
		String fileName = "/etc/ganglia/gmond.conf";

		for (int i = 0; i < arguments.length; i++) {

			if (arguments[i].equals("--config-file")) {
				fileName = arguments[i + 1];
			}
		}
		return fileName;
	}

	private static void validateArgs(String[] args) throws Exception {
		String string;

		for (int index = 0; index < args.length; index++) {
			string = args[index];

			if (string.equals("--add"))
				index = index + 2;
			else if (string.equals("--update-port"))
				index = index + 2;
			else if (string.equals("--update-channel"))
				index = index + 3;
			else if (string.equals("--update-host"))
				index = index + 2;
			else if (string.equals("--remove"))
				index = index + 1;
			else if (string.equals("--config-file"))
				index = index + 1;
			else {
				printUsage();
				System.exit(1);
			}
		}
	}

	private static void printUsage() throws Exception {
		System.out.println("" + "USAGE: java -jar gmondconf "
				+ "[--add host port] " + "[--update-port host port] "
				+ "[--update-host currentHost newHost] "
				+ "[--update-channel currentHost newHost newPort]"
				+ "[--remove host]" + "[--config-file configFile]");
		throw (new Exception());
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
		for (Integer index : udpSendChannelLines) {
			fileLines.remove(fileLines.get(index));
		}
	}

	public void updateUdpSendChannel(String newHost, String currentHost,
			int newPort) {

		List<Integer> udpSendChannelLines = getUdpSendChannelHost(currentHost);
		for (Integer index : udpSendChannelLines) {
			String line = fileLines.get(index);
			line = setAttributeIfApplicable("host", newHost, line);
			line = setAttributeIfApplicable("port", newPort + "", line);
			fileLines.set(index, line);
		}
	}

	private String setAttributeIfApplicable(String attr, String newValue,
			String line) {
		if (line.contains(attr) && line.contains("=")) {
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

	public void updateUdpSendChannelHost(String currentHost, String newHost) {

		searchIndex = 0;
		List<Integer> indexes = getUdpSendChannelHost(currentHost);

		if (indexes != null) {
			for (int index : indexes) {
				if (fileLines.get(index).contains("host")
						&& fileLines.get(index).contains(currentHost)) {
					fileLines.set(index, "  host = " + newHost);
				}
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
