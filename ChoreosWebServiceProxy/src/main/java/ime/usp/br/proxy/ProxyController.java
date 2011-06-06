/**
 * 
 */
package ime.usp.br.proxy;

import ime.usp.br.proxy.interceptor.ProxyInterceptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.endpoint.Server;

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

    List<Server> knownWebServices = new ArrayList<Server>();
    Server currentServer = null;

    /**
     * 
     */
    public ProxyController(Server initialServiceProvider) {
	knownWebServices.add(initialServiceProvider);
	currentServer = initialServiceProvider;
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
     * @return True, if successfully changed the service provider. False otherwise.
     */
    public void switchWSImplementation(Server serviceProvider) {
	if (!knownWebServices.contains(serviceProvider)) {
	    addNewServer(serviceProvider);
	}
	currentServer = serviceProvider;
    }

    /**
     * Short description
     * 
     * Longer description.
     *
     * @param service2
     */
    public boolean addNewServer(Server newServiceProvider) {
	return knownWebServices.add(newServiceProvider);
    }

    /**
     * Short description
     * 
     * Longer description.
     *
     * @return
     */
    public List<Server> getServerList() {
	return knownWebServices;
    }


}
