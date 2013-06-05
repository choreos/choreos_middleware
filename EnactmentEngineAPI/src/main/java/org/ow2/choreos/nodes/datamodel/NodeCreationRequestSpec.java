package org.ow2.choreos.nodes.datamodel;

import javax.xml.bind.annotation.XmlRootElement;

import org.ow2.choreos.services.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.ResourceImpactDefs.MemoryTypes;

/**
 * This class is used to pass two different objects to a REST operation. We
 * build this class with both object's attributes and send it as XML
 */
@XmlRootElement
public class NodeCreationRequestSpec {

    private String nodeId;
    private Integer nodeCpus;
    private Integer nodeRam;
    private Integer nodeStorage;
    private String nodeSo;
    private String nodeZone;
    private String nodeIp;
    private String nodeHostname;
    private String nodeImage;
    private Integer nodeState;

    private MemoryTypes memoryImpact;
    private String cpuImpact;
    private String ioImpact;
    private String regionImpact;

    public NodeCreationRequestSpec() {

    }

    public NodeCreationRequestSpec(Node node, ResourceImpact resImpact) {
	nodeId = node.getId();
	nodeCpus = node.getCpus();
	nodeRam = node.getRam();
	nodeStorage = node.getStorage();
	nodeSo = node.getSo();
	nodeZone = node.getZone();
	nodeIp = node.getIp();
	nodeHostname = node.getHostname();
	nodeImage = node.getImage();
	nodeState = node.getState();

	memoryImpact = resImpact.getMemory();
	cpuImpact = resImpact.getCpu();
	ioImpact = resImpact.getIo();
	regionImpact = resImpact.getRegion();
    }

    public Node getNode() {
	Node node = new Node();
	node.setId(nodeId);
	node.setCpus(nodeCpus);
	node.setRam(nodeRam);
	node.setStorage(nodeStorage);
	node.setSo(nodeSo);
	node.setZone(nodeZone);
	node.setIp(nodeIp);
	node.setHostname(nodeHostname);
	node.setImage(nodeImage);
	node.setState(nodeState);

	return node;
    }

    public ResourceImpact getResourceImpact() {
	ResourceImpact impact = new ResourceImpact();
	impact.setCpu(cpuImpact);
	impact.setIo(ioImpact);
	impact.setMemory(memoryImpact);
	impact.setRegion(regionImpact);

	return impact;
    }
}
