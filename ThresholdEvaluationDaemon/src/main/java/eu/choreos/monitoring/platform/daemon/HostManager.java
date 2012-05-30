package eu.choreos.monitoring.platform.daemon;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class HostManager {
	
	private GmondDataReader dataReader;
	private List<Host> hostsDown;
	private List<Host> registeredHosts;
	
	public HostManager(GmondDataReader dataReader) throws GangliaException {
		this.dataReader = dataReader;
		registeredHosts = new ArrayList<Host>();
		hostsDown = new ArrayList<Host>();
		updateHostsInfo();
	}

	public void updateHostsInfo() throws GangliaException {
		hostsDown.clear(); 
		registeredHosts.clear();
		
		registeredHosts = dataReader.getUpToDateHostsInfo();
		updateHostsDown();		
	}

	private void updateHostsDown() {
		for(Host host: registeredHosts) {
			if (host.isDown()) {
				hostsDown.add(host);
			}
		}
	}
	
	public List<Host> getHostsDown() {
		return hostsDown;
	}
	
	public List<Host> getRegisteredHosts() {
		return registeredHosts;
	}

	public boolean thereAreHostsDown() throws GangliaException {
		return !hostsDown.isEmpty();
	}
}
