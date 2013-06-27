/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.context;

import java.util.List;

public interface ContextSender {

    /**
     * Calls setInvokationAddress operation on service in the serviceEndpoint.
     * So, the service in endpoint will know that its partner named partnerName
     * with partnerRole is realized by instances in partnerEndpoints.
     * 
     * @param serviceEndpoint
     * @param partnerRole
     * @param partnerName
     * @param partnerEndpoints
     * @throws ContextNotSentException
     *             if context was not successfully set
     */
    public void sendContext(String serviceEndpoint, String partnerRole, String partnerName,
            List<String> partnerEndpoints) throws ContextNotSentException;
}
