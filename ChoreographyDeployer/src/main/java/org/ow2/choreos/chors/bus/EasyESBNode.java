/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import java.io.IOException;

import esstar.petalslink.com.service.management._1_0.ManagementException;

public interface EasyESBNode {

    public String getAdminEndpoint();

    /**
     * Binds and exposes a service into the EasyESB node.
     * 
     * @param serviceUrl
     * @param serviceWsdl
     * @return the URI to access the service exposed by the bus
     * @throws IOException
     *             if could not complete the operation
     */
    public String proxifyService(String serviceUrl, String serviceWsdl) throws ManagementException;

}
