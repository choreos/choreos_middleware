package org.ow2.choreos.chors.context;

import java.util.List;


public interface ContextSender {
	
	/**
	 * Calls setInvokationAddress operation on service in the <code>serviceEndpoint</code>.
	 * So, the service in <code>endpoint</code> will know that its
	 * partner named <code>partnerName</code> with <code>partnerRole</code> 
	 * is realized by instances in <code>partnerEndpoints</code>.
	 * 
	 * @param serviceEndpoint
	 * @param partnerRole
	 * @param partnerName
	 * @param partnerEndpoints
	 * @throws ContextNotSentException if context was not successfully set
	 */
	public void sendContext(String serviceEndpoint, 
			String partnerRole, String partnerName, List<String> partnerEndpoints) throws ContextNotSentException;
}
