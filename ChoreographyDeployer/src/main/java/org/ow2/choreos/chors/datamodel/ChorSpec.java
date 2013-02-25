package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChorSpec {

	protected List<ChorServiceSpec> chorServiceSpecs = new ArrayList<ChorServiceSpec>();

	public ChorServiceSpec getServiceSpecByName(String serviceName) {
		
		for (ChorServiceSpec svc: chorServiceSpecs) {
			if (serviceName.equals(svc.getName()))
				return svc;
		}
		return null;
	}
	
	public void addChorServiceSpec(ChorServiceSpec spec) {
		this.chorServiceSpecs.add(spec);
	}
	
	public List<ChorServiceSpec> getChorServiceSpecs() {
		return chorServiceSpecs;
	}

	public void setChorServiceSpecs(List<ChorServiceSpec> spec) {
		this.chorServiceSpecs = spec;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chorServiceSpecs == null) ? 0 : chorServiceSpecs.hashCode());
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
		ChorSpec other = (ChorSpec) obj;
		if (chorServiceSpecs == null) {
			if (other.chorServiceSpecs != null)
				return false;
		} else if (!chorServiceSpecs.equals(other.chorServiceSpecs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChorSpec [chorServiceSpecs=" + chorServiceSpecs + "]";
	}
	
}
