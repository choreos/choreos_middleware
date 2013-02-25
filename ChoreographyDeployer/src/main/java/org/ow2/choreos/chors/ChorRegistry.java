package org.ow2.choreos.chors;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.Service;


/**
 * Stores choreography descriptions.
 * 
 * TODO: In the future, we must use a database instead a static map.
 * 
 * @author leonardo
 *
 */
public class ChorRegistry {
	
	private static ChorRegistry instance = new ChorRegistry();;

	// map key is the chor id
	private Map<String, Choreography> chors = new ConcurrentHashMap<String, Choreography>(); 
	private AtomicInteger counter = new AtomicInteger();
	
	private ChorRegistry() {
		
	}
	
	public static ChorRegistry getInstance() {
		
		return instance;
	}
	
	/**
	 * Creates a new choreography entry
	 * @return the just registred choreography ID
	 */
	public String create(ChorSpec chorSpec) {
		
		String id = Integer.toString(counter.incrementAndGet());
		
		Choreography chor = new Choreography();
		chor.setId(id);
		chor.setRequestedChorSpec(chorSpec);
		chor.setCurrentChorSpec(null);
		chors.put(id, chor);
		
		return id;
	}
	
	public Choreography get(String chorId) {
		
		return chors.get(chorId);
	}
	
	/**
	 * Associates deployed services with a choreography
	 * @param deployed
	 * @param chorId
	 */
	public void addDeployedServices(String chorId, List<Service> deployedServices) {
		
		Choreography chor = chors.get(chorId);
		if (chor == null)
			throw new IllegalArgumentException("chorId " + chorId + " does not exist");
		chor.setDeployedServices(deployedServices);
	}

}
