package br.usp.ime.ccsl.choreos.middleware.proxy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.ccsl.choreos.wsdl.WsdlUtils;


public class WsdlUtilsTest {

    @Test
    public void testGetPortNameFromFile() throws Exception {
	assertEquals("HelloWorld8081", WsdlUtils.getPortName(Object.class.getResource("/role.wsdl")));
    }

    @Test
    public void testGetNamespaceFromFile() throws Exception {
        assertEquals("http://webservice.support.proxy.middleware.choreos.ccsl.ime.usp.br/",
        	WsdlUtils.getNamespace(Object.class.getResource("/role.wsdl")));
    }
}
