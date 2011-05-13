/*
 * DIIClient class
 * J2SE unmanaged application
 * Service lookup: JAX-RPC ServiceFactory
 * Service access: DII
 */
package proxy;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceFactory;

import org.apache.log4j.Logger;

public class GenericProxy {

    private static final Logger log = Logger.getLogger(GenericProxy.class);
    private String targetNamespace;
    private String endpointAddress;
    private String portName;
    private javax.xml.rpc.Service service;

    public GenericProxy(String targetNamespace, String serviceName, String endpointAddress, String portName) {

	this.targetNamespace = targetNamespace;
	this.endpointAddress = endpointAddress;
	this.portName = portName;

	try {
	    /* Service lookup */
	    ServiceFactory serviceFactory = ServiceFactory.newInstance();
	    service = serviceFactory.createService(new QName(targetNamespace, serviceName));
	} catch (Exception e) {
	    System.out.println("Error: Problem instantiating service");
	    e.printStackTrace();
	}
    }

    public Object invokeWebMethod(String operationName, Object[] arguments) {

	try {

	    /* Service access */
	    Call call = (Call) service.createCall();

	    call.setProperty(Call.ENCODINGSTYLE_URI_PROPERTY, "");
	    call.setProperty(Call.OPERATION_STYLE_PROPERTY, "document");

	    call.setTargetEndpointAddress(endpointAddress);

	    call.removeAllParameters();

	    call.setPortTypeName(new QName(targetNamespace, portName));
	    call.setOperationName(new QName(targetNamespace, operationName));

	    if (call.isParameterAndReturnSpecRequired(call.getOperationName())) {
		call.addParameter("yourName", new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class,
			javax.xml.rpc.ParameterMode.INOUT);
		call.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));

		log.info("call.isParameterAndReturnSpecRequired(call.getOperationName())");
	    }

	    log.info("Web service answered: ");
	    /* Service invocation */
	    Object invocation = call.invoke(arguments);
	    log.info("Invocation successful.");
	    return invocation;
	} catch (Exception e) {
	    log.error("ERROR: Problem invoking method " + operationName + " with arguments " + arguments.toString(), e);
	}
	return null;
    }
}