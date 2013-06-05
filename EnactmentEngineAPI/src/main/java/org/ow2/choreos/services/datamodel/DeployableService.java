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
	checkServiceInstances();
	return serviceInstances;
    }

    public void setServiceInstances(List<ServiceInstance> instances) {
	this.serviceInstances = instances;
    }

    public void addInstance(ServiceInstance instance) {
	checkServiceInstances();
	synchronized (serviceInstances) {
	    serviceInstances.add(instance);
	}
	instance.setServiceSpec(this.getSpec());
    }

    @Override
    public List<String> getUris() {

	checkServiceInstances();
	List<String> uris = new ArrayList<String>();

	synchronized (serviceInstances) {
	    for (ServiceInstance service : serviceInstances) {
		uris.add(service.getNativeUri());
	    }
	}
	return uris;
    }

    public ServiceInstance getInstance(String instanceId) {
	checkServiceInstances();
	synchronized (serviceInstances) {
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

    private void checkServiceInstances() {

	if (serviceInstances == null) {
	    synchronized (this) {
		if (serviceInstances == null) {
		    serviceInstances = new ArrayList<ServiceInstance>();
		}
	    }
	}
    }
}
