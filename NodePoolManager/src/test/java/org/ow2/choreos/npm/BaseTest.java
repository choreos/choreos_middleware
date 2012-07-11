package org.ow2.choreos.npm;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.ow2.choreos.npm.datamodel.NodeRestRepresentation;
import org.ow2.choreos.npm.rest.NPMStandaloneServer;
import org.ow2.choreos.utils.LogConfigurator;

public class BaseTest {
	
	protected static String nodePoolManagerHost;
	
    @BeforeClass
    public static void startServer() throws Exception {
    	
    	LogConfigurator.configLog();
        NPMStandaloneServer.startNodePoolManager();
        nodePoolManagerHost = NPMStandaloneServer.URL;
    }

    @AfterClass
    public static void stopServer() throws UnsupportedEncodingException {
    	
        NPMStandaloneServer.stopNodePoolManager();
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
    	
    	String regex = NPMStandaloneServer.URL + "nodes/.+";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(uri);
    	
    	return matcher.matches();
    }

}
