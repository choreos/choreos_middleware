package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.deployment.services.datamodel.Service;

class ChoreographyRunningStatus {

	public List<Service> services;

	public ChoreographyRunningStatus() {
		this.services = new ArrayList<Service>();
	}
	
}