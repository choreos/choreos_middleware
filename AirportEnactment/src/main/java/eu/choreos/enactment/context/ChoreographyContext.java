package eu.choreos.enactment.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChoreographyContext {
	
	// key = service name; value = service endpoint
	private Map<String, String> chorContext = new HashMap<String, String>();

	public void addService(String name, String endpoint) {
		
		chorContext.put(name, endpoint);
	}
	
	public Map<String, String> getContext() {
		return Collections.unmodifiableMap(chorContext);
	}
}
