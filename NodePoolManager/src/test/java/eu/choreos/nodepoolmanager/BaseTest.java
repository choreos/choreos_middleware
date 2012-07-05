package eu.choreos.nodepoolmanager;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.cloudprovider.FixedCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;
import eu.choreos.nodepoolmanager.rest.NodePoolManagerStandaloneServer;
import eu.choreos.nodepoolmanager.utils.LogConfigurator;


public class BaseTest {
	
	protected static String TEST_IMAGE = ""; // era 1 
	private static String TEST_DEFAULT_PROVIDER = ""; // era stub
	protected static String EXPECTED_IMAGE = "us-east-1/ami-ccf405a5";
	private static String RESOURCE = "nodes";
	
	protected static WebClient client;
    protected static CloudProvider infrastructure = new FixedCloudProvider();
    protected static Node sampleNode;
    

    @BeforeClass
    public static void startServer() throws Exception {
    	
    	LogConfigurator.configLog();
    	
    	client = WebClient.create(NodePoolManagerStandaloneServer.URL);
    	
        NodePoolManagerStandaloneServer.startNodePoolManager();
        Configuration.set("DEFAULT_PROVIDER", TEST_DEFAULT_PROVIDER);
        // createSampleNode();

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(Long.MAX_VALUE);
        conduit.getClient().setConnectionTimeout(Long.MAX_VALUE);
    }

    @AfterClass
    public static void stopServer() throws UnsupportedEncodingException {
    	//infrastructure.destroyNode(sampleNode.getId());
        NodePoolManagerStandaloneServer.stopNodePoolManager();
    }

//    public static void createSampleNode() throws RunNodesException {
//        sampleNode = new Node();
//        sampleNode.setImage(TEST_IMAGE);
//
//        infrastructure.createNode(sampleNode);
//    }
    
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
    	
    	String regex = NodePoolManagerStandaloneServer.URL + RESOURCE + "/.+";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(uri);
    	
    	return matcher.matches();
    }
    
    protected void resetPath() {
        client.back(true);
    }

}
