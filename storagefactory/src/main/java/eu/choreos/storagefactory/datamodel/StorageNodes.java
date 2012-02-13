package eu.choreos.storagefactory.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "storageNodes")
public class StorageNodes {

	
	private List<StorageNode> nodes = new ArrayList<StorageNode>();

	@XmlElement(name="storageNode")
	public List<StorageNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<StorageNode> nodes) {
		this.nodes = nodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageNodes other = (StorageNodes) obj;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StorageNodes [nodes=" + nodes + "]";
	}
	
	
}
