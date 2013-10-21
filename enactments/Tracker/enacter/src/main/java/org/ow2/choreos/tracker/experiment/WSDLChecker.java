package org.ow2.choreos.tracker.experiment;

import java.util.concurrent.Callable;

import javax.ws.rs.WebApplicationException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;

public class WSDLChecker {

    private static final String WSDL_EXCERPT = "portType";

    private String wsdl;

    public WSDLChecker(String wsdl) {
        this.wsdl = wsdl;
    }

    public boolean check() {
        CheckerTask task = new CheckerTask();
        int timeout = 10;
        Invoker<Boolean> invoker = new InvokerBuilder<Boolean>("CheckerTask", task, timeout).trials(3)
                .pauseBetweenTrials(20).build();
        try {
            return invoker.invoke();
        } catch (InvokerException e) {
            return false;
        }
    }

    private class CheckerTask implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            WebClient client = WebClient.create(wsdl);
            try {
                String wsdlStr = client.get(String.class);
                return wsdlStr.contains(WSDL_EXCERPT);
            } catch (WebApplicationException e) {
                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
