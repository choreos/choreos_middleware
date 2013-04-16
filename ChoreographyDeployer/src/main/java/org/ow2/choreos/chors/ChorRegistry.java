package org.ow2.choreos.chors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;


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
	public String create(ChoreographySpec chorSpec) {
		
		String id = Integer.toString(counter.incrementAndGet());
		
		Choreography chor = new Choreography();
		chor.setId(id);
		chor.setChoreographySpec(chorSpec);
		chors.put(id, chor);
		
		return id;
	}
	
	public Choreography get(String chorId) {
		
		return chors.get(chorId);
	}
}
