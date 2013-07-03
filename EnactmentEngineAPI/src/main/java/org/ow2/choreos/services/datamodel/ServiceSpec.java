/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlSeeAlso;

import org.ow2.choreos.chors.datamodel.LegacyServiceSpec;

@XmlSeeAlso({ DeployableServiceSpec.class, LegacyServiceSpec.class })
public abstract class ServiceSpec implements Serializable {

    private static final long serialVersionUID = -1319357334438972249L;

    protected String name;
    protected ServiceType serviceType;
    protected List<String> roles;
    protected List<ServiceDependency> dependencies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        result = prime * result + ((dependencies == null) ? 0 : dependencies.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + ((serviceType == null) ? 0 : serviceType.hashCode());
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
        if (dependencies == null) {
            if (other.dependencies != null)
                return false;
        } else if (!dependencies.equals(other.dependencies))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        if (serviceType != other.serviceType)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ServiceSpec [name=" + name + ", serviceType=" + serviceType + ", roles=" + roles + ", dependencies="
                + dependencies + "]";
    }

}