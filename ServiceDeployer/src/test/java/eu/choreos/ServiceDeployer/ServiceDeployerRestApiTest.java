package eu.choreos.ServiceDeployer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.ServiceDeployer.datamodel.ResourceImpact;
import eu.choreos.ServiceDeployer.datamodel.Service;
import eu.choreos.ServiceDeployer.datamodel.ServiceSpec;
import eu.choreos.ServiceDeployer.rest.StandaloneServer;


/**
 * Tests the Service Deployer REST API
 * 
 * It just tests if Service Deployer API properly receives the 
 * invocations and if the answers are coherent values
 * It doesn't test if the Service Deployer is 
 * actually doing properly its job
 * 
 * @author alfonso, leonardo, nelson
 *
 */
public class ServiceDeployerRestApiTest {

	private WebClient client;
	
    @BeforeClass
    public static void startServer() throws Exception {
    	StandaloneServer.start();
    }
    
    @AfterClass
    public static void stopServer() throws UnsupportedEncodingException {
        StandaloneServer.stop();
    }
  
    @Before
    public void setUpClient() {
    	client = WebClient.create("http://localhost:8080/serviceDeployer/");
    }
    
    @Test
    public void shouldSuccessfulyInvokeGetService() {
    	
        client.path("services/serviceID");
        Response response = client.get();
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void shouldSuccessfulyInvokeDeleteService() {
    	
        client.path("services/servicesID");
        Response response = client.delete();
        assertEquals(Family.SUCCESSFUL, Status.fromStatusCode(response.getStatus()).getFamily());
        // delete response has no body, so the response code is 204, which is from the SUCCESSFUL family
    }
    
    @Test
    public void shouldSuccessfulyInvokePostService() {

        client.path("services");
        client.type("application/xml");
        Response response = client.post("<serviceSpec><codeUri>URI</codeUri><resourceImpact>"+
        		" <memory>light</memory><cpu>medium</cpu>" +
        		" <io>heavy</io><region>France</region> "+
        		" </resourceImpact><type>JAR</type></serviceSpec>");
        
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void shouldReceiveInternalErrorFromPostService() {
    	
    	 client.path("services");
         client.type("application/xml");
         Response response = client.post("isNotXml");
         assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void shouldReceiveUnsupportedMediaTypeFromPostService() {
    	
    	 client.path("services");
         client.type("text/plain");
         Response response = client.post("isNotXml");
         assertEquals(Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void shouldReceiveBadRequestFromPostService() {
    	
    	 client.path("services");
         client.type("application/xml");
         Response response = client.post("<isNotServiceSpec/>");
         assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void shouldReceiveServiceWithProperServiceNode() {
    
        client.path("services");
        client.type("application/xml");

        ServiceSpec serviceSpec = new ServiceSpec();
        serviceSpec.setCodeUri("http://onthewild");
        serviceSpec.setType("onTheWild");
        
        ResourceImpact resourceImpact = new ResourceImpact();
        resourceImpact.setMemory("light");
        resourceImpact.setCpu("medium");
        resourceImpact.setIo("heavy");
        resourceImpact.setRegion("France");
        serviceSpec.setResourceImpact(resourceImpact);
        
        Service service = client.post(serviceSpec, Service.class);
        assertTrue(service instanceof Service);
    }
    
}
