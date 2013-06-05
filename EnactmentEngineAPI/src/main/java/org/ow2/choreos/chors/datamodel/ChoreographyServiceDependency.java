/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.datamodel;

/**
 * Represents the dependence of a service acting with a role
 * 
 * @author leonardo
 * 
 */
public class ChoreographyServiceDependency {

    private String choreographyServiceSpecName;
    private String choreographyServiceSpecRole;

    public ChoreographyServiceDependency() {

    }

    public ChoreographyServiceDependency(String choreographyServiceSpecName, String choreographyServiceRole) {
	this.choreographyServiceSpecName = choreographyServiceSpecName;
	this.choreographyServiceSpecRole = choreographyServiceRole;
    }

    public String getChoreographyServiceSpecName() {
	return choreographyServiceSpecName;
    }

    public void setChoreographyServiceSpecName(String choreographyServiceSpecName) {
	this.choreographyServiceSpecName = choreographyServiceSpecName;
    }

    public String getChoreographyServiceSpecRole() {
	return choreographyServiceSpecRole;
    }

    public void setChoreographyServiceSpecRole(String choreographyServiceSpecRole) {
	this.choreographyServiceSpecRole = choreographyServiceSpecRole;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((choreographyServiceSpecName == null) ? 0 : choreographyServiceSpecName.hashCode());
	result = prime * result + ((choreographyServiceSpecRole == null) ? 0 : choreographyServiceSpecRole.hashCode());
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
	ChoreographyServiceDependency other = (ChoreographyServiceDependency) obj;
	if (choreographyServiceSpecName == null) {
	    if (other.choreographyServiceSpecName != null)
		return false;
	} else if (!choreographyServiceSpecName.equals(other.choreographyServiceSpecName))
	    return false;
	if (choreographyServiceSpecRole == null) {
	    if (other.choreographyServiceSpecRole != null)
		return false;
	} else if (!choreographyServiceSpecRole.equals(other.choreographyServiceSpecRole))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ServiceDependency [name=" + choreographyServiceSpecName + ", role=" + choreographyServiceSpecRole + "]";
    }

}
