package org.ow2.choreos.enactment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.Choreography;

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
	 * @return the just created choreography
	 */
	public Choreography newChor() {
		
		String id = Integer.toString(counter.incrementAndGet());
		Choreography chor = new Choreography();
		chor.setId(id);
		chors.put(id, chor);
		
		return chor;
	}
	
	/**
	 * Associates a service to a choreography
	 * @param service the service specification
	 * @param chorId the choreography id
	 */
	public void addService(ChorService service, String chorId) {
		
		Choreography chor = chors.get(chorId);
		if (chorId == null)
			throw new IllegalArgumentException();
		chor.getServices().add(service);
	}
	
	public Choreography get(String chorId) {
		
		return chors.get(chorId);
	}

}
