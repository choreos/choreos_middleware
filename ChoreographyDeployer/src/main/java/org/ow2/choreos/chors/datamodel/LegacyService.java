package org.ow2.choreos.chors.datamodel;

import java.util.List;

import org.ow2.choreos.deployment.services.datamodel.Service;

/* When dealing with legacy services, there is no real Service object; these
 * only exist when they are created by the DeploymentManager. But we want to
 * handle both cases with a homogeneous interface on the ChoreographyDeployer,
 * so this class is a "mock" class to encapsulate the data we need to represent
 * legacy services. 
 */
public class LegacyService extends Service {
	List<String> URIs;

	LegacyService(LegacyServiceSpec serviceSpec) {
		super(serviceSpec);
		URIs = serviceSpec.getNativeURIs();
	}

	@Override
	public List<String> getUris() {
		return URIs;
	}
}
