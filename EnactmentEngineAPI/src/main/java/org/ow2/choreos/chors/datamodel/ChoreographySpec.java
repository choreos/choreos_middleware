/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceSpec;

@XmlRootElement
public class ChoreographySpec {

    private List<DeployableServiceSpec> deployableServiceSpecs = new ArrayList<DeployableServiceSpec>();
    private List<LegacyServiceSpec> legacyServiceSpecs = new ArrayList<LegacyServiceSpec>();

    public ChoreographySpec() {

    }

    public ChoreographySpec(DeployableServiceSpec... serviceSpecs) {
	for (DeployableServiceSpec spec : serviceSpecs)
	    this.deployableServiceSpecs.add(spec);
    }

    public ServiceSpec getServiceSpecByName(String ServiceSpecName) {

	for (ServiceSpec svcSpec : deployableServiceSpecs) {
	    if (ServiceSpecName.equals(svcSpec.getName()))
		return svcSpec;
	}
	throw new NoSuchElementException("Service spec named " + ServiceSpecName + " does not exist");
    }

    public void addServiceSpec(DeployableServiceSpec serviceSpec) {
	this.deployableServiceSpecs.add(serviceSpec);
    }

    public void addServiceSpec(LegacyServiceSpec serviceSpec) {
	this.legacyServiceSpecs.add(serviceSpec);
    }

    public List<DeployableServiceSpec> getDeployableServiceSpecs() {
	return deployableServiceSpecs;
    }

    public void setDeployableServiceSpecs(List<DeployableServiceSpec> serviceSpecs) {
	this.deployableServiceSpecs = serviceSpecs;
    }

    public List<LegacyServiceSpec> getLegacyServiceSpecs() {
        return legacyServiceSpecs;
    }

    public void setLegacyServiceSpecs(List<LegacyServiceSpec> legacyServiceSpecs) {
        this.legacyServiceSpecs = legacyServiceSpecs;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((deployableServiceSpecs == null) ? 0 : deployableServiceSpecs.hashCode());
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
	ChoreographySpec other = (ChoreographySpec) obj;
	if (deployableServiceSpecs == null) {
	    if (other.deployableServiceSpecs != null)
		return false;
	} else if (!deployableServiceSpecs.equals(other.deployableServiceSpecs))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ChoreographySpec [deployableServiceSpecs=" + deployableServiceSpecs + ", legacyServiceSpecs="
		+ legacyServiceSpecs + "]";
    }

}
