package eu.choreos.gmond;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.io.FileUtils;

import eu.choreos.gmond.beans.Channel;
import eu.choreos.gmond.reloader.GmondReloader;

@WebService
public class GmondConf implements IGmondConf {
	private static GmondReloader reloader = null;
	private List<String> fileLines;
	private int searchIndex;
	private String gmondFile;
	
	
	public GmondConf(String configFile) {
		setConfigFile(configFile);
	}
	
	public GmondConf() {
		this("/etc/ganglia/gmond.conf");
	}

	/* (non-Javadoc)
	 * @see eu.choreos.gmond.IGmondConfWeb#setConfigFile(java.lang.String)
	 */
	@Override
	public void setConfigFile(String fileName) {
		gmondFile = fileName;
		load();
		searchIndex = 0;
	}
	
	/* (non-Javadoc)
	 * @see eu.choreos.gmond.IGmondConfWeb#getConfigFile()
	 */
	@Override
	public String getConfigFile() {
		return gmondFile;
	}
	
	public void setReloader(GmondReloader newReloader) {
		reloader = newReloader;
	}

	private void load() {
		try {
			fileLines = FileUtils.readLines((new File(gmondFile)));
		} catch (IOException e) {
			System.out.println("ERROR: Could not find desired file: "
					+ gmondFile);
			e.printStackTrace();
			System.exit(1);
		}
	}

	/* (non-Javadoc)
	 * @see eu.choreos.gmond.IGmondConfWeb#save()
	 */
	@Override
	public void save() {
		try {
			FileUtils.writeLines((new File(gmondFile)), fileLines);
			reloadConfigurations();
		} catch (IOException e) {
			System.out.println("ERROR: Could not write to file " + gmondFile);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void rollBack() {
		load();
	}

	/* (non-Javadoc)
	 * @see eu.choreos.gmond.IGmondConfWeb#addUdpSendChannel(java.lang.String, java.lang.String)
	 */
	@Override
	public void addUdpSendChannel(String host, String port) {
		fileLines.add("");
		fileLines.add("udp_send_channel {");
		fileLines.add("\thost = " + host);
		fileLines.add("\tport = " + port);
		fileLines.add("}");
	}

	/* (non-Javadoc)
	 * @see eu.choreos.gmond.IGmondConfWeb#updateUdpSendChannel(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateUdpSendChannel(String currentHost, String newHost,
			String newPort) {

		List<Integer> udpSendChannelLines = getUdpSendChannelLineIndexes(currentHost);
		for (Integer index : udpSendChannelLines) {
			String line = fileLines.get(index);
			line = setAttributeIfApplicable("host", newHost, line);
			line = setAttributeIfApplicable("port", newPort, line);
			fileLines.set(index, line);
		}
	}

	/* (non-Javadoc)
	 * @see eu.choreos.gmond.IGmondConfWeb#updateUdpSendChannelHost(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateUdpSendChannelHost(String currentHost, String newHost) {
		String currentPort = getUdpSendChannelPort(currentHost);
		updateUdpSendChannel(currentHost, newHost, currentPort);
	}

	/* (non-Javadoc)
	 * @see eu.choreos.gmond.IGmondConfWeb#updateUdpSendChannelPort(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateUdpSendChannelPort(String host, String port) {
		updateUdpSendChannel(host, host, port);
	}

	private String setAttributeIfApplicable(String attr, String newValue,
			String line) {
		if (line.contains(attr) && line.contains("=")) {
			String[] parts = line.split("=");
			line = parts[0] + "= " + newValue;
		}
		return line;
	}

	/* (non-Javadoc)
	 * @see eu.choreos.gmond.IGmondConfWeb#removeUdpSendChannel(java.lang.String)
	 */
	@Override
	public void removeUdpSendChannel(String host) {
	
		List<Integer> udpSendChannelLines = getUdpSendChannelLineIndexes(host);
		for (Integer index : udpSendChannelLines) {
			fileLines.remove(fileLines.get(index));
		}
	}

	private String getUdpSendChannelPort(String currentHost) {
		List<Integer> sendChannelLineIndexes = getUdpSendChannelLineIndexes(currentHost);
		String currentPort = null;
	
		for (int lineIndex : sendChannelLineIndexes) {
			String line = fileLines.get(lineIndex);
			if (line.contains("port") && line.contains("="))
				currentPort = line.split("=")[1];
		}
		return currentPort;
	}

	private List<Integer> getUdpSendChannelLineIndexes(String host) {
		List<Integer> udpSendChannel;
	
		searchIndex = 0;
		
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
	
	private void reloadConfigurations() {

		if (reloader == null)
			reloader = new GmondReloader();

		if (reloader.reload())
			System.out.println("Gmond restarted.");
		else
			System.out
					.println("Could not restart gmond. Are you root? Do you have the necessary privileges?");
	}

	private List<Integer> findNextUdpSendChannel(List<String> fileContents) {
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

	@Override
	public Channel[] listUdpSendChannel() {
		List<Integer> udpSendChannel;
		List<Channel> channels = new ArrayList<Channel>();
		
		searchIndex = 0;
		
		while ((udpSendChannel = findNextUdpSendChannel(fileLines)) != null) {
			channels.add(createChannel(udpSendChannel));
		}
		return channels.toArray(new Channel[0]);
	}

	private Channel createChannel(List<Integer> indexes) {
		Channel channel = new Channel();
		for(Integer i: indexes) {
			String line = fileLines.get(i); 
			if (line.contains("host"))
				channel.setHost(line.split("=")[1]);
			if (line.contains("port"))
				channel.setHost(line.split("=")[1]);
		}
		return channel;
	}
	
	
}
