/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.ResourceImpactDefs.MemoryType;

@XmlRootElement
public class NodeSpec {

    private MemoryType memoryImpact;
    private String cpuImpact;
    private String storageImpact;
    private String networkImpact;
    private String regionImpact;

    private String so;
    private String zone;
    private String image;

    public NodeSpec() {

    }

    public ResourceImpact getResourceImpact() {
	ResourceImpact impact = new ResourceImpact();
	impact.setCpu(cpuImpact);
	impact.setStorage(storageImpact);
	impact.setMemory(memoryImpact);
	impact.setNetwork(networkImpact);
	return impact;
    }

    public MemoryType getMemoryImpact() {
        return memoryImpact;
    }

    public void setMemoryImpact(MemoryType memoryImpact) {
        this.memoryImpact = memoryImpact;
    }

    public String getCpuImpact() {
        return cpuImpact;
    }

    public void setCpuImpact(String cpuImpact) {
        this.cpuImpact = cpuImpact;
    }

    public String getStorageImpact() {
        return storageImpact;
    }

    public void setStorageImpact(String storageImpact) {
        this.storageImpact = storageImpact;
    }

    public String getNetworkImpact() {
        return networkImpact;
    }

    public void setNetworkImpact(String networkImpact) {
        this.networkImpact = networkImpact;
    }

    public String getRegionImpact() {
        return regionImpact;
    }

    public void setRegionImpact(String regionImpact) {
        this.regionImpact = regionImpact;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cpuImpact == null) ? 0 : cpuImpact.hashCode());
	result = prime * result + ((image == null) ? 0 : image.hashCode());
	result = prime * result + ((memoryImpact == null) ? 0 : memoryImpact.hashCode());
	result = prime * result + ((networkImpact == null) ? 0 : networkImpact.hashCode());
	result = prime * result + ((regionImpact == null) ? 0 : regionImpact.hashCode());
	result = prime * result + ((so == null) ? 0 : so.hashCode());
	result = prime * result + ((storageImpact == null) ? 0 : storageImpact.hashCode());
	result = prime * result + ((zone == null) ? 0 : zone.hashCode());
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
	NodeSpec other = (NodeSpec) obj;
	if (cpuImpact == null) {
	    if (other.cpuImpact != null)
		return false;
	} else if (!cpuImpact.equals(other.cpuImpact))
	    return false;
	if (image == null) {
	    if (other.image != null)
		return false;
	} else if (!image.equals(other.image))
	    return false;
	if (memoryImpact != other.memoryImpact)
	    return false;
	if (networkImpact == null) {
	    if (other.networkImpact != null)
		return false;
	} else if (!networkImpact.equals(other.networkImpact))
	    return false;
	if (regionImpact == null) {
	    if (other.regionImpact != null)
		return false;
	} else if (!regionImpact.equals(other.regionImpact))
	    return false;
	if (so == null) {
	    if (other.so != null)
		return false;
	} else if (!so.equals(other.so))
	    return false;
	if (storageImpact == null) {
	    if (other.storageImpact != null)
		return false;
	} else if (!storageImpact.equals(other.storageImpact))
	    return false;
	if (zone == null) {
	    if (other.zone != null)
		return false;
	} else if (!zone.equals(other.zone))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "NodeSpec [memoryImpact=" + memoryImpact + ", cpuImpact=" + cpuImpact + ", storageImpact="
		+ storageImpact + ", networkImpact=" + networkImpact + ", regionImpact=" + regionImpact + ", so=" + so
		+ ", zone=" + zone + ", image=" + image + "]";
    }


}
