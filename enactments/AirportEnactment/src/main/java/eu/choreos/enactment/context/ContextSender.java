package eu.choreos.enactment.context;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

class ContextSender {
	
	/**
	 * Calls setInvokationAddress operation on service in the <code>serviceEndpoint</code>.
	 * So, the service in <code>endpoint</code> will know that its
	 * partner with <code>partnerRole</code> is realized by <code>partnerEndpoint</code>.
	 * A context is transferred by successive calls of this operation.
	 * 
	 * @param endpoint
	 * @param context
	 * @return true if context was successfully set, false otherwise
	 */
	public boolean sendContext(String serviceEndpoint, 
			String partnerRole, String partnerEndpoint) {
		
		try {

			WSClient client = new WSClient(serviceEndpoint + "?wsdl");
			client.request("setInvocationAddress", partnerRole, partnerEndpoint);
			
		} catch (WSDLException e) {
			e.printStackTrace();
			return false;
		} catch (XmlException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (FrameworkException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidOperationNameException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
