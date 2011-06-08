package ime.usp.br.proxy;

import ime.usp.br.proxy.codegenerator.CodeGenerator;
import ime.usp.br.proxy.codegenerator.CodeGeneratorHelper;
import ime.usp.br.proxy.generic.GenericImpl;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;

public class ProxyFactory {
    public Object generateProxyImplementor(URL wsdlLocation) {

	generateServerClasses(wsdlLocation.toExternalForm());
	Object proxyInstance = getProxyInstance(wsdlLocation);

	return proxyInstance;
    }

    public static String getPortName(URL wsdlLocation) {
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();
	return cgh.getPortName(wsdlLocation);
    }

    // TODO: Test!
    public String getClassLocation(URL wsdlLocation) {
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();

	String destinationFolder = cgh.getDestinationFolder("", wsdlLocation);
	String packageName = destinationFolder.substring(1).replaceAll("/", "\\.");
	String className = packageName + getPortName(wsdlLocation);

	return className;
    }

    // Class is ** SUPPOSED ** to be generic. No need for a warning!
    @SuppressWarnings("unchecked")
    private Object getProxyInstance(URL wsdlLocation) {
	Class clazz = null;
	String className = getClassLocation(wsdlLocation)+"Impl";
	Object implementor = null;
	try {

	    clazz = Class.forName(className);
	    implementor = clazz.newInstance();
	    
	} catch (ClassNotFoundException e) {
	    System.out.println("Found no such class " + className + " in current directory");
	    e.printStackTrace();
	    return null;
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	}
	return implementor;
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

}
