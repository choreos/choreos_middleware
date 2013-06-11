/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlSeeAlso;

import org.ow2.choreos.chors.datamodel.LegacyServiceSpec;

@XmlSeeAlso({ DeployableServiceSpec.class, LegacyServiceSpec.class })
public abstract class ServiceSpec {
    
    protected String name;
    protected String uuid;
    protected ServiceType serviceType;
    protected List<String> roles;
    protected List<ServiceDependency> dependencies;
    
    public ServiceSpec() {
	this.uuid = UUID.randomUUID().toString();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public ServiceType getServiceType() {
        return serviceType;
    }
    
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
    
    public List<String> getRoles() {
        return roles;
    }
    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public List<ServiceDependency> getDependencies() {
        return dependencies;
    }
  
    public void setDependencies(List<ServiceDependency> dependencies) {
        this.dependencies = dependencies;
    }
    
    public void addDependency(ServiceDependency dependency) {
	if (dependencies == null)
	    dependencies = new ArrayList<ServiceDependency>();
	dependencies.add(dependency);
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
	ServiceSpec other = (ServiceSpec) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (uuid == null) {
	    if (other.uuid != null)
		return false;
	} else if (!uuid.equals(other.uuid))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ServiceSpec [name=" + name + ", uuid=" + uuid + "]";
    }

}