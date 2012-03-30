package br.usp.ime.ccsl.choreos.middleware.proxy.generic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GenericImpl implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	//Error e = new Error("There are no service providers for this role");
	//e.printStackTrace();
	return null;
    }

}
