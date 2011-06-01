package ime.usp.br.proxy.generic;

import java.lang.reflect.*;

public class GenericImpl implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	System.out.println("WSClient.request(" + method.getName() + ", " + args.toString() + ");");
	System.out.println("Method name: " + method.getName());
	System.out.print("Arguments:   ");
	for (int i = 0; i < args.length; i++) {
	    System.out.print(args[i]);
	}
	System.out.println();
	return null;
    }

}
