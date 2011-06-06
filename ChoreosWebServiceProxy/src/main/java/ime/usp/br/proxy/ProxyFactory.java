package ime.usp.br.proxy;

import ime.usp.br.proxy.codeGenerator.CodeGenerator;
import ime.usp.br.proxy.codeGenerator.CodeGeneratorHelper;
import ime.usp.br.proxy.generic.GenericImpl;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;

public class ProxyFactory {
    public Server generateProxy(URL wsdlLocation, int port) {

	int initialPort;
	
	initialPort = (port == 0 ) ? wsdlLocation.getPort() + 1 : port;

	generateServerClasses(wsdlLocation.toExternalForm());

	Server proxyServer = instantiateProxy(wsdlLocation, initialPort);

	System.out.println("Service available at: " + proxyServer.getEndpoint().getEndpointInfo().getAddress());

	return proxyServer;
    }

    private void generateServerClasses(String host) {
	CodeGenerator codeGenerator = new CodeGenerator();
	try {
	    codeGenerator.generateServerClasses(new URL(host));
	} catch (MalformedURLException e) {
	    System.out.println("Verify that the URL " + host + " is not wrong.");
	    e.printStackTrace();
	}
    }

    private String getPortName(URL wsdlLocation) {
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();
	return cgh.getPortName(wsdlLocation);
    }

    @SuppressWarnings("unchecked")
    // The Class variable cls is ** SUPPOSED ** to be a generic type.
    public Server instantiateProxy(URL wsdlLocation, int port) {
	Class cls = null;
	String address = "http://localhost:" + port + "/" + getPortName(wsdlLocation);
	String className = getClassLocation(wsdlLocation);
	Server server = null;
	ServerFactoryBean serverFactoryBean = null;
	
	try {
	    cls = Class.forName(className);

	    Object implementor = Proxy.newProxyInstance(cls.getClassLoader(), cls.getClasses(), new GenericImpl());

	    serverFactoryBean = new ServerFactoryBean();
	    serverFactoryBean.setAddress(address);
	    serverFactoryBean.setServiceBean(implementor);
	    
	    server = serverFactoryBean.create();
	    
	} catch (ClassNotFoundException e) {
	    System.out.println("Found no such class " + className + " in current directory");
	    e.printStackTrace();
	    return null;
	}
	// ProxyInterceptor proxyService = new ProxyInterceptor();

	// server.getEndpoint().getInInterceptors().add(proxyService);
	return server;
    }

    public String getClassLocation(URL wsdlLocation) {
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();

	String namespace = cgh.getNamespace(wsdlLocation);
	String destinationFolder = cgh.getDestinationFolder("", namespace);
	String packageName = destinationFolder.substring(1).replaceAll("/", "\\.");
	String className = packageName + getPortName(wsdlLocation);

	return className;
    }

}
