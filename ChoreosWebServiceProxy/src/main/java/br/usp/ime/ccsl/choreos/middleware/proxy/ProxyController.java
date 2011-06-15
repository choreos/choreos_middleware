/**
 * 
 */
package br.usp.ime.ccsl.choreos.middleware.proxy;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;

import br.usp.ime.ccsl.choreos.middleware.proxy.codegenerator.CodeGeneratorHelper;
import br.usp.ime.ccsl.choreos.middleware.proxy.interceptor.ProxyInterceptor;

/**
 * The Proxy service provider.
 * 
 * This class implements the service provider and proxy controller methods.
 * 
 * @author Felipe Pontes Guimarães
 * @version beta
 * @param (methods and constructors only)
 * @throws
 * @see ProxyFactory
 * @see ProxyInterceptor
 */
public class ProxyController {

    Map<String, ProxyInterceptor> knownWebServices = new HashMap<String, ProxyInterceptor>();
    Server currentServer = null;
    String currentInterceptorURL = null;

    /**
     * Short description
     * 
     * (Optional) Longer description
     *
     * @return the currentInterceptor
     */
    public ProxyInterceptor getCurrentWebService() {
        return knownWebServices.get(currentInterceptorURL);
    }

    /**
     * 
     */
    public ProxyController() {
    }

    /**
     * Demands the proxy to change from the current service provider to another
     * one.
     * 
     * Takes the org.apache.cxf.endpoint.Server indicated in the argument and
     * sets it as the current service provider for this role. If the server is
     * not already listed as a knownServiceProvider, it should be automatically
     * added.
     * 
     * @param serviceProvider
     *            The service provider that will, from now on, receive requests
     *            from the role's clients
     * @return True, if successfully changed the service provider. False
     *         otherwise.
     */
    public void switchWSImplementation(String url) {
	ProxyInterceptor interceptor = knownWebServices.get(url);
	currentInterceptorURL = url;

	Endpoint endpoint = currentServer.getEndpoint();

	removeCurrentInterceptor(endpoint);
	defineCurrentInterceptor(interceptor, endpoint);

    }

    private void defineCurrentInterceptor(ProxyInterceptor interceptor, Endpoint endpoint) {
	endpoint.getInInterceptors().add(interceptor);
    }

    private void removeCurrentInterceptor(Endpoint endpoint) {
	endpoint.getInInterceptors().remove(currentInterceptorURL);
    }

    /**
     * Short description
     * 
     * Longer description.
     * 
     * @param service2
     */
    public void addNewWebService(String url) {
	ProxyInterceptor interceptor = new ProxyInterceptor(url);
	knownWebServices.put(url, interceptor);
	
	if(currentInterceptorURL == null){
	    currentInterceptorURL = url;
	}
	
    }

    /**
     * Short description
     * 
     * Longer description.
     * 
     * @return
     */
    public List<String> getServerList() {
	return new ArrayList<String>(knownWebServices.keySet());
    }

    private URL getServerURL() {
	URL url = null;
	try {
	    url = new URL(currentServer.getEndpoint().getEndpointInfo().getAddress());
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	}
	return url;
    }
    public URL instantiateProxy(URL wsdlLocation, int port) {
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();
	if (currentServer != null)
	    return getServerURL();

	String address = "http://localhost:" + port + "/"+ cgh.getDestinationFolder("", wsdlLocation)+ ProxyFactory.getPortName(wsdlLocation);

	ProxyFactory factory = new ProxyFactory();
	Object proxyInstance = factory.generateProxyImplementor(wsdlLocation);

	ServerFactoryBean serverFactoryBean = new ServerFactoryBean();

	serverFactoryBean.setAddress(address);
	serverFactoryBean.setServiceBean(proxyInstance);

	currentServer = serverFactoryBean.create();

	return getServerURL();
    }
}
