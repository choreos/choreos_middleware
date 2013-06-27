/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import java.util.List;

import javax.xml.bind.annotation.XmlSeeAlso;

import org.ow2.choreos.chors.datamodel.LegacyService;

@XmlSeeAlso({ DeployableService.class, LegacyService.class })
public abstract class Service {

    private ServiceSpec spec;

    public Service() {

    }

    public Service(ServiceSpec serviceSpec) {
        this.spec = serviceSpec;
    }

    public abstract List<String> getUris();

    public ServiceSpec getSpec() {
        return spec;
    }

    public void setSpec(ServiceSpec spec) {
        this.spec = spec;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = spec.hashCode();
        result = prime * result + ((getUris() == null) ? 0 : getUris().hashCode());
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

        Service other = (Service) obj;

        if (spec == null) {
            if (other.spec != null)
                return false;
        } else if (!spec.equals(other.spec))
            return false;

        return true;
    }

    @Override
    public String toString() {
        String repr = "Service [uuid=" + spec.getUuid();
        repr += ", spec={" + spec + "}";
        repr += (getUris() != null) ? repr += ", uri=" + getUris().toString() + "]" : "]";
        return repr;
    }

}
