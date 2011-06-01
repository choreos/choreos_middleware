package ime.usp.br.proxy;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import javax.wsdl.Service;
import javax.wsdl.WSDLException;

import ime.usp.br.proxy.codeGenerator.CodeGenerator;
import ime.usp.br.proxy.interceptor.ProxyInterceptor;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;

import org.ow2.easywsdl.schema.api.SchemaReader.FeatureConstants;
import org.ow2.easywsdl.wsdl.*;
import org.ow2.easywsdl.wsdl.impl.wsdl20.WSDLReaderImpl;

import com.sun.tools.xjc.generator.util.ExistingBlockReference;

public class ProxyFactory {
    public static void generateProxy(URL wsdlLocation) {

	int initialPort = wsdlLocation.getPort();

	generateServerCode(wsdlLocation.getHost());

	getServiceName(wsdlLocation);

	String address = instantiateProxy(wsdlLocation, initialPort);

	System.out.println("Service available at: " + address);

    }

    private static void generateServerCode(String host) {
	try {
	    new CodeGenerator().generateServerCode(new URL(host));
	} catch (MalformedURLException e) {
	    System.out.println("Verify that the URL " + host + " is not worng.");
	    e.printStackTrace();
	}
    }

    private static String getServiceName(URL wsdlLocation) {
	WSDLReaderImpl wsdlModel = null;

	try {
	    wsdlModel = new WSDLReaderImpl();
	    wsdlModel.readWSDL(new URI(wsdlLocation.toExternalForm()));
	} catch (org.ow2.easywsdl.wsdl.api.WSDLException e) {
	    System.out.println("Error while reading WSDL at " + wsdlLocation.getHost());
	    e.printStackTrace();
	} catch (URISyntaxException e) {
	    System.out.println("Error while reading WSDL at " + wsdlLocation.getHost());
	    e.printStackTrace();
	}

	Map<org.ow2.easywsdl.wsdl.api.WSDLReader.FeatureConstants, Object> allFeatures = wsdlModel.getFeatures();

	return null;
    }

    private static String instantiateProxy(URL wsdlLocation, int port) {
	Class cls = null;
	String address = "http://localhost:" + port + "/hello";

	generateServerCode(wsdlLocation.toExternalForm());

	try {
	    cls = Class.forName(getServiceName(wsdlLocation));
	} catch (ClassNotFoundException e) {
	    System.out.println("Found no such class " + getServiceName(wsdlLocation) + ".class in current directory");
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

}
