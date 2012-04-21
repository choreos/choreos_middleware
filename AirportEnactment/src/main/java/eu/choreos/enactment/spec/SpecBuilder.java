package eu.choreos.enactment.spec;

import java.util.Properties;

import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public interface SpecBuilder {

	ServiceSpec getSpec(String serviceName, Properties properties);
}
