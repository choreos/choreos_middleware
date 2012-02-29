package eu.choreos.storagefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;
import eu.choreos.storagefactory.rest.StandaloneServer;

/**
 * Tests the Storage Factory REST API
 * 
 * It just tests if Storage API properly receives the invocations and if the
 * answers are coherent values It doesn't test if the Storage Factory is
 * actually doing properly its job
 * 
 * @author leonardo
 * 
 */
public class StorageRestApiTest {

	private static final String HOST = "http://localhost:9102/storagefactory/";
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
		client = WebClient.create(HOST);
	}
	
    @After
    public void resetPath() {
        client.back(true);
    }


	@Test
	public void shouldSuccessfulyInvokeGetStorage() {
		//shouldReceiveStorageNodeWithProperTypeAndUuid();
		// Since the method shouldReceiveStorageNodeWithProperTypeAndUuid
		// sets the client.path to "storage" it only requires to
		// add the "uuid" to the path.
		client.path("storages");
		client.path("314159265");
		Response response = client.get();
		assertTrue( response.getStatus()== Status.OK.getStatusCode() ||
				    response.getStatus()== Status.NO_CONTENT.getStatusCode() );
	}

	@Test
	public void shouldSuccessfulyInvokeDeleteStorage() {
		//shouldReceiveStorageNodeWithProperTypeAndUuid();
		// Since the method shouldReceiveStorageNodeWithProperTypeAndUuid
		// sets the client.path to "storage" it only requires to
		// add the "uuid" to the path.
		client.path("storages");
		client.path("314159265");
		Response response = client.delete();
		assertEquals(Family.SUCCESSFUL,
				Status.fromStatusCode(response.getStatus()).getFamily());
		// delete response has no body, so the response code is 204, which is
		// from the SUCCESSFUL family
	}

	@Test
	public void shouldSuccessfulyInvokePostStorage() {

		client.path("storages");
		client.type("application/xml");
		Response response = client
				.post("<storageNodeSpec><uuid>314159265</uuid><type>MySQL</type></storageNodeSpec>");
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReceiveInternalErrorFromPostStorage() {

		client.path("storages");
		client.type("application/xml");
		Response response = client.post("isNotXml");
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
				response.getStatus());
	}

	@Test
	public void shouldReceiveUnsupportedMediaTypeFromPostStorage() {

		client.path("storages");
		client.type("text/plain");
		Response response = client.post("isNotXml");
		assertEquals(Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode(),
				response.getStatus());
	}

	@Test
	public void shouldReceiveBadRequestFromPostStorage() {

		client.path("storages");
		client.type("application/xml");
		Response response = client.post("<isNotStorageNodeSpec/>");
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void shouldReceiveStorageNodeWithProperTypeAndUuid() {

		client.path("storages");
		client.type("application/xml");
		StorageNodeSpec spec = new StorageNodeSpec();
		spec.setType("MySQL");
		spec.setUuid("314159265");
		StorageNode node = client.post(spec, StorageNode.class);
		assertEquals(spec.getUuid(), node.getUuid());
	}

	@Test
	public void shouldReceiveValidNodeFromGetStorage() {

		client.path("storages/314159265");
		StorageNode node = client.get(StorageNode.class);
		System.out.println("StorageNode: "+node+ "class:");
		assertTrue(node instanceof StorageNode);
	}
}
