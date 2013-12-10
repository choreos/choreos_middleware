package org.ow2.choreos.ee.services;

public class ServiceCreatorFactory {

    public static ServiceCreator serviceCreatorForTest;

    public static boolean testing = false;

    public static ServiceCreator getNewInstance() {
	if (testing)
	    return serviceCreatorForTest;
	return new ServiceCreator();
    }

}
