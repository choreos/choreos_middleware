package org.ow2.choreos.experiment.enact;

import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class Spec {

    public static ChoreographySpec getSpec() {
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	return models.getChorSpec();
    }

}
