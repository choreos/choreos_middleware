package eu.choreos.enactment.spec;

import java.util.Properties;

import eu.choreos.enactment.SpecRetriever;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class POWSSpecBuilder implements SpecBuilder {

	private static final String SERVICE_TYPE = "JAR";
	
	@Override
	public ServiceSpec getSpec(String serviceName, Properties properties) {

		ServiceSpec spec = new ServiceSpec();
		spec.setType(SERVICE_TYPE);
		spec.setRole(serviceName);
		
		String endpointName = serviceName.toLowerCase();
		spec.setEndpointName(endpointName);

		String key = serviceName + ".codeUri";
		String codeUri = properties.getProperty(key); 
		SpecRetriever.checkNull(key, codeUri);
		spec.setCodeUri(codeUri);
		
		key = serviceName + ".port";
		String port = properties.getProperty(key);
		SpecRetriever.checkNull(key, port);
		spec.setPort(port);
		
		return spec;
	}
}
