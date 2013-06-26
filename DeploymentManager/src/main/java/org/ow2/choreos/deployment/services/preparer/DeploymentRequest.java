/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.preparer;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.DeployableService;

@XmlRootElement
public class DeploymentRequest {

    private DeployableService service;
    private int numberOfInstances = 1;
    private ResourceImpact resourceImpact = new ResourceImpact();
    private String deploymentManagerURL;
    
    public DeploymentRequest(DeployableService service) {
	this.service = service;
    }

    public DeployableService getService() {
        return service;
    }

    public void setService(DeployableService service) {
        this.service = service;
    }

    public int getNumberOfInstances() {
        return numberOfInstances;
    }

    public void setNumberOfInstances(int numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
    }

    public ResourceImpact getResourceImpact() {
        return resourceImpact;
    }

    public void setResourceImpact(ResourceImpact resourceImpact) {
        this.resourceImpact = resourceImpact;
    }

    public String getDeploymentManagerURL() {
        return deploymentManagerURL;
    }

    public void setDeploymentManagerURL(String deploymentManagerURL) {
        this.deploymentManagerURL = deploymentManagerURL;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + numberOfInstances;
	result = prime * result + ((service == null) ? 0 : service.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DeploymentRequest other = (DeploymentRequest) obj;
	if (numberOfInstances != other.numberOfInstances)
	    return false;
	if (service == null) {
	    if (other.service != null)
		return false;
	} else if (!service.equals(other.service))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "DeploymentRequest [service=" + service + ", numberOfInstances=" + numberOfInstances
		+ ", resourceImpact=" + resourceImpact + "]";
    }
    
}
