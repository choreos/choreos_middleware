package eu.choreos.enactment.spec;

class EndpointResolver {

	public enum SAType {SOAP_PROVIDE, SOAP_CONSUME, CD_PROVIDE};
	
	public static String getEndpointName(String serviceName, SAType saType) {
		
		switch (saType) {
		case SOAP_CONSUME:
			return serviceName.toLowerCase() + "1234";
		case SOAP_PROVIDE:
			return serviceName + "PortService";
		case CD_PROVIDE:
			return "CD" + serviceName + "Port";
		}
		
		return null;
	}
}
