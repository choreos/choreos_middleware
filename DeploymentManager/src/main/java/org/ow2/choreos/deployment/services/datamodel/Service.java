package org.ow2.choreos.deployment.services.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.ow2.choreos.deployment.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.deployment.services.recipe.Recipe;


@XmlRootElement
public class Service {

	/**
	 * The service name
	 */
	private String name;

	/**
	 * The service specification
	 */
	private ServiceSpec spec;

	/**
	 * The list of all instances of the service
	 */
	private List<ServiceInstance> serviceInstances;

	@XmlTransient
	private Recipe recipe;

	
	
	
	/**
	 * Default constructor for Java XML Bindings
	 */
	public Service() {

	}

	public Service(ServiceSpec serviceSpec) {

		this.spec = serviceSpec;
		if (this.spec.getPackageType() == null) {
			this.spec.setPackageType(PackageType.OTHER);
		}

		if (serviceSpec.getName() == null || serviceSpec.getName().isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			name = serviceSpec.getName();
		}

		if (serviceSpec.packageType == PackageType.LEGACY) {
			setLegacyServiceInstances(serviceSpec);
		}
	}

	private void setLegacyServiceInstances(ServiceSpec serviceSpec) {
		List<String> uris = serviceSpec.getLegacyServicesUris();

		if(uris.size() > 0) {

			for(String uri: uris) {
				URIInfoRetriever info = new URIInfoRetriever(uri);

				ServiceInstance si = new ServiceInstance(null, this);

				si.setLegacyHostname(info.getHostname());
				si.setLegacyIp(info.getIp());
				si.setNativeUri(serviceSpec.getPackageUri());

				serviceInstances.add(si);
			}
		}
	}

	public List<ServiceInstance> getInstances() {
		return serviceInstances;
	}

	public void setInstances(List<ServiceInstance> instances) {
		if(serviceInstances == null)
			serviceInstances = new ArrayList<ServiceInstance>();
		serviceInstances.clear();
		serviceInstances.addAll(instances);
	}

	public void addAllInstances(List<ServiceInstance> instances) {
		if(serviceInstances == null)
			serviceInstances = new ArrayList<ServiceInstance>();
		serviceInstances.addAll(instances);
	}

	public void addInstance(ServiceInstance instance) {
		if(serviceInstances == null)
			serviceInstances = new ArrayList<ServiceInstance>();
		serviceInstances.add(instance);
	}

	public ServiceSpec getSpec() {
		return spec;
	}

	public void setSpec(ServiceSpec spec) {
		this.spec = spec;
	}
	
	public String getId() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUris() {
		List<String> result = new ArrayList<String>();
		for(ServiceInstance inst: serviceInstances)
			result.add(inst.getNativeUri());
		return result;
	}

	public ServiceInstance getInstance(String instanceId) throws ServiceInstanceNotFoundException{
		for(ServiceInstance instance: serviceInstances) {
			if(instance.getInstanceId().equals(instanceId))
				return instance;
		}
		
		throw new ServiceInstanceNotFoundException(this.name, instanceId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		
		Service other = (Service) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		String repr = "Service [name=" + name;
		repr += (getUris() != null) ? repr +=	", uri=" + getUris().toString() + "]" : "]";
		return repr;
	}

	public void setRecipe(Recipe serviceRecipe) {
		this.recipe = serviceRecipe;
	}

	public Recipe getRecipe() {
		return recipe;
	}
}
