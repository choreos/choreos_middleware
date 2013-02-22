package org.ow2.choreos.deployment.services.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.deployment.services.ServiceInstanceNotFoundException;


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
	 * A Unique Id for the service
	 */
	private String id;

	/**
	 * The list of all instances of the service
	 */
	private List<ServiceInstance> serviceInstances;

	
	
	
	/**
	 * Default constructor for Java XML Bindings
	 */
	public Service() {

	}

	public Service(ServiceSpec serviceSpec) throws Exception {

		this.id = UUID.randomUUID().toString();

		this.spec = serviceSpec;
		if (this.spec.getArtifactType() == null) {
			this.spec.setArtifactType(ArtifactType.OTHER);
		}

		if (serviceSpec.getName() == null || serviceSpec.getName().isEmpty()) {
			throw new Exception();
		} else {
			name = serviceSpec.getName();
		}

		if (serviceSpec.artifactType == ArtifactType.LEGACY) {
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
				si.setNativeUri(serviceSpec.getDeployableUri());

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		
		if (id == null) {
			if(other.id != null) 
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		String repr = "Service [name=" + name;
		repr += (getUris() != null) ? repr +=	", uri=" + getUris().toString() + "]" : "]";
		return repr;
	}
}
