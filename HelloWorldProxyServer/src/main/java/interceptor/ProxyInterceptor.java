package interceptor;

import hello.HelloWorld8081;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Executor;

import javax.xml.ws.EndpointReference;

import org.apache.cxf.Bus;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.tcp.SoapTcpDestination;
import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerRegistry;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.transport.http_jetty.JettyHTTPDestination;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public class ProxyInterceptor extends AbstractSoapInterceptor {

	public ProxyInterceptor() {
		super(Phase.READ);
	}

	public void handleMessage(SoapMessage message) throws Fault {
		Exchange ex = message.getExchange();

		Bus bus = CXFBusFactory.getDefaultBus();

		ServerRegistry serverRegistry = bus.getExtension(ServerRegistry.class);
		List<Server> servers = serverRegistry.getServers();
		Endpoint ep = ex.getEndpoint();
		for (Server server : servers) {
			if (!server.getEndpoint().getEndpointInfo().getAddress().equals(
					ep.getEndpointInfo().getAddress())) {
				System.out.println("Troquei o ep do address de "
						+ server.getEndpoint().getEndpointInfo().getAddress());
				ep = server.getEndpoint();
				System.out.println("para");
			}
			System.out.println(server.getEndpoint().getEndpointInfo()
					.getAddress());
		}

		ex.put(Endpoint.class, ep);
		ex.put(Binding.class, ep.getBinding());
		ex.put(Service.class, ep.getService());

		InterceptorChain chain = message.getInterceptorChain();
		chain.add(ep.getInInterceptors());
		chain.add(ep.getBinding().getInInterceptors());
		chain.add(ep.getService().getInInterceptors());

		chain.setFaultObserver(ep.getOutFaultObserver());

	}

}
