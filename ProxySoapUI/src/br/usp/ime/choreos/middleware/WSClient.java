package br.usp.ime.choreos.middleware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import br.usp.ime.choreos.middleware.exceptions.FrameworkException;
import br.usp.ime.choreos.middleware.exceptions.InvalidOperationName;
import br.usp.ime.choreos.middleware.exceptions.WSDLException;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.Request.SubmitException;
import com.eviware.soapui.support.SoapUIException;

/**
 * Proxy to invoke and treat web services operations
 *
 */
public class WSClient {

	private final String wsdl;
	private WsdlInterface iface;
	private List<String> operations;

	/**
	 * 
	 * @param wsdl the path or URL to the WSDL document
	 * @throws XmlException
	 * @throws IOException
	 * @throws FrameworkException
	 * @throws WSDLException
	 */
	public WSClient(String wsdl) throws XmlException, IOException, FrameworkException, WSDLException {
		
		WsdlProject project;
		try {
			project = new WsdlProject();
		} catch (SoapUIException e) {
			throw new FrameworkException(e);
		}
		
		try {
			iface = WsdlInterfaceFactory.importWsdl(project, wsdl, true)[0];
		} catch (SoapUIException e) {
			throw new WSDLException(e);
		}
		
		parseWsdlOperations();

		this.wsdl = wsdl;
	}
	
	/**
	 * Retrieves the operations available in the service, using the SoapUI library
	 */
	private void parseWsdlOperations() {

		this.operations = new ArrayList<String>();

		for (com.eviware.soapui.model.iface.Operation suop : this.iface.getAllOperations()) {
			String op = suop.getName();
			this.operations.add(op);
		}
	}

	List<String> getOperations() {
		return this.operations;
	}

	/**
	 * 
	 * @return the WSDL URL
	 */
	public String getWsdl() {
		return wsdl;
	}

	/**
	 * Makes a request to a web service operation
	 * @param operationName
	 * @param parameters
	 * @return the operation response
	 * @throws InvalidOperationName
	 * @throws FrameworkException
	 */
	public String request(String operationName, String... parameters) throws InvalidOperationName, FrameworkException {
		
		if (!operations.contains(operationName))
			throw new InvalidOperationName();

		WsdlOperation operation = (WsdlOperation) iface.getOperationByName(operationName);

		String defaultRequestContent = operation.getRequestAt(0).getRequestContent();

		String requestContent = SoapEnvelopeHelper.generate(defaultRequestContent, parameters);

		WsdlRequest request = operation.addNewRequest("myRequest");
		request.setRequestContent(requestContent);

		// submit the request
		WsdlSubmit<WsdlRequest> submit = null;
		try {
			submit = request.submit(new WsdlSubmitContext(request), false);
		} catch (SubmitException e) {
			throw new FrameworkException(e);
		}

		// wait for the response
		Response response = submit.getResponse();

		// print the response
		String responseContent = response.getContentAsString();

		responseContent = SoapEnvelopeHelper.getCleanResponse(responseContent);

		return responseContent;
	}

}
