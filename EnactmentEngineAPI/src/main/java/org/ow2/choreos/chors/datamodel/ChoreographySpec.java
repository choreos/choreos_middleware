package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChoreographySpec {

    protected List<ChoreographyServiceSpec> choreographyServiceSpecs = new ArrayList<ChoreographyServiceSpec>();

    public ChoreographySpec() {

    }

    public ChoreographySpec(ChoreographyServiceSpec... choreographyServiceSpecs) {
	for (ChoreographyServiceSpec spec : choreographyServiceSpecs)
	    this.choreographyServiceSpecs.add(spec);
    }

    public ChoreographyServiceSpec getChoreographyServiceSpecByName(String choreographyServiceSpecName) {

	for (ChoreographyServiceSpec svcSpec : choreographyServiceSpecs) {
	    if (choreographyServiceSpecName.equals(svcSpec.getName()))
		return svcSpec;
	}
	throw new NoSuchElementException("Service spec named " + choreographyServiceSpecName + " does not exist");
    }

    public void addChoreographyServiceSpec(ChoreographyServiceSpec choreographyServiceSpec) {
	this.choreographyServiceSpecs.add(choreographyServiceSpec);
    }

    public List<ChoreographyServiceSpec> getChoreographyServiceSpecs() {
	return choreographyServiceSpecs;
    }

    public void setChoreographyServiceSpecs(List<ChoreographyServiceSpec> choreographyServiceSpecs) {
	this.choreographyServiceSpecs = choreographyServiceSpecs;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((choreographyServiceSpecs == null) ? 0 : choreographyServiceSpecs.hashCode());
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
	ChoreographySpec other = (ChoreographySpec) obj;
	if (choreographyServiceSpecs == null) {
	    if (other.choreographyServiceSpecs != null)
		return false;
	} else if (!choreographyServiceSpecs.equals(other.choreographyServiceSpecs))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ChorSpec [chorServiceSpecs=" + choreographyServiceSpecs + "]";
    }

}
