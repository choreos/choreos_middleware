package org.ow2.choreos.chors.context;


public interface ContextSender {
	
	/**
	 * Calls setInvokationAddress operation on service in the <code>serviceEndpoint</code>.
	 * So, the service in <code>endpoint</code> will know that its
	 * partner with <code>partnerRole</code> is realized by <code>partnerEndpoint</code>.
	 * 
	 * @param serviceEndpoint
	 * @param partnerRole
	 * @param partnerEndpoint
	 * @throws ContextNotSentException if context was not successfully set
	 */
	public void sendContext(String serviceEndpoint, 
			String partnerRole, String partnerEndpoint) throws ContextNotSentException;

}
