/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.nodes.datamodel.ResourceImpact;

@XmlRootElement
public class DeployableServiceSpec extends ServiceSpec {

    private String packageUri;
    private PackageType packageType;
    private String endpointName;
    private int port;
    private String owner;
    private String group;
    private int numberOfInstances = 1;
    private ResourceImpact resourceImpact;
    private String version;
    
    public DeployableServiceSpec() {
	
    }

    public DeployableServiceSpec(String name, ServiceType serviceType, PackageType packageType, ResourceImpact resourceImpact,
	    String version, String packageUri, int port, String endpointName, int numberOfInstances) {
	super.name = name;
	super.serviceType = serviceType;
	this.packageType = packageType;
	this.resourceImpact = resourceImpact;
	this.version = version;
	this.packageUri = packageUri;
	this.port = port;
	this.endpointName = endpointName;
	this.numberOfInstances = numberOfInstances;
    }

    public DeployableServiceSpec(String name, ServiceType serviceType, PackageType packageType, ResourceImpact resourceImpact,
	    String version, String packageUri, String endpointName, int numberOfInstances) {
	super.name = name;
	super.serviceType = serviceType;
	this.packageType = packageType;
	this.resourceImpact = resourceImpact;
	this.version = version;
	this.packageUri = packageUri;
	this.endpointName = endpointName;
	this.numberOfInstances = numberOfInstances;
    }

    public void setNumberOfInstances(int numberOfInstances) {
	if (numberOfInstances > 0)
	    this.numberOfInstances = numberOfInstances;
	else
	    this.numberOfInstances = 1;
    }

    public String getPackageUri() {
	return packageUri;
    }

    public void setPackageUri(String packageUri) {
	this.packageUri = packageUri;
    }

    public PackageType getPackageType() {
	return packageType;
    }

    public void setPackageType(PackageType packageType) {
	this.packageType = packageType;
    }

    public String getEndpointName() {
	return endpointName;
    }

    public void setEndpointName(String endpointName) {
	this.endpointName = endpointName;
    }

    public String getOwner() {
	return owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
    }

    public String getGroup() {
	return group;
    }

    public void setGroup(String group) {
	this.group = group;
    }

    public ResourceImpact getResourceImpact() {
	return resourceImpact;
    }

    public void setResourceImpact(ResourceImpact resourceImpact) {
	this.resourceImpact = resourceImpact;
    }

    public String getVersion() {
	return version;
    }

    public void setVersion(String version) {
	this.version = version;
    }

    public int getNumberOfInstances() {
	return numberOfInstances;
    }

    public void setPort(int port) {
	this.port = port;
    }

    public String getFileName() {
	FileNameRetriever retriever = new FileNameRetriever(this);
	return retriever.getFileName();
    }

    public int getPort() {
	int effectivePort = port;
	if (portIsNotDefined()) {
	    PortRetriever portRetriever = new PortRetriever();
	    effectivePort = portRetriever.getPortByPackageType(packageType);
	}
	return effectivePort;
    }

    private boolean portIsNotDefined() {
	return this.port == 0;
    }

    // equals and hash code are used from ServiceSpec

    @Override
    public String toString() {
	return "DeployableServiceSpec [packageUri=" + packageUri + ", packageType=" + packageType + ", endpointName="
		+ endpointName + ", port=" + port + ", owner=" + owner + ", group=" + group + ", numberOfInstances="
		+ numberOfInstances + ", version=" + version + "]";
    }

}