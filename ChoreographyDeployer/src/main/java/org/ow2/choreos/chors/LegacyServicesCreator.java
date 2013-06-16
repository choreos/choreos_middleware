package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.datamodel.LegacyService;
import org.ow2.choreos.chors.datamodel.LegacyServiceSpec;

public class LegacyServicesCreator {

    public List<LegacyService> createLegacyServices(ChoreographySpec chorSpec) {
	List<LegacyService> legacyServices = new ArrayList<LegacyService>();
	List<LegacyServiceSpec> specs = chorSpec.getLegacyServiceSpecs();
	if (specs != null) {
	    for (LegacyServiceSpec spec : chorSpec.getLegacyServiceSpecs()) {
		LegacyService svc = new LegacyService(spec);
		legacyServices.add(svc);
	    }
	}
	return legacyServices;
    }
}
