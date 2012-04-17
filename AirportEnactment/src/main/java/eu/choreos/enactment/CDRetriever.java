package eu.choreos.enactment;

import java.io.IOException;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;

import eu.choreos.enactment.EndpointResolver.SAType;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class CDRetriever implements ServicesRetriever {

	private static final String SERVICE_TYPE = "PETALS";
	private static final String PROPERTIES_FILE = "services.properties";

	@Override
	public Set<ServiceSpec> retrieve() {

		Properties properties = new Properties();
		try {
			properties.load(ClassLoader
					.getSystemResourceAsStream(PROPERTIES_FILE));
		} catch (IOException e) {
			System.out.println("Cannot load " + PROPERTIES_FILE);
			e.printStackTrace();
		}

		Set<ServiceSpec> specs = new HashSet<ServiceSpec>();

		for (String name : Info.SERVICES_NAMES) {

			ServiceSpec spec = new ServiceSpec();
			spec.setType(SERVICE_TYPE);

			String endpointName = EndpointResolver.getEndpointName(name,
					SAType.CD_PROVIDE);
			spec.setEndpointName(endpointName);

			String key = name + ".dsb.cd";
			String codeUri = properties.getProperty(key);
			checkNull(key, codeUri);
			spec.setCodeUri(codeUri);

			specs.add(spec);
		}

		return specs;
	}

	private void checkNull(String key, String value) {
		if (value == null) {
			throw new MissingResourceException("Missing property " + key
					+ " in " + PROPERTIES_FILE, Properties.class.toString(),
					key);
		}
	}

}
