package org.ow2.choreos.deployment.services.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.ow2.choreos.deployment.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.deployment.services.recipe.Recipe;


public class DeployedService extends Service {

	/**
	 * The list of all instances of the service
	 */
	private List<ServiceInstance> serviceInstances;

	@XmlTransient
	private Recipe recipe;

	public DeployedService(DeployedServiceSpec spec) {
		super(spec);
	}
	
	@Override
	public DeployedServiceSpec getSpec() {
		return (DeployedServiceSpec) super.getSpec();
	}

	public List<ServiceInstance> getInstances() {
		return serviceInstances;
	}

	public void setInstances(List<ServiceInstance> instances) {
		for (ServiceInstance ins: instances) {
			addInstance(ins);
		}
	}

	public void addInstance(ServiceInstance instance) {
		if (serviceInstances == null) {
			serviceInstances = new ArrayList<ServiceInstance>();
		}
		serviceInstances.add(instance);
		instance.setService(this);
	}

	@Override
	public List<String> getUris() {
		List<String> uris = new ArrayList<String>();
		for (ServiceInstance service : serviceInstances) {
			uris.add(service.getNativeUri());
		}
		return uris;
	}

	public ServiceInstance getInstance(String instanceId) throws ServiceInstanceNotFoundException{
		for(ServiceInstance instance: serviceInstances) {
			System.out.println("Searching for " + instanceId + " found "+ instance.getInstanceId());
			if(instance.getInstanceId().equals(instanceId))
				return instance;
		}
		throw new ServiceInstanceNotFoundException(getSpec().getUUID(), instanceId);
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
}
