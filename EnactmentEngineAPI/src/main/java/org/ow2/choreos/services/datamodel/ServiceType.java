/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceType implements Serializable {

    private static final long serialVersionUID = -1593475545608859760L;
    
    public static ServiceType SOAP;    
    public static ServiceType REST;
    
    private static Map<String, ServiceType> serviceTypes;
    
    static {
        SOAP = new ServiceType("SOAP");
        serviceTypes = new HashMap<String, ServiceType>();
        serviceTypes.put(SOAP.toString(), SOAP);
    }
    
    private String type;

    public ServiceType() {

        // default constructor in behalf of JAX-B satisfaction

        // TODO: the right thing in deserialization would be to use the
        // getPackageType method.
        // otherwise, we will create several instances to the same package type.
        // and the intent of safer enum pattern was to avoid this.
        // one bad consequence: we have to use .equals instead ==
    }

    private ServiceType(String type) {
        this.type = type;
    }
    
    public static synchronized ServiceType getServiceType(String type) {
        if (!serviceTypes.containsKey(type)) {
            ServiceType serviceType = new ServiceType(type);
            serviceTypes.put(serviceType.toString(), serviceType);
        }
        return serviceTypes.get(type);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        ServiceType other = (ServiceType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return type;
    }


}
