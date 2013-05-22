package eu.choreos.enactment;

import java.io.IOException;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;

import eu.choreos.enactment.spec.CDSpecBuilder;
import eu.choreos.enactment.spec.SpecBuilder;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class SpecRetriever {

	private static final String FIRST_SERVICE = "Hotel";
	private static final String PROPERTIES_FILE = "services.properties";
	
	private SpecBuilder[] specBuilders;
	private Properties properties;
	private ServiceSpec firstSpec;
	
	public SpecRetriever(SpecBuilder[] specBuilders) {
		
		properties = new Properties();
		try {
			properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
		} catch (IOException e) {
			System.out.println("Cannot load " + PROPERTIES_FILE);
			e.printStackTrace();
		}

		this.specBuilders = specBuilders;
		this.firstSpec = buildFirstSpec();
	}
	
	public Set<ServiceSpec> retrieve() {

		Set<ServiceSpec> specs = new HashSet<ServiceSpec>();
		
		for (String serviceName: Info.SERVICES_NAMES) {

			for (SpecBuilder specBuilder: specBuilders) {
				ServiceSpec spec = specBuilder.getSpec(serviceName, properties);
				if (!spec.equals(firstSpec)) {
					specs.add(spec);
				}
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
	
	private ServiceSpec buildFirstSpec() {
		
		CDSpecBuilder builder = new CDSpecBuilder();
		return builder.getSpec(FIRST_SERVICE, properties);
	}
	
	/**
	 * Returns the spec of the first SA to be deployed
	 * Its node will be the MASTER node (on Petals topology)
	 * @return
	 */
	public ServiceSpec getFirstSpec() {
		
		return firstSpec;
	}
}
