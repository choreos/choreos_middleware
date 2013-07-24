package org.ow2.choreos.chors.bus;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;

import com.ebmwebsourcing.easierbsm.BSMFactoryImpl;
import com.ebmwebsourcing.easyesb.esb.api.ESBFactory;
import com.ebmwebsourcing.easyesb.external.protocol.soap.impl.server.SoapServer;
import com.ebmwebsourcing.easyesb.soa.api.ESBException;
import com.ebmwebsourcing.easyesb.soa.api.node.Node;
import com.ebmwebsourcing.easyesb.soa.impl.config.ConfigurationImpl;

public class EasyESBNodeImplTest {

//    @Before
    public void setUp() throws Exception {
        EasyAPILoader.loadEasyAPI();
    }

    private static Node createBSMNode(QName name, String host, int port, final int soap_port)
            throws ESBException {
        ESBFactory factory = new BSMFactoryImpl("creation-resources-service-factory", "rawreport-service-factory");
        Node node = factory.createNode(name, new ConfigurationImpl(host, port, new HashMap<String, String>() {
            {
                put(SoapServer.PORT_PROPERTY_NAME, String.valueOf(soap_port));
            }
        }));
        
        
        return node;
    }
    
//    @Test
    public void test() throws ESBException, EasyESBException {
        Node monitoringBus = createBSMNode(new QName("http://petals.ow2.org", "MonitoringBus"), "localhost", 9101, 8085);
        String bsmAdminEndpoint = "http://localhost:8085/services/bsmadminExternalEndpoint";
        
        String adminEndpoint = "http://localhost:8180/services/adminExternalEndpoint";
        EasyESBNode node = new EasyESBNodeImpl(adminEndpoint);
        
        node.notifyEasierBSM(bsmAdminEndpoint);
        
        Map listenedEndpoints = monitoringBus.getListenedEndpoints();
    }
    
    public static void main(String[] args) throws ESBException {
        System.out.println("oi");
    }

}
