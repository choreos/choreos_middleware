package eu.choreos.storagefactory;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.storagefactory.rest.StandaloneServer;

/**
 * Tests the Storage Factory REST API
 * 
 * It just tests if Storage API properly receives the 
 * invocations and if the answers are coherent values
 * It doesn't test if the Storage Factory is 
 * actually doing properly its job
 * 
 * @author leonardo
 *
 */
public class StorageRestApiTest {

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
    	client = WebClient.create("http://localhost:8080/storagefactory/");
    }
    
    @Test
    public void shouldSuccessfulyInvokeGetUserStorages() {
    	
        client.path("storages/userID");
        Response response = client.get();
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void shouldSuccessfulyInvokeGetCorrelationStorage() {

        client.path("correlations/correlationID/storage");
        Response response = client.get();
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void shouldSuccessfulyInvokeDeleteStorage() {
    	
        client.path("storages/userID/storageID");
        Response response = client.delete();
        assertEquals(Family.SUCCESSFUL, Status.fromStatusCode(response.getStatus()).getFamily());
        // delete response has no body, so the response code is 204, which is from the SUCCESSFUL family
    }
    
    @Test
    public void shouldSuccessfulyInvokePostStorage() {

        client.path("storages");
        client.type("application/xml");
        Response response = client.post("<storageNodeSpec/>");
        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }
}
