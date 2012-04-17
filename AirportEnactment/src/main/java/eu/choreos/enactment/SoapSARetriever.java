package eu.choreos.enactment;

import java.io.IOException;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;

import eu.choreos.enactment.EndpointResolver.SAType;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class SoapSARetriever implements ServicesRetriever {

	private static final String SERVICE_TYPE = "PETALS";
	private static final String PROPERTIES_FILE = "services.properties";
	private Properties properties;
	
	@Override
	public Set<ServiceSpec> retrieve() {

		properties = new Properties();
		try {
			properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
		} catch (IOException e) {
			System.out.println("Cannot load " + PROPERTIES_FILE);
			e.printStackTrace();
		}
		
		Set<ServiceSpec> specs = new HashSet<ServiceSpec>();
		
		for (String name: Info.SERVICES_NAMES) {

			ServiceSpec specProvide = getProvide(name);
			ServiceSpec specConsume = getConsume(name);
			specs.add(specProvide);
			specs.add(specConsume);
		}
		
		return specs;
	}
	
	private ServiceSpec getProvide(String serviceName) {
		
		ServiceSpec spec = new ServiceSpec();
		spec.setType(SERVICE_TYPE);
		
		String endpointName = EndpointResolver.getEndpointName(serviceName,
				SAType.SOAP_PROVIDE);
		spec.setEndpointName(endpointName);

		String key = serviceName + ".dsb.provide";
		String codeUri = properties.getProperty(key); 
		checkNull(key, codeUri);
		spec.setCodeUri(codeUri);
		
		return spec;
	}
	
	private ServiceSpec getConsume(String serviceName) {

		ServiceSpec spec = new ServiceSpec();
		spec.setType(SERVICE_TYPE);
		
		String endpointName = EndpointResolver.getEndpointName(serviceName,
				SAType.SOAP_CONSUME);
		spec.setEndpointName(endpointName);
		
		String key = serviceName + ".dsb.consume";
		String codeUri = properties.getProperty(key); 
		checkNull(key, codeUri);
		spec.setCodeUri(codeUri);
		
		return spec;
	}

	private void checkNull(String key, String value) {
		if (value == null) {
			throw new MissingResourceException("Missing property " + key + " in "
					+ PROPERTIES_FILE, Properties.class.toString(), key);
		}
	}
	
}
