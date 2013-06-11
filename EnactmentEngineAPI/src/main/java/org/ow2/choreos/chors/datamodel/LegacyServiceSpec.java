/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.datamodel;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.services.datamodel.ServiceSpec;

@XmlRootElement
public class LegacyServiceSpec extends ServiceSpec {

    List<String> nativeURIs;

    public List<String> getNativeURIs() {
	return nativeURIs;
    }

    public void setNativeURIs(List<String> nativeURIs) {
	this.nativeURIs = nativeURIs;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((nativeURIs == null) ? 0 : nativeURIs.hashCode());
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
	if (!super.equals(obj))
	    return false;

	LegacyServiceSpec other = (LegacyServiceSpec) obj;

	if (nativeURIs == null) {
	    if (other.nativeURIs != null)
		return false;
	} else if (!nativeURIs.equals(other.nativeURIs))
	    return false;
	return true;
    }

}