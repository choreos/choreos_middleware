package org.ow2.choreos.nodes.datamodel;


public class ActiveNode {

    private CloudNode cloudNode;
    private RunList runList;
    
    public String getId() {
	return cloudNode.getId();
    }
    
    public CloudNode getCloudNode() {
        return cloudNode;
    }
    
    public void setCloudNode(CloudNode cloudNode) {
        this.cloudNode = cloudNode;
    }
    
    public RunList getRunList() {
        return runList;
    }
    
    public void setRunList(RunList runList) {
        this.runList = runList;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cloudNode == null) ? 0 : cloudNode.hashCode());
	result = prime * result + ((runList == null) ? 0 : runList.hashCode());
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
	ActiveNode other = (ActiveNode) obj;
	if (cloudNode == null) {
	    if (other.cloudNode != null)
		return false;
	} else if (!cloudNode.equals(other.cloudNode))
	    return false;
	if (runList == null) {
	    if (other.runList != null)
		return false;
	} else if (!runList.equals(other.runList))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ActiveNode [cloudNode=" + cloudNode + ", runList=" + runList + "]";
    }
    
}
