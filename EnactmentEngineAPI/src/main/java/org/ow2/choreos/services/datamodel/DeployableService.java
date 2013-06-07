/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class DeployableService extends Service {

    /**
     * The list of all instances of the service
     */
    private List<ServiceInstance> serviceInstances;

    @XmlTransient
    private RecipeBundle recipeBundle;

    public DeployableService() {
	super();
    }

    public DeployableService(DeployableServiceSpec spec) {
	super(spec);
    }

    @Override
    public DeployableServiceSpec getSpec() {
	return (DeployableServiceSpec) super.getSpec();
    }

    /**
     * This method seems to be necessary to JAXB set the super class attribute
     * when doing unmarshalling
     */
    @Override
    public void setSpec(ServiceSpec spec) {
	super.setSpec((DeployableServiceSpec) spec);
    }

    public List<ServiceInstance> getInstances() {
	if (serviceInstances != null) 
	    return serviceInstances;
	else
	    return new ArrayList<ServiceInstance>();
    }

    public void setServiceInstances(List<ServiceInstance> instances) {
	this.serviceInstances = instances;
    }

    @Override
    public List<String> getUris() {

	if (serviceInstances == null) {
	    return new ArrayList<String>();
	}
	
	List<String> uris = new ArrayList<String>();
	synchronized (serviceInstances) {
	    for (ServiceInstance service : serviceInstances) {
		uris.add(service.getNativeUri());
	    }
	}
	return uris;
    }

    public ServiceInstance getInstance(String instanceId) {
	if (serviceInstances != null) {
	    for (ServiceInstance instance : serviceInstances) {
		if (instance.getInstanceId().equals(instanceId))
		    return instance;
	    }
	}
	throw new IllegalArgumentException("getSpec().getUUID() " + " / " + instanceId);
    }

    public RecipeBundle getRecipeBundle() {
	return recipeBundle;
    }

    public void setRecipeBundle(RecipeBundle recipeBundle) {
	this.recipeBundle = recipeBundle;
    }

}
