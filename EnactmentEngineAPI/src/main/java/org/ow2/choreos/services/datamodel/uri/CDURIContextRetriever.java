package org.ow2.choreos.services.datamodel.uri;

import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class CDURIContextRetriever implements URIContextRetriever {

    @Override
    public String retrieveURI(DeployableServiceSpec spec, String instanceID) {
        return "services/" + spec.getEndpointName() + "ClientProxyEndpoint/";
    }

}
