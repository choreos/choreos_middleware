package eu.choreos.enactment;

import java.util.HashSet;
import java.util.Set;

import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class SimplePOWSRetriever {

	public Set<ServiceSpec> retrieve() {

		ServiceSpec spec = new ServiceSpec();
		spec.setType("JAR");
		spec.setCodeUri("https://github.com/downloads/choreos/choreos_middleware/simplews.jar");
		spec.setEndpointName("");
		spec.setPort("8042");
		
		Set<ServiceSpec> specs = new HashSet<ServiceSpec>();
		specs.add(spec);
		return specs;
	}

}
