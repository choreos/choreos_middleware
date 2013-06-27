/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services;

public class ServiceDeployerException extends Exception {

    private static final long serialVersionUID = -8759048371767307766L;
    private final String serviceName;

    public ServiceDeployerException(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceDeployerException(String serviceName, String message) {
        super(message);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return this.serviceName;
    }
}
