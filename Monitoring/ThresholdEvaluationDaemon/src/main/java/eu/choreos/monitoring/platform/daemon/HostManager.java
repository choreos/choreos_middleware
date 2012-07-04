package eu.choreos.monitoring.platform.daemon;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class HostManager {
	
	private GmondDataReader dataReader;
	private List<Host> hostsDown;
	private List<Host> hosts;
	
	public HostManager(GmondDataReader dataReader) throws GangliaException {
		this.dataReader = dataReader;
		this.hosts = new ArrayList<Host>();
		this.hostsDown = new ArrayList<Host>();
		getDataReaderHostInfo();
	}

	public void getDataReaderHostInfo() throws GangliaException {
		hosts.clear(); hostsDown.clear();
		hosts = dataReader.getUpToDateHostsInfo();
		
		for(Host host: hosts) {
			if (host.isDown()) {
				hostsDown.add(host);
			}
		}
	}

	public List<Host> getHostsDown() throws GangliaException {
		//TODO: update data each time request info
		return hostsDown;
	}
	
	public List<Host> getHosts() throws GangliaException {
		return hosts;
	}

	public boolean thereAreHostsDown() throws GangliaException {
		return !hostsDown.isEmpty();
	}
}
