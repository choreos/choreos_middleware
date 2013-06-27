package org.ow2.choreos.services.datamodel;

public class PortRetriever {

    private static final int TOMCAT_PORT = 8080;
    private static final int EASYESB_PORT = 8180;

    public int getPortByPackageType(PackageType packageType) {
        switch (packageType) {
        case TOMCAT:
            return TOMCAT_PORT;
        case EASY_ESB:
            return EASYESB_PORT;
        default:
            throw new IllegalArgumentException("Port not known for package " + packageType);
        }
    }

}
