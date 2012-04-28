package eu.choreos.enactment.topology;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.enactment.CloudNode;

public class TopologyBuilderTest {

	// the generated topology must be the same as TOPOLOGY_TEST
	private static final String TOPOLOGY_TEST = "topology_test.xml";
	
	@Before
	public void beforeTest() {
	
		//TODO to check if topology.xml already existis
		// if so, delete it
	}
	
	@Test
	public void shouldGenerateTheTopologyFile() throws IOException {

		CloudNode master = new CloudNode("host1", "192.168.0.101", "CHOREOS-MASTER");
		List<CloudNode> slaves = new ArrayList<CloudNode>();
		slaves.add(new CloudNode("host2", "192.168.0.102", "SLAVE1"));
		slaves.add(new CloudNode("host3", "192.168.0.103", "SLAVE2"));
		
		TopologyBuilder builder = new TopologyBuilder(master, slaves);
		File topologyFile = builder.build();
		String topology = FileUtils.readFileToString(topologyFile);
		
    	URL uri = ClassLoader.getSystemResource(TOPOLOGY_TEST);
		String topologyTest = FileUtils.readFileToString(new File(uri.getFile()));
		
		assertEquals(topologyTest, topology);
	}

}
