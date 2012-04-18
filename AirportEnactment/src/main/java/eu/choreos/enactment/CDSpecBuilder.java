package eu.choreos.enactment;

import java.util.Properties;

import eu.choreos.enactment.EndpointResolver.SAType;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class CDSpecBuilder implements SpecBuilder {

	private static final String SERVICE_TYPE = "PETALS";

	@Override
	public ServiceSpec getSpec(String serviceName, Properties properties) {

		ServiceSpec spec = new ServiceSpec();
		spec.setType(SERVICE_TYPE);

		String endpointName = EndpointResolver.getEndpointName(serviceName,
				SAType.CD_PROVIDE);
		spec.setEndpointName(endpointName);

		String key = serviceName + ".dsb.cd";
		String codeUri = properties.getProperty(key);
		SpecRetriever.checkNull(key, codeUri);
		spec.setCodeUri(codeUri);

		return spec;
	}

}
