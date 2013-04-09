package org.ow2.choreos.deployment.nodes;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.datamodel.NodeRestRepresentation;
import org.ow2.choreos.deployment.rest.DeploymentManagerServer;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
public class BaseTest {
	
	protected static String nodePoolManagerHost;
	private static DeploymentManagerServer server;
	
    @BeforeClass
    public static void startServer() throws Exception {
    	
    	LogConfigurator.configLog();
		server = new DeploymentManagerServer();
		server.start();
        nodePoolManagerHost = DeploymentManagerServer.URL;
    }

    @AfterClass
    public static void stopServer() throws UnsupportedEncodingException {
    	
    	server.stop();
    }

    protected static NodeRestRepresentation getNodeFromResponse(Response response) {
    	
        String location = (String) response.getMetadata().get("Location").get(0);
        WebClient webClient = WebClient.create(location);
        return webClient.get(NodeRestRepresentation.class);
    }
    
    /**
     * Verify if <code>uri</code> matches http://localhost:port/nodes/.+
     * @param uri
     * @return
     */
    protected boolean isNodeLocation(String uri) {
    	
    	String regex = DeploymentManagerServer.URL + "nodes/.+";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(uri);
    	
    	return matcher.matches();
    }

}
