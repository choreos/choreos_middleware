package eu.choreos.nodepoolmanager;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;
import eu.choreos.nodepoolmanager.rest.NodePoolManagerStandaloneServer;
import eu.choreos.nodepoolmanager.utils.LogConfigurator;


public class BaseTest {
	
	protected static WebClient client;

    @BeforeClass
    public static void startServer() throws Exception {
    	
    	LogConfigurator.configLog();
    	
    	client = WebClient.create(NodePoolManagerStandaloneServer.URL);
    	
        NodePoolManagerStandaloneServer.startNodePoolManager();

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(Long.MAX_VALUE);
        conduit.getClient().setConnectionTimeout(Long.MAX_VALUE);
    }

    @AfterClass
    public static void stopServer() throws UnsupportedEncodingException {
    	
        NodePoolManagerStandaloneServer.stopNodePoolManager();
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
    	
    	String regex = NodePoolManagerStandaloneServer.URL + "nodes/.+";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(uri);
    	
    	return matcher.matches();
    }
}
