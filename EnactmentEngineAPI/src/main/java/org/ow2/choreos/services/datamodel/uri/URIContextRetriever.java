package org.ow2.choreos.services.datamodel.uri;

import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public interface URIContextRetriever {
    
    public String retrieveURI(DeployableServiceSpec serviceSpec, String instanceID);

}
