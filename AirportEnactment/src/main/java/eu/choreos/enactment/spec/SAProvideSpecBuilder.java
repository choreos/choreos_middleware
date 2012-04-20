package eu.choreos.enactment.spec;

import java.util.Properties;

import eu.choreos.enactment.spec.EndpointResolver.SAType;
import eu.choreos.enactment.SpecRetriever;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class SAProvideSpecBuilder implements SpecBuilder {

	private static final String SERVICE_TYPE = "PETALS";
	
	@Override
	public ServiceSpec getSpec(String serviceName, Properties properties) {
		
		ServiceSpec spec = new ServiceSpec();
		spec.setType(SERVICE_TYPE);
		spec.setRole(serviceName);
		
		String endpointName = EndpointResolver.getEndpointName(serviceName,
				SAType.SOAP_PROVIDE);
		spec.setEndpointName(endpointName);

		String key = serviceName + ".dsb.provide";
		String codeUri = properties.getProperty(key); 
		SpecRetriever.checkNull(key, codeUri);
		spec.setCodeUri(codeUri);
		
		return spec;
	}
}
