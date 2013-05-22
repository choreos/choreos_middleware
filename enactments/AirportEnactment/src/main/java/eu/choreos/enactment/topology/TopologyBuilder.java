package eu.choreos.enactment.topology;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;

import eu.choreos.enactment.CloudNode;

class TopologyBuilder {

	private static final String TEMPLATE_FILE_NAME = "topology_template.xml";
	private static final String SLAVE_FRAGMENT_NAME = "slave_fragment.xml";
	private static final String TOPOLOGY_PATH = "src/main/resources/topology.xml";
	private CloudNode master;
	private List<CloudNode> slaves;
	
	public TopologyBuilder(CloudNode master, List<CloudNode> slaves) {
		this.master = master;
		this.slaves = slaves;
	}
	
	/**
	 * 
	 * @return the built topology file
	 */
	public File build() {
		
		String template = getTemplateAsString();

		String topology = generateTopology(template);
		
		File file = saveTopology(topology);
		return file;
	}

	private String generateTopology(String template) {

		String topology = template;
		String name = master.getPrivateDNS();
		name = name.replace(".compute-1.internal", "");
		name = name.replace(".ec2.internal", "");
		// in the chef recipe, "name" was node['hostname'] 
		topology = topology.replace("$MASTER_PRIVATE_DNS", name);
		topology = topology.replace("$MASTER_PRIVATE_IP", master.getPrivateIp());
		
		StringBuilder slavesXml = new StringBuilder("");
		for (CloudNode slave: slaves) {
			String frag = getSlaveFragment(slave);
			slavesXml.append("\n").append(frag);
		}
		
		return topology.replace("$SLAVES", slavesXml);
	}

	private String getSlaveFragment(CloudNode slave) {

		String frag = getSlaveFragmentAsString();
		String name = slave.getPrivateDNS();
		name = name.replace(".compute-1.internal", "");
		name = name.replace(".ec2.internal", "");
		frag = frag.replace("$SLAVE_PRIVATE_DNS", name);
		frag = frag.replace("$SLAVE_PRIVATE_IP", slave.getPrivateIp());
		return frag;
	}

	private File saveTopology(String topology) {
		File file = new File(TOPOLOGY_PATH);
		try {
			FileUtils.write(file, topology);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not generate topology.xml");
		}
		return file;
	}
	
	private String getTemplateAsString() {
		URL url = ClassLoader.getSystemResource(TEMPLATE_FILE_NAME);
		try {
			return FileUtils.readFileToString(new File(url.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Not possible retrieve topology template file");
		}	
		return null;
	}
	
	private String getSlaveFragmentAsString() {
		URL url = ClassLoader.getSystemResource(SLAVE_FRAGMENT_NAME);
		try {
			return FileUtils.readFileToString(new File(url.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Not possible retrieve slave fragment file");
		}	
		return null;
	}
}
