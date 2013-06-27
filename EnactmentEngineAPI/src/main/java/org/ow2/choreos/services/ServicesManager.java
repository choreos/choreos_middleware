/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services;

import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public interface ServicesManager {

    /**
     * 
     * @param service
     *            specification
     * @return information about how the service was deployed.
     * @throws ServiceNotCreatedException
     *             if deploy was not possible.
     */
    public DeployableService createService(DeployableServiceSpec serviceSpec) throws ServiceNotCreatedException;

    /**
     * 
     * @param uuid
     * @return the service representation
     * @throws ServiceNotFoundException
     *             if ID does not exist
     * @throws ServiceNotFoundException
     */
    public DeployableService getService(String uuid) throws ServiceNotFoundException;

    /**
     * 
     * @param uuid
     * @throws ServiceNotDeletedException
     *             if it fails
     */
    public void deleteService(String uuid) throws ServiceNotDeletedException, ServiceNotFoundException;

    /**
     * 
     * @param service
     *            specification
     * @return information about how the service was deployed.
     * @throws UnhandledModificationException
     * @throws ServiceNotCreatedException
     *             if deploy was not possible.
     */
    public DeployableService updateService(DeployableServiceSpec newServiceSpec) throws ServiceNotModifiedException,
            UnhandledModificationException;
}
