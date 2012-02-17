package eu.choreos.servicedeployer.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Services {

	private List<Service> services = new ArrayList<Service>();

	@XmlElement(name = "service")
	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

}
