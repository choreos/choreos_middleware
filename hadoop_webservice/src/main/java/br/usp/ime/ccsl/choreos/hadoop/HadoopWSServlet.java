package br.usp.ime.ccsl.choreos.hadoop;

import javax.servlet.*;
import javax.ws.rs.Path;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

public class HadoopWSServlet extends CXFNonSpringServlet {
    @Override
    public void loadBus(ServletConfig servletConfig) {
        super.loadBus(servletConfig);
        
        // You could add the endpoint publish codes here
        Bus bus = getBus();
        BusFactory.setDefaultBus(bus);
        Endpoint.publish(HadoopWS.class.getAnnotation(Path.class).value(), new HadoopWS());
        
        // You can als use the simple frontend API to do this
        //JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		//sf.setResourceClasses(HadoopWS.class);
		//sf.setAddress(HadoopWS.class.getAnnotation(Path.class).value());
		//sf.create();
    }
}
