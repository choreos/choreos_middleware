package eu.choreos.nodepoolmanager.cloudprovider;

public class CloudProviderFactory {

	public enum CloudProviderType {
		FIXED, AWS, OPEN_STACK
	}
	
	public static CloudProvider getInstance(String cloudProviderType) {
		
		CloudProviderType type;
		try {
			type = CloudProviderType.valueOf(cloudProviderType);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(
					"Invalid CLOUD_PROVIDER in properties file: " + cloudProviderType);
		}
		
		return getInstance(type);
	}
	
	public static CloudProvider getInstance(CloudProviderType type) {
		
		switch(type) {
		
		case AWS:
			return new AWSCloudProvider();
		case FIXED:
			return new FixedCloudProvider();
		case OPEN_STACK:
			throw new IllegalArgumentException("OpenStack not implemented yet");
		default:
			throw new IllegalArgumentException("Invalid cloud provider type");
		}
	}
}
