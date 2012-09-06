package org.ow2.choreos.servicedeployer.datamodel;

public enum ServiceType {

	COMMAND_LINE("jar"), 
	TOMCAT("war"), 
	EASY_ESB("zip"), 
	LEGACY(null), 
	OTHER(null);
	
    private final String extension;
    
    private ServiceType(String extension) {
            this.extension = extension;
    }
    
    /**
     * 
     * @return deploy file extension
     */
    public String getExtension() {
    	if (this.extension == null)
    		throw new IllegalArgumentException("ServiceType " + this + " does not provide an extension.");
        return this.extension;
    }
}
