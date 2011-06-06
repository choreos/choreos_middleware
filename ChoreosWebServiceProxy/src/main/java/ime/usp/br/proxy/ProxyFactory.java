package ime.usp.br.proxy;

import ime.usp.br.proxy.codeGenerator.CodeGenerator;
import ime.usp.br.proxy.codeGenerator.CodeGeneratorHelper;
import ime.usp.br.proxy.interceptor.ProxyInterceptor;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;

public class ProxyFactory {
    public void generateProxy(URL wsdlLocation) {

	int initialPort = wsdlLocation.getPort()+1;

	generateServerClasses(wsdlLocation.toExternalForm());

	String address = instantiateProxy(wsdlLocation, initialPort);

	System.out.println("Service available at: " + address);

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
    public String instantiateProxy(URL wsdlLocation, int port) {
	Class cls = null;
	String address = "http://localhost:" + port + "/" + getPortName(wsdlLocation);
	String className = getClassLocation(wsdlLocation);

	try {
	    cls = Class.forName(className);
	} catch (ClassNotFoundException e) {
	    System.out.println("Found no such class " + className + " in current directory");
	    e.printStackTrace();
	    return null;
	}

	ProxyInterceptor proxyService = new ProxyInterceptor();

	ServerFactoryBean serverFactoryBean = new ServerFactoryBean();
	serverFactoryBean.setAddress(address);
	serverFactoryBean.setServiceBean(cls);

	Server server = serverFactoryBean.create();

	server.getEndpoint().getInInterceptors().add(proxyService);
	return address;
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
