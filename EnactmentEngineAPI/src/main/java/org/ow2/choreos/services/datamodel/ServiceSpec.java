/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import java.util.UUID;

import javax.xml.bind.annotation.XmlSeeAlso;

import org.ow2.choreos.chors.datamodel.LegacyServiceSpec;

@XmlSeeAlso({ DeployableServiceSpec.class, LegacyServiceSpec.class })
public abstract class ServiceSpec {
    
    protected ServiceType serviceType;
    private String uuid;

    public ServiceSpec() {

    }

    protected ServiceSpec(ServiceType serviceType) {
	uuid = UUID.randomUUID().toString();
	this.serviceType = serviceType;
    }

    public String getUUID() {
	return uuid;
    }

    /**
     * Use it carefully. Should be used only when updating a existing service
     * 
     * @return
     */
    public void setUUID(String uuid) {
	this.uuid = uuid;
    }

    public ServiceType getType() {
	return serviceType;
    }

    public void setType(ServiceType type) {
	this.serviceType = type;
    }

    public abstract int getNumberOfInstances();

    public abstract void setNumberOfInstances(int numberOfInstances);

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;

	ServiceSpec other = (ServiceSpec) obj;

	if (serviceType == null) {
	    if (other.serviceType != null)
		return false;
	} else if (!serviceType.equals(other.serviceType))
	    return false;

	return true;
    }
}