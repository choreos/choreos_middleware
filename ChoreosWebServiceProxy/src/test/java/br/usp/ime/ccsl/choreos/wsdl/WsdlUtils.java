package br.usp.ime.ccsl.choreos.wsdl;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import com.ibm.wsdl.BindingImpl;

public class WsdlUtils {

    public static String getNamespace(URL wsdlInterfaceDescriptor) {
	Definition def = null;
	try {
	    WSDLFactory factory = WSDLFactory.newInstance();
	    WSDLReader reader = factory.newWSDLReader();
	    reader.setFeature("javax.wsdl.verbose", false);
	    reader.setFeature("javax.wsdl.importDocuments", true);
	    def = reader.readWSDL(null, wsdlInterfaceDescriptor.toExternalForm());
	} catch (WSDLException e) {
	    e.printStackTrace();
	}

	return def.getTargetNamespace();
    }

    public static String getPortName(URL wsdlInterfaceDescriptor) {
	Definition def = null;
	try {
	    WSDLFactory factory = WSDLFactory.newInstance();
	    WSDLReader reader = factory.newWSDLReader();
	    reader.setFeature("javax.wsdl.verbose", false);
	    reader.setFeature("javax.wsdl.importDocuments", true);
	    def = reader.readWSDL(null, wsdlInterfaceDescriptor.toExternalForm());
	} catch (WSDLException e) {
	    e.printStackTrace();
	}

	Collection<?> bindingList = def.getBindings().values();
	for (Iterator<?> bindingIterator = bindingList.iterator(); bindingIterator.hasNext();) {
	    BindingImpl bind = (BindingImpl) bindingIterator.next();
	    if (!bind.getPortType().isUndefined())
		return bind.getPortType().getQName().getLocalPart();
	}

	return "";
    }

}
