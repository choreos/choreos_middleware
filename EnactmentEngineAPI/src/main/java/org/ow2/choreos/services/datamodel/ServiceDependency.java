/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

/**
 * Represents the dependence of a service acting with a role
 * 
 * @author leonardo
 * 
 */
public class ServiceDependency {

    private String serviceSpecName;
    private String serviceSpecRole;

    public ServiceDependency() {

    }

    public ServiceDependency(String serviceSpecName, String serviceRole) {
        this.serviceSpecName = serviceSpecName;
        this.serviceSpecRole = serviceRole;
    }

    public String getServiceSpecName() {
        return serviceSpecName;
    }

    public void setServiceSpecName(String choreographyServiceSpecName) {
        this.serviceSpecName = choreographyServiceSpecName;
    }

    public String getServiceSpecRole() {
        return serviceSpecRole;
    }

    public void setServiceSpecRole(String choreographyServiceSpecRole) {
        this.serviceSpecRole = choreographyServiceSpecRole;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serviceSpecName == null) ? 0 : serviceSpecName.hashCode());
        result = prime * result + ((serviceSpecRole == null) ? 0 : serviceSpecRole.hashCode());
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
        ServiceDependency other = (ServiceDependency) obj;
        if (serviceSpecName == null) {
            if (other.serviceSpecName != null)
                return false;
        } else if (!serviceSpecName.equals(other.serviceSpecName))
            return false;
        if (serviceSpecRole == null) {
            if (other.serviceSpecRole != null)
                return false;
        } else if (!serviceSpecRole.equals(other.serviceSpecRole))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ServiceDependency [name=" + serviceSpecName + ", role=" + serviceSpecRole + "]";
    }

}
