package eu.choreos.enactment;

import java.util.Properties;

import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public interface SpecBuilder {

	ServiceSpec getSpec(String serviceName, Properties properties);
}
