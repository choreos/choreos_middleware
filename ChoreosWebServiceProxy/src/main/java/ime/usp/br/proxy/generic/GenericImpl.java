package ime.usp.br.proxy.generic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;

public class GenericImpl implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	System.out.println("Method name: " + method.getName());
	System.out.print("Arguments:   ");
	for (int i = 0; i < args.length; i++) {
	    System.out.print(args[i]);
	}
	System.out.println();
	return null;
    }

}
