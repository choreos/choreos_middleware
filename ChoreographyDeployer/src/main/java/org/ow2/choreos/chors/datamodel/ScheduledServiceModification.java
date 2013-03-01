package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.diff.DeployerAction;

public class ScheduledServiceModification {
	
	private SpecAndService specAndService;
	private ChorServiceSpec requestedSpec;
	private List<DeployerAction> actions = null;

	public SpecAndService getSpecAndService() {
		return specAndService;
	}

	public void setSpecAndService(SpecAndService specAndService) {
		this.specAndService = specAndService;
	}

	public ChorServiceSpec getRequestedSpec() {
		return requestedSpec;
	}

	public void setRequestedSpec(ChorServiceSpec requestedSpec) {
		this.requestedSpec = requestedSpec;
	}

	public ScheduledServiceModification(ChorServiceSpec spec, SpecAndService specAndService) {
		this.requestedSpec = spec;
		this.specAndService = specAndService;
	}
	
	public void addAction(DeployerAction action) {
		if(actions == null)
			actions = new ArrayList<DeployerAction>();
		this.actions.add(action);
	}
	
	public List<DeployerAction> getActions() {
		return this.actions;
	}

}
