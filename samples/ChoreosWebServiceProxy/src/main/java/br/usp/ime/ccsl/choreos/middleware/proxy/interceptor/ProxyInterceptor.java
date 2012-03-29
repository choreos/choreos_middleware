package br.usp.ime.ccsl.choreos.middleware.proxy.interceptor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerRegistry;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;

public class ProxyInterceptor extends AbstractSoapInterceptor {

    private static Log log = LogFactory.getLog(ProxyInterceptor.class);

    private Endpoint epDestination;

    public ProxyInterceptor(String destination) {
	super(Phase.READ);

	epDestination = chooseServerEndpoint(destination);
    }

    public void handleMessage(SoapMessage message) throws Fault {
	Exchange ex = message.getExchange();

	ex.put(Endpoint.class, epDestination);
	ex.put(Binding.class, epDestination.getBinding());
	ex.put(Service.class, epDestination.getService());

	InterceptorChain chain = message.getInterceptorChain();
	chain.add(epDestination.getInInterceptors());
	chain.add(epDestination.getBinding().getInInterceptors());
	chain.add(epDestination.getService().getInInterceptors());

	chain.setFaultObserver(epDestination.getOutFaultObserver());

	log.debug(String.format("Changed the endpoint from \"%s\" to \"%s\"\n", ex.getEndpoint().getEndpointInfo()
		.getAddress(), epDestination.getEndpointInfo().getAddress()));
    }

    private Endpoint chooseServerEndpoint(String destinationAddress) {

	Bus bus = CXFBusFactory.getDefaultBus();

	ServerRegistry serverRegistry = bus.getExtension(ServerRegistry.class);
	List<Server> servers = serverRegistry.getServers();

	Endpoint ep = null;

	for (Server server : servers) {
	    if (server.getEndpoint().getEndpointInfo().getAddress().equals(destinationAddress)) {
		ep = server.getEndpoint();
		log.debug(String.format("Destination has been set to \"%s\"\n", ep.getEndpointInfo().getAddress()));
	    }
	}
	return ep;
    }
}
