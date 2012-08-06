package org.ow2.choreos.enactment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

/**
 * Stores choreography descriptions.
 * 
 * TODO: In the future, we must use a database instead a static map.
 * 
 * @author leonardo
 *
 */
public class ChorRegistry {
	
	private static ChorRegistry instance;
	
	// map key is the chor id
	private Map<String, Choreography> chors = new HashMap<String, Choreography>(); 
	private AtomicInteger counter = new AtomicInteger();
	
	private ChorRegistry() {
		
	}
	
	public static ChorRegistry getInstance() {
		
		if (instance == null)
			instance = new ChorRegistry();
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
		chor.setServiceSpecs(chorSpec.getServiceSpecs());
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
