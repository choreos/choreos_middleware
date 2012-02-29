package eu.choreos.nodepoolmanager;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.jclouds.compute.RunNodesException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;
import eu.choreos.nodepoolmanager.rest.NodePoolManagerStandaloneServer;


public class BaseTest {
	
	protected static String TEST_IMAGE = ""; // era 1 
	private static String TEST_DEFAULT_PROVIDER = ""; // era stub
	protected static String EXPECTED_IMAGE = "us-east-1/ami-ccf405a5";
	private static String LOCAL_HOST = "http://localhost:9100/";
	private static String RESOURCE = "nodes";
	
	protected static final WebClient client = WebClient.create(LOCAL_HOST);
    protected static AWSCloudProvider infrastructure = new AWSCloudProvider();
    protected static Node sampleNode;
    

    @BeforeClass
    public static void startServer() throws Exception {
        NodePoolManagerStandaloneServer.start();
        Configuration.set("DEFAULT_PROVIDER", TEST_DEFAULT_PROVIDER);
        createSampleNode();

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(Long.MAX_VALUE);
        conduit.getClient().setConnectionTimeout(Long.MAX_VALUE);
    }

    @AfterClass
    public static void stopServer() throws UnsupportedEncodingException {
    	infrastructure.destroyNode(sampleNode.getId());
        NodePoolManagerStandaloneServer.stop();
    }

    public static void createSampleNode() throws RunNodesException {
        sampleNode = new Node();
        sampleNode.setImage(TEST_IMAGE);

        infrastructure.createNode(sampleNode);
    }
    
    protected static NodeRestRepresentation getNodeFromResponse(Response response) {
    	
        String location = (String) response.getMetadata().get("Location").get(0);
        WebClient webClient = WebClient.create(location);
        return webClient.get(NodeRestRepresentation.class);
    }
    
    /**
     * Verify if <code>uri</code> matches http://localhost:8080/nodes/.+
     * @param uri
     * @return
     */
    protected boolean isNodeLocation(String uri) {
    	
    	String regex = LOCAL_HOST + RESOURCE + "/.+";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(uri);
    	
    	return matcher.matches();
    }

}
