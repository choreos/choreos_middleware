package eu.choreos.enactment;

import java.io.IOException;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;

import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class SpecRetriever {

	private static final String PROPERTIES_FILE = "services.properties";
	
	private SpecBuilder[] specBuilders;
	private Properties properties;
	
	public SpecRetriever(SpecBuilder[] specBuilders) {
		
		this.specBuilders = specBuilders;
	}
	
	public Set<ServiceSpec> retrieve() {

		properties = new Properties();
		try {
			properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
		} catch (IOException e) {
			System.out.println("Cannot load " + PROPERTIES_FILE);
			e.printStackTrace();
		}
		
		Set<ServiceSpec> specs = new HashSet<ServiceSpec>();
		
		for (String serviceName: Info.SERVICES_NAMES) {

			for (SpecBuilder specBuilder: specBuilders) {
				ServiceSpec spec = specBuilder.getSpec(serviceName, properties);
				specs.add(spec);
			}
		}
		
		return specs;
	}
	
	public static void checkNull(String key, String value) {
		if (value == null) {
			throw new MissingResourceException("Missing property " + key + " in "
					+ PROPERTIES_FILE, Properties.class.toString(), key);
		}
	}
}
