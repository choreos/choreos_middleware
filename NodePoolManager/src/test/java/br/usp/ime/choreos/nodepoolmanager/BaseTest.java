package br.usp.ime.choreos.nodepoolmanager;

import java.io.UnsupportedEncodingException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.jclouds.compute.RunNodesException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseTest {
    protected static final WebClient client = WebClient.create("http://localhost:8080/");
    protected static Node sampleNode;

    @BeforeClass
    public static void startServer() throws Exception {
        NodePoolManagerStandaloneServer.start();
        Configuration.set("DEFAULT_PROVIDER", "stub");
        createSampleNode();

        HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
        conduit.getClient().setReceiveTimeout(Long.MAX_VALUE);
        conduit.getClient().setConnectionTimeout(Long.MAX_VALUE);
    }

    @AfterClass
    public static void stopServer() throws UnsupportedEncodingException {
        NodePoolManagerStandaloneServer.stop();
        destroyNode(sampleNode);
    }

    public static void createSampleNode() throws RunNodesException {
        sampleNode = new Node();
        sampleNode.setImage("1");

        Infrastructure infrastructure = new Infrastructure();
        infrastructure.createNode(sampleNode);
    }

    protected static void destroyNode(Node node) throws UnsupportedEncodingException {
        client.path("nodes/" + node.getId());
        client.delete();
    }


}
