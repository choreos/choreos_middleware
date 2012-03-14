package eu.choreos.servicedeployer.npm;

public interface NodePoolManager {

	/**
	 * 
	 * @param config corresponds to the chef recipe name
	 */
	public String applyConfig(String configName);
}
