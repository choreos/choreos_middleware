package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.deployment.services.ScheduledServiceModification;
import org.ow2.choreos.deployment.services.datamodel.SpecAndService;

class ChoreographyRunningStatus {

	//public ChorSpec requestedSpec;
	public List<SpecAndService> specsAndServices;
	public Map<String, List<ScheduledServiceModification>> scheduledChanges;

	public ChoreographyRunningStatus() {
		//this.requestedSpec = null;
		this.specsAndServices = new ArrayList<SpecAndService>();
		this.scheduledChanges = new HashMap<String, List<ScheduledServiceModification>>();
		cleanUpScheduledChanges();
	}
	
	void cleanUpScheduledChanges() {
		this.scheduledChanges.put("create", new ArrayList<ScheduledServiceModification>());
		this.scheduledChanges.put("update", new ArrayList<ScheduledServiceModification>());
		this.scheduledChanges.put("remove", new ArrayList<ScheduledServiceModification>());
		this.scheduledChanges.put("nochange", new ArrayList<ScheduledServiceModification>());
	}

	void addScheduledServiceChange(String changeType, ScheduledServiceModification scheduledServiceModification) {
		scheduledChanges.get(changeType).add(scheduledServiceModification);
	}
}