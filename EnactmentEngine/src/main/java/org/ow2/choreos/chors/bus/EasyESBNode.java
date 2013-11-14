/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;


public interface EasyESBNode {

    public String getAdminEndpoint();

    /**
     * Binds and exposes a service into the EasyESB node.
     * 
     * @param serviceUrl
     * @param serviceWsdl
     * @return the URI to access the service exposed by the bus
     * @throws EasyESBException
     */
    public String proxifyService(String serviceUrl, String serviceWsdl) throws EasyESBException;

    public void addNeighbour(EasyESBNode neighbour) throws EasyESBException;
    
    public void notifyEasierBSM(String bsmAdminEndpoint) throws EasyESBException;

}
