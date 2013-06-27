/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.preparer;

import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class DeploymentRequest {

    private DeployableServiceSpec spec;
    private int numberOfInstances = 1;
    
    public DeployableServiceSpec getSpec() {
        return spec;
    }
    public void setSpec(DeployableServiceSpec spec) {
        this.spec = spec;
    }
    public int getNumberOfInstances() {
        return numberOfInstances;
    }
    public void setNumberOfInstances(int numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
    }
    
}
