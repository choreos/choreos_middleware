/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * The artifact is the deployable unit encompassing service binaries, and it is
 * intended to be deployed somewhere according to its type.
 * 
 * @author leonardo
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PackageType implements Serializable {

    public static PackageType COMMAND_LINE;
    public static PackageType TOMCAT;
    public static PackageType EASY_ESB;

    private static final long serialVersionUID = -2622225615133550982L;
    private static Map<String, PackageType> packageTypes;

    static {
        COMMAND_LINE = new PackageType("COMMAND_LINE");
        TOMCAT = new PackageType("TOMCAT");
        EASY_ESB = new PackageType("EASY_ESB");
        packageTypes = new HashMap<String, PackageType>();
        packageTypes.put(COMMAND_LINE.toString(), COMMAND_LINE);
        packageTypes.put(TOMCAT.toString(), TOMCAT);
        packageTypes.put(EASY_ESB.toString(), EASY_ESB);
    }

    private String type;

    public PackageType() {

        // default constructor in behalf of JAX-B satisfaction

        // TODO: the right thing in deserialization would be to use the
        // getPackageType method.
        // otherwise, we will create several instances to the same package type.
        // and the intent of safer enum pattern was to avoid this.
        // one bad consequence: we have to use .equals instead ==
    }

    private PackageType(String type) {
        this.type = type;
    }

    public static synchronized PackageType getPackageType(String type) {
        if (!packageTypes.containsKey(type)) {
            PackageType packageType = new PackageType(type);
            packageTypes.put(packageType.toString(), packageType);
        }
        return packageTypes.get(type);
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
        PackageType other = (PackageType) obj;
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
