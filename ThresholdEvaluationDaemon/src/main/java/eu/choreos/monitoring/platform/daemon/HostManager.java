package eu.choreos.monitoring.platform.daemon;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class HostManager {
	
	private GmondDataReader dataReader;
	private List<Host> hostsDown;

	public HostManager(GmondDataReader dataReader) {
		this.dataReader = dataReader;
	}
	
	public List<Host> getHostsDown() {
		return hostsDown;
	}
	
	public boolean thereAreHostsDown() {
		hostsDown = new ArrayList<Host>();
		for(Host host: dataReader.getHosts())
			if (host.isMetricsEmpty())
				hostsDown.add(host);
		return !hostsDown.isEmpty();
	}

}
