package org.ow2.choreos.deployment.services.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Service {

	private String name;
	private ServiceSpec spec;
	private String id;
	private List<ServiceInstance> serviceInstances = new ArrayList<ServiceInstance>(); 

	
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
			List<String> uris = serviceSpec.getLegacyServicesUris();
			
			if(uris.size() > 0) {
				
				for(String uri: uris) {
					URIInfoRetriever info = new URIInfoRetriever(uri);
					
					ServiceInstance si = new ServiceInstance(null, this);
					
					si.setHost(info.getHostname());
					si.setIp(info.getIp());
					si.setUri(serviceSpec.getDeployableUri());
					
					serviceInstances.add(si);
				}
			}
		}
	}


	public Service() {
		
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

	public List<String> getUris() {
		List<String> result = new ArrayList<String>();
		for(ServiceInstance inst: serviceInstances)
			result.add(inst.getUri());
		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		// never use ServiceInstance hashCode to avoid circular dependecies
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		// TODO: add more stuff to the hash code
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
