package proxy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proxy.GenericProxy;

public class GenericProxyTest {

    GenericProxy proxy = new
    // GenericProxy(String targetNamespace, String serviceName, String
    // endpointAddress, String portName)
    GenericProxy("http://hello/", "HelloWorld8081Service", "http://localhost:8081/hello?wsdl", "HelloWorld8081");

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    private void invokeWebMethod() {
	proxy.invokeWebMethod("sayHello", new Object[] { "Felps" });// TODO
								    // Auto-generated
								    // method
								    // stub

    }

    public static void main(String[] args) {
	GenericProxy proxy = new
	// GenericProxy(String targetNamespace, String serviceName, String
	// endpointAddress, String portName)
	GenericProxy("http://hello/", "HelloWorld8081Service", "http://localhost:8081/hello", "HelloWorld8081");

	System.out.println(proxy.invokeWebMethod("sayHello", new Object[] { "Felps" }).toString());// TODO
								    // Auto-generated
								    // method
    }

}
