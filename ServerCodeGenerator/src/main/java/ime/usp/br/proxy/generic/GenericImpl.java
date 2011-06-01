package ime.usp.br.proxy.generic;

import javax.jws.WebMethod;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.common.i18n.Message; //import javax.jws.WebService;
import org.apache.cxf.interceptor.Fault;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Provider;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class GenericImpl extends AbstractSoapInterceptor{

	public void handleMessage(SoapMessage message) throws Fault {
		System.out.println(message.toString());
	}

}
