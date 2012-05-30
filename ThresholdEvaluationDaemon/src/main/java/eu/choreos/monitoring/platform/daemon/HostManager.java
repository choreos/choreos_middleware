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
	
	private void updateHostsInfo() throws GangliaException {
		hostsDown.clear(); 
		registeredHosts.clear();
		registeredHosts = dataReader.getHosts();
		
		for(Host host: registeredHosts) {
			if (host.isMetricsEmpty()) {
				host.setIsDown(true);
				hostsDown.add(host);
			}
		}
		
	}

	public HostManager(GmondDataReader dataReader) throws GangliaException {
		this.dataReader = dataReader;
		registeredHosts = new ArrayList<Host>();
		hostsDown = new ArrayList<Host>();
		updateHostsInfo();
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
	
	public static void main(String[] args) {
		
		try {
			HostManager m = new HostManager(new GmondDataReader("eclipse.ime.usp.br", 8649));
			System.out.println(m.getHostsDown());
		} catch (GangliaException e1) {
			e1.printStackTrace();
		}
	}
}
