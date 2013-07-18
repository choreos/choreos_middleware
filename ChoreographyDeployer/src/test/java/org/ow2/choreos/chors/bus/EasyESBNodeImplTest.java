package org.ow2.choreos.chors.bus;

import java.util.HashMap;

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

    @Before
    public void setUp() throws Exception {
        EasyAPILoader.loadEasyAPI();
    }

    private Node createBSMNode(final QName name, final String host, final int port, final int soap_port)
            throws ESBException {
        ESBFactory factory = new BSMFactoryImpl("creation-resources-service-factory", "rawreport-service-factory");
        Node node = factory.createNode(name, new ConfigurationImpl(host, port, new HashMap<String, String>() {
            {
                put(SoapServer.PORT_PROPERTY_NAME, String.valueOf(soap_port));
            }
        }));
        return node;
    }
    
    @Test
    public void test() throws ESBException {
        Node monitoringBus = this.createBSMNode(new QName("http://petals.ow2.org", "MonitoringBus"), "localhost", 9101, 8085);
    }

}
