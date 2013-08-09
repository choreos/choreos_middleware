package org.ow2.choreos.services.datamodel.uri;

import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class WarURIContextRetriever implements URIContextRetriever {

    @Override
    public String retrieveURI(DeployableServiceSpec spec, String instanceID) {
        return instanceID + "/" + spec.getEndpointName();
    }

}
