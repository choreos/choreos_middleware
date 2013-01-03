package org.ow2.choreos.chors.context;

public class ContextNotSentException extends Exception {

	private static final long serialVersionUID = -8530402048426407353L;

	private String serviceUri, partnerRole, partnerUri;

	public ContextNotSentException(String serviceUri, String partnerRole,
			String partnerUri) {
		super();
		this.serviceUri = serviceUri;
		this.partnerRole = partnerRole;
		this.partnerUri = partnerUri;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getServiceUri() {
		return serviceUri;
	}

	public String getPartnerRole() {
		return partnerRole;
	}

	public String getPartnerUri() {
		return partnerUri;
	}

	@Override
	public String toString() {
		return "ContextNotSentException [serviceUri=" + serviceUri
				+ ", partnerRole=" + partnerRole + ", partnerUri=" + partnerUri
				+ "]";
	}
	
}
