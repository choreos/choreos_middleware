/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.ResourceImpact;

@XmlRootElement
public class DeploymentRequest {

    private DeployableService service;
    private ResourceImpact resourceImpact;
    private int numberOfInstances = 1;
    private String recipeName;
    private String deploymentManagerURL;

    public String getRecipeName() {
	return recipeName;
    }

    public void setRecipeName(String recipeName) {
	this.recipeName = recipeName;
    }

    public DeploymentRequest() {

    }

    public DeploymentRequest(String recipeName, ResourceImpact resourceImpact, int numberOfInstances) {
	this.numberOfInstances = numberOfInstances;
	this.resourceImpact = resourceImpact;
	this.recipeName = recipeName;
    }

    public DeploymentRequest(DeployableService service) {
	this.service = service;
	this.resourceImpact = service.getSpec().getResourceImpact();
	this.numberOfInstances = service.getSpec().getNumberOfInstances();
    }

    public DeploymentRequest(String recipeName) {
	this.recipeName = recipeName;
    }

    public DeployableService getService() {
	return this.service;
    }

    public ResourceImpact getResourceImpact() {
	return resourceImpact;
    }

    public void setResourceImpact(ResourceImpact resourceImpact) {
	this.resourceImpact = resourceImpact;
    }

    public int getNumberOfInstances() {
	return numberOfInstances;
    }

    public void setNumberOfInstances(int numberOfInstances) {
	this.numberOfInstances = numberOfInstances;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((recipeName == null) ? 0 : recipeName.hashCode());
	result = prime * result + numberOfInstances;
	result = prime * result + ((resourceImpact == null) ? 0 : resourceImpact.hashCode());
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
	if (recipeName == null) {
	    if (other.recipeName != null)
		return false;
	} else if (!recipeName.equals(other.recipeName))
	    return false;
	if (numberOfInstances != other.numberOfInstances)
	    return false;
	if (resourceImpact == null) {
	    if (other.resourceImpact != null)
		return false;
	} else if (!resourceImpact.equals(other.resourceImpact))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "DeploymentRequest [resourceImpact=" + resourceImpact + ", numberOfInstances=" + numberOfInstances + "]";
    }

    public String getDeploymentManagerURL() {
	return this.deploymentManagerURL;
    }

    public void setDeploymentManagerURL(String deploymentManagerURL) {
	this.deploymentManagerURL = deploymentManagerURL;
    }

}
