/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NodeSpec {

    private String image;
    private String zone;
    private ResourceImpact resourceImpact;

    public NodeSpec() {

    }

    public ResourceImpact getResourceImpact() {
	return this.resourceImpact;
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
	result = prime * result + ((image == null) ? 0 : image.hashCode());
	result = prime * result + ((resourceImpact == null) ? 0 : resourceImpact.hashCode());
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
	if (image == null) {
	    if (other.image != null)
		return false;
	} else if (!image.equals(other.image))
	    return false;
	if (resourceImpact == null) {
	    if (other.resourceImpact != null)
		return false;
	} else if (!resourceImpact.equals(other.resourceImpact))
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
	return "NodeSpec [image=" + image + ", zone=" + zone + ", resourceImpact=" + resourceImpact + "]";
    }

}
