package org.ow2.choreos.servicedeployer.datamodel;

/**
 * The artifact is the deployable unit encompassing service binaries,
 * and it is intended to be deployed somewhere according to its type.
 * 
 * @author leonardo
 *
 */
public enum ArtifactType {

	COMMAND_LINE("jar"), 
	TOMCAT("war"), 
	EASY_ESB("tar.gz"), 
	LEGACY(null), 
	OTHER(null);
	
    private final String extension;
    
    private ArtifactType(String extension) {
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
