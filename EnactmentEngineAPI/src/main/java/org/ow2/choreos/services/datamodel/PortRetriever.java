package org.ow2.choreos.services.datamodel;

public class PortRetriever {

    private static final int TOMCAT_PORT = 8080;
    private static final int EASYESB_PORT = 8180;

    public int getPortByPackageType(PackageType packageType) {
        if (packageType.equals(PackageType.TOMCAT))
            return TOMCAT_PORT;
        else if (packageType.equals(PackageType.EASY_ESB))
            return EASYESB_PORT;
        else
            throw new IllegalArgumentException("Port not known for package type " + packageType);        
    }

}
