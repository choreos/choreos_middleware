package eu.choreos.ServiceDeployer.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "services")
public class ServiceNodes {

	private List<ServiceNode> nodes = new ArrayList<ServiceNode>();

	@XmlElement(name="service")
	public List<ServiceNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<ServiceNode> nodes){
		this.nodes = nodes;
	}
	

}
