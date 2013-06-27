/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.services.preparer.PrepareDeploymentFailedException;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.utils.LogConfigurator;

public class ServiceDeployerImplTest {

    private ServicesManager servicesManager;
    private DeployableServiceSpec serviceSpec;

    @Before
    public void setUp() throws PrepareDeploymentFailedException {
        LogConfigurator.configLog();
        DeploymentManagerConfiguration.set("TESTING", "true");
        setUpServiceDeployer();
    }

    private void setUpServiceDeployer() {
        serviceSpec = new DeployableServiceSpec();
        serviceSpec.setPackageUri("http://choreos.eu/services/airline.jar");
        serviceSpec.setPackageType(PackageType.COMMAND_LINE);
        serviceSpec.setEndpointName("airline");
        serviceSpec.setPort(8042);
        servicesManager = new ServicesManagerImpl();
    }

    @Test
    public void shouldReturnAValidService() throws PrepareDeploymentFailedException, ServiceNotCreatedException {
        DeployableService service = servicesManager.createService(serviceSpec);
        assertEquals(serviceSpec.getUuid(), service.getSpec().getUuid());
    }

}
