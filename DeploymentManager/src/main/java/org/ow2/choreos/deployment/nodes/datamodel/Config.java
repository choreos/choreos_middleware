package org.ow2.choreos.deployment.nodes.datamodel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Config {

    private String name;
    private ResourceImpact resourceImpact;
    private int numberOfInstances = 1;

    public Config() {
    
    }

    public Config(String name) {
    	this.name = name;
    }

    public Config(String name, int numberOfInstances) {
    	this.name = name;
    	this.numberOfInstances = numberOfInstances;
    }
    
    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

	public ResourceImpact getResourceImpact() {
		return resourceImpact;
	}

	public void setResourceImpact(ResourceImpact resourceImpact) {
		this.resourceImpact = resourceImpact;
	}

	public int getNumberOfInstances() {
		return numberOfInstances;
	}

	public void setNumberOfInstances(int numberOfInstances) {
		this.numberOfInstances = numberOfInstances;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + numberOfInstances;
		result = prime * result
				+ ((resourceImpact == null) ? 0 : resourceImpact.hashCode());
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
		Config other = (Config) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (numberOfInstances != other.numberOfInstances)
			return false;
		if (resourceImpact == null) {
			if (other.resourceImpact != null)
				return false;
		} else if (!resourceImpact.equals(other.resourceImpact))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Config [name=" + name + ", resourceImpact=" + resourceImpact
				+ ", numberOfInstances=" + numberOfInstances + "]";
	}
	
}
